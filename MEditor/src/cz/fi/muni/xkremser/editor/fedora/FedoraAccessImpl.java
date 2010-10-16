package cz.fi.muni.xkremser.editor.fedora;

import static cz.fi.muni.xkremser.editor.fedora.utils.FedoraUtils.getDjVuImage;
import static cz.fi.muni.xkremser.editor.fedora.utils.FedoraUtils.getFedoraDatastreamsList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.fedora.api.FedoraAPIA;
import org.fedora.api.FedoraAPIAService;
import org.fedora.api.FedoraAPIM;
import org.fedora.api.FedoraAPIMService;
import org.fedora.api.ObjectFactory;
import org.fedora.api.RelationshipTuple;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.client.Constants.KrameriusModel;
import cz.fi.muni.xkremser.editor.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.fedora.utils.LexerException;
import cz.fi.muni.xkremser.editor.fedora.utils.PIDParser;
import cz.fi.muni.xkremser.editor.fedora.utils.RESTHelper;
import cz.fi.muni.xkremser.editor.fedora.utils.XMLUtils;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;

/**
 * Default implementation of fedoraAccess
 * 
 * @see FedoraAccess
 * @author pavels
 */
public class FedoraAccessImpl implements FedoraAccess {

	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(FedoraAccessImpl.class.getName());

	private final EditorConfiguration configuration;
	private final NamespaceContext nsContext;

	@Inject
	public FedoraAccessImpl(EditorConfiguration configuration, NamespaceContext nsContext) {
		super();
		this.configuration = configuration;
		this.nsContext = nsContext;
	}

	@Override
	public List<Element> getPages(String uuid, boolean deep) throws IOException {
		Document relsExt = getRelsExt(uuid);

		DOMImplementationLS domImplLS = (DOMImplementationLS) relsExt.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		String str = serializer.writeToString(relsExt);
		System.out.println(str);

		return getPages(uuid, relsExt.getDocumentElement());
	}

	@Override
	public Document getRelsExt(String uuid) throws IOException {
		String relsExtUrl = relsExtUrl(uuid);
		LOGGER.fine("Reading rels ext +" + relsExtUrl);
		InputStream docStream = RESTHelper.inputStream(relsExtUrl, configuration.getFedoraLogin(), configuration.getFedoraPassword());

		try {
			return XMLUtils.parseDocument(docStream, true);
		} catch (ParserConfigurationException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		}
	}

	@Override
	public KrameriusModel getKrameriusModel(Document relsExt) {
		try {
			Element foundElement = XMLUtils.findElement(relsExt.getDocumentElement(), "hasModel", FedoraNamespaces.FEDORA_MODELS_URI);
			if (foundElement != null) {
				String sform = foundElement.getAttributeNS(FedoraNamespaces.RDF_NAMESPACE_URI, "resource");
				PIDParser pidParser = new PIDParser(sform);
				pidParser.disseminationURI();
				KrameriusModel model = KrameriusModelHelper.parseString(pidParser.getObjectId());
				return model;
			} else
				throw new IllegalArgumentException("cannot find model of ");
		} catch (DOMException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IllegalArgumentException(e);
		} catch (LexerException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public KrameriusModel getKrameriusModel(String uuid) throws IOException {
		return getKrameriusModel(getRelsExt(uuid));
	}

	@Override
	public Document getBiblioMods(String uuid) throws IOException {
		String biblioModsUrl = biblioMods(uuid);
		LOGGER.info("Reading bibliomods +" + biblioModsUrl);
		InputStream docStream = RESTHelper.inputStream(biblioModsUrl, configuration.getFedoraLogin(), configuration.getFedoraPassword());
		try {
			return XMLUtils.parseDocument(docStream, true);
		} catch (ParserConfigurationException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		}
	}

	@Override
	public Document getDC(String uuid) throws IOException {
		String dcUrl = dc(uuid);
		LOGGER.info("Reading dc +" + dcUrl);
		InputStream docStream = RESTHelper.inputStream(dcUrl, configuration.getFedoraLogin(), configuration.getFedoraPassword());
		try {
			return XMLUtils.parseDocument(docStream, true);
		} catch (ParserConfigurationException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		}
	}

	boolean processRelsExtInternal(Element topElem, RelsExtHandler handler, int level) throws IOException, LexerException {
		boolean breakProcess = false;
		String namespaceURI = topElem.getNamespaceURI();
		if (namespaceURI.equals(FedoraNamespaces.ONTOLOGY_RELATIONSHIP_NAMESPACE_URI)) {
			String nodeName = topElem.getLocalName();
			FedoraRelationship relation = FedoraRelationship.findRelation(nodeName);
			if (relation != null) {
				if (handler.accept(relation)) {
					handler.handle(topElem, relation, level);
					if (handler.breakProcess())
						return true;

					// deep
					String attVal = topElem.getAttributeNS(FedoraNamespaces.RDF_NAMESPACE_URI, "resource");
					PIDParser pidParser = new PIDParser(attVal);
					pidParser.disseminationURI();
					String objectId = pidParser.getObjectId();
					// LOGGER.info("processing uuid =" +objectId);
					Document relsExt = getRelsExt(objectId);
					breakProcess = processRelsExtInternal(relsExt.getDocumentElement(), handler, level + 1);
				}
			} else {
				LOGGER.severe("Unsupported type of relation '" + nodeName + "'");
			}

			if (breakProcess) {
				LOGGER.info("Process has been borken");
				return breakProcess;
			}
			NodeList childNodes = topElem.getChildNodes();
			for (int i = 0, ll = childNodes.getLength(); i < ll; i++) {
				Node item = childNodes.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					breakProcess = processRelsExtInternal((Element) item, handler, level);
					if (breakProcess)
						break;
				}
			}
		} else if (namespaceURI.equals(FedoraNamespaces.RDF_NAMESPACE_URI)) {
			NodeList childNodes = topElem.getChildNodes();
			for (int i = 0, ll = childNodes.getLength(); i < ll; i++) {
				Node item = childNodes.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					breakProcess = processRelsExtInternal((Element) item, handler, level);
					if (breakProcess)
						break;
				}
			}
		}
		return breakProcess;

	}

	@Override
	public void processRelsExt(Document relsExtDocument, RelsExtHandler handler) throws IOException {
		try {
			processRelsExtInternal(relsExtDocument.getDocumentElement(), handler, 1);
		} catch (DOMException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (LexerException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		}
	}

	@Override
	public void processRelsExt(String uuid, RelsExtHandler handler) throws IOException {
		LOGGER.info("processing uuid =" + uuid);
		processRelsExt(getRelsExt(uuid), handler);
	}

	@Override
	public List<Element> getPages(String uuid, Element rootElementOfRelsExt) throws IOException {
		try {
			ArrayList<Element> elms = new ArrayList<Element>();
			String xPathStr = "/RDF/Description/hasPage";
			XPathFactory xpfactory = XPathFactory.newInstance();
			XPath xpath = xpfactory.newXPath();
			XPathExpression expr = xpath.compile(xPathStr);
			NodeList nodes = (NodeList) expr.evaluate(rootElementOfRelsExt, XPathConstants.NODESET);
			for (int i = 0, lastIndex = nodes.getLength() - 1; i <= lastIndex; i++) {
				Element elm = (Element) nodes.item(i);
				elms.add(elm);
			}
			return elms;
		} catch (XPathExpressionException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		}
	}

	private List<String> getUuids(String uuid, String xPathString) throws IOException {
		try {
			Element rootElementOfRelsExt = getRelsExt(uuid).getDocumentElement();
			List<String> elms = new ArrayList<String>();
			XPathFactory xpfactory = XPathFactory.newInstance();
			XPath xpath = xpfactory.newXPath();
			xpath.setNamespaceContext(nsContext);
			XPathExpression expr = xpath.compile(xPathString);

			NodeList nodes = (NodeList) expr.evaluate(rootElementOfRelsExt, XPathConstants.NODESET);
			for (int i = 0, lastIndex = nodes.getLength() - 1; i <= lastIndex; i++) {
				Node node = nodes.item(i);
				PIDParser pidParser = new PIDParser(node.getNodeValue());
				pidParser.disseminationURI();
				elms.add(pidParser.getObjectId());
			}
			return elms;
		} catch (XPathExpressionException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (LexerException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
	}

	public static org.w3c.dom.Document loadXMLFrom(String xml) throws org.xml.sax.SAXException, java.io.IOException {
		return loadXMLFrom(new java.io.ByteArrayInputStream(xml.getBytes()));
	}

	public static org.w3c.dom.Document loadXMLFrom(java.io.InputStream is) throws org.xml.sax.SAXException, java.io.IOException {
		javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		javax.xml.parsers.DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (javax.xml.parsers.ParserConfigurationException ex) {
		}
		org.w3c.dom.Document doc = builder.parse(is);
		is.close();
		return doc;
	}

	public static void main(String... args) throws FileNotFoundException, SAXException, IOException, XPathExpressionException, LexerException {

		Document doc = loadXMLFrom(new FileInputStream("/home/freon/example.xml"));

		Element rootElementOfRelsExt = doc.getDocumentElement();
		List<String> elms = new ArrayList<String>();
		XPathFactory xpfactory = XPathFactory.newInstance();
		XPath xpath = xpfactory.newXPath();
		xpath.setNamespaceContext(new FedoraNamespaceContext()); // TODO: singleton
																															// a inject
		// XPathExpression expr = xpath.compile("/RDF/Description/hasPage");
		XPathExpression expr = xpath.compile("/rdf:RDF/rdf:Description/kramerius:hasPage/@rdf:resource");

		NodeList nodes = (NodeList) expr.evaluate(rootElementOfRelsExt, XPathConstants.NODESET);
		System.out.println(nodes.getLength());
		for (int i = 0, lastIndex = nodes.getLength() - 1; i <= lastIndex; i++) {

			// DOMImplementationLS domImplLS = (DOMImplementationLS)
			// nodes.item(i).getOwnerDocument().getImplementation();
			// LSSerializer serializer = domImplLS.createLSSerializer();
			// String str = serializer.writeToString(nodes.item(i));
			// System.out.println(str);

			Node elm = nodes.item(i);
			System.out.println("***************");
			System.out.println("node value=" + elm.getNodeValue());
			System.out.println("local name=" + elm.getLocalName());
			System.out.println("text content=" + elm.getTextContent());
			System.out.println("prefix=" + elm.getPrefix());
			System.out.println("namespace uri=" + elm.getNamespaceURI());
			// String sform = elm.getAttributeNS(FedoraNamespaces.RDF_NAMESPACE_URI,
			// "resource");
			// PIDParser pidParser = new PIDParser(sform);
			// pidParser.disseminationURI();
			// elms.add(pidParser.getObjectId());
		}

	}

	@Override
	public List<String> getPagesUuid(String uuid) throws IOException {
		return getUuids(uuid, "/rdf:RDF/rdf:Description/kramerius:hasPage/@rdf:resource");
	}

	@Override
	public List<String> getIsOnPagesUuid(String uuid) throws IOException {
		return getUuids(uuid, "/rdf:RDF/rdf:Description/kramerius:isOnPage/@rdf:resource");
	}

	@Override
	public InputStream getThumbnail(String uuid) throws IOException {
		HttpURLConnection con = (HttpURLConnection) RESTHelper.openConnection(FedoraUtils.getThumbnailFromFedora(uuid), configuration.getFedoraLogin(),
				configuration.getFedoraPassword());
		InputStream thumbInputStream = con.getInputStream();
		return thumbInputStream;
	}

	@Override
	public Document getThumbnailProfile(String uuid) throws IOException {
		HttpURLConnection con = (HttpURLConnection) RESTHelper.openConnection(thumbImageProfile(uuid), configuration.getFedoraLogin(),
				configuration.getFedoraPassword());
		InputStream stream = con.getInputStream();
		try {
			return XMLUtils.parseDocument(stream, true);
		} catch (ParserConfigurationException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		}
	}

	@Override
	public String getThumbnailMimeType(String uuid) throws IOException, XPathExpressionException {
		Document profileDoc = getThumbnailProfile(uuid);
		return mimetypeFromProfile(profileDoc);
	}

	@Override
	public InputStream getImageFULL(String uuid) throws IOException {
		HttpURLConnection con = (HttpURLConnection) RESTHelper
				.openConnection(getDjVuImage(uuid), configuration.getFedoraLogin(), configuration.getFedoraPassword());
		con.connect();
		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream thumbInputStream = con.getInputStream();
			return thumbInputStream;
		}
		throw new IOException("404");
	}

	@Override
	public boolean isImageFULLAvailable(String uuid) throws IOException {
		HttpURLConnection con = (HttpURLConnection) RESTHelper.openConnection(getFedoraDatastreamsList(uuid), configuration.getFedoraLogin(),
				configuration.getFedoraPassword());
		InputStream stream = con.getInputStream();
		try {
			Document parseDocument = XMLUtils.parseDocument(stream, true);
			return datastreamInListOfDatastreams(parseDocument, FedoraUtils.IMG_FULL_STREAM);
		} catch (ParserConfigurationException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (XPathExpressionException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} finally {
			con.disconnect();
		}
	}

	@Override
	public boolean isContentAccessible(String uuid) throws IOException {
		return true;
	}

	@Override
	public String getImageFULLMimeType(String uuid) throws IOException, XPathExpressionException {
		Document profileDoc = getImageFULLProfile(uuid);
		return mimetypeFromProfile(profileDoc);
	}

	private boolean datastreamInListOfDatastreams(Document datastreams, String dsId) throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile("/objectDatastreams/datastream[@dsid='" + dsId + "']");
		Node oneNode = (Node) expr.evaluate(datastreams, XPathConstants.NODE);
		return (oneNode != null);

	}

	private String mimetypeFromProfile(Document profileDoc) throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile("/datastreamProfile/dsMIME");
		Node oneNode = (Node) expr.evaluate(profileDoc, XPathConstants.NODE);
		if (oneNode != null) {
			Element elm = (Element) oneNode;
			String mimeType = elm.getTextContent();
			if ((mimeType != null) && (!mimeType.trim().equals(""))) {
				mimeType = mimeType.trim();
				return mimeType;
			}
		}
		return null;
	}

	@Override
	public Document getImageFULLProfile(String uuid) throws IOException {
		HttpURLConnection con = (HttpURLConnection) RESTHelper.openConnection(fullImageProfile(uuid), configuration.getFedoraLogin(),
				configuration.getFedoraPassword());
		InputStream stream = con.getInputStream();
		try {
			return XMLUtils.parseDocument(stream, true);
		} catch (ParserConfigurationException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		}
	}

	public String fullImageProfile(String uuid) {
		return dsProfile("IMG_FULL", uuid);
	}

	public String thumbImageProfile(String uuid) {
		return dsProfile("IMG_THUMB", uuid);
	}

	public String dcProfile(String uuid) {
		return dsProfile("DC", uuid);
	}

	public String biblioModsProfile(String uuid) {
		return dsProfile("BIBLIO_MODS", uuid);
	}

	public String relsExtProfile(String uuid) {
		return dsProfile("RELS-EXT", uuid);
	}

	public String dsProfile(String ds, String uuid) {
		String fedoraObject = configuration.getFedoraHost() + "/objects/uuid:" + uuid;
		return fedoraObject + "/datastreams/" + ds + "?format=text/xml";
	}

	public String dsProfileForPid(String ds, String pid) {
		String fedoraObject = configuration.getFedoraHost() + "/objects/" + pid;
		return fedoraObject + "/datastreams/" + ds + "?format=text/xml";
	}

	public String biblioMods(String uuid) {
		String fedoraObject = configuration.getFedoraHost() + "/get/uuid:" + uuid;
		return fedoraObject + "/BIBLIO_MODS";
	}

	public String dc(String uuid) {
		String fedoraObject = configuration.getFedoraHost() + "/get/uuid:" + uuid;
		return fedoraObject + "/DC";
	}

	public String obj(String uuid) {
		String fedoraObject = configuration.getFedoraHost() + "/get/" + uuid;
		return fedoraObject;
	}

	public String relsExtUrl(String uuid) {
		String url = configuration.getFedoraHost() + "/get/uuid:" + uuid + "/RELS-EXT";
		return url;
	}

	private FedoraAPIM APIMport;
	private FedoraAPIA APIAport;

	private ObjectFactory of;

	@Override
	public FedoraAPIA getAPIA() {
		if (APIAport == null) {
			initAPIA();
		}
		return APIAport;
	}

	@Override
	public FedoraAPIM getAPIM() {
		if (APIMport == null) {
			initAPIM();
		}
		return APIMport;
	}

	@Override
	public ObjectFactory getObjectFactory() {
		if (of == null) {
			of = new ObjectFactory();
		}
		return of;
	}

	private void initAPIA() {
		final String user = configuration.getFedoraLogin();
		final String pwd = configuration.getFedoraPassword();
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pwd.toCharArray());
			}
		});

		FedoraAPIAService APIAservice = null;
		try {
			APIAservice = new FedoraAPIAService(new URL(configuration.getFedoraLogin() + "/wsdl?api=API-A"), new QName("http://www.fedora.info/definitions/1/0/api/",
					"Fedora-API-A-Service"));
		} catch (MalformedURLException e) {
			LOGGER.severe("InvalidURL API-A:" + e);
			throw new RuntimeException(e);
		}
		APIAport = APIAservice.getPort(FedoraAPIA.class);
		((BindingProvider) APIAport).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
		((BindingProvider) APIAport).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, pwd);

	}

	private void initAPIM() {
		final String user = configuration.getFedoraLogin();
		final String pwd = configuration.getFedoraPassword();
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pwd.toCharArray());
			}
		});

		FedoraAPIMService APIMservice = null;
		try {
			APIMservice = new FedoraAPIMService(new URL(configuration.getFedoraHost() + "/wsdl?api=API-M"), new QName("http://www.fedora.info/definitions/1/0/api/",
					"Fedora-API-M-Service"));
		} catch (MalformedURLException e) {
			LOGGER.severe("InvalidURL API-M:" + e);
			throw new RuntimeException(e);
		}
		APIMport = APIMservice.getPort(FedoraAPIM.class);
		((BindingProvider) APIMport).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
		((BindingProvider) APIMport).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, pwd);

	}

	private static final List<String> TREE_PREDICATES = Arrays.asList(new String[] { "http://www.nsdl.org/ontologies/relationships#hasPage",
			"http://www.nsdl.org/ontologies/relationships#hasPart", "http://www.nsdl.org/ontologies/relationships#hasVolume",
			"http://www.nsdl.org/ontologies/relationships#hasItem", "http://www.nsdl.org/ontologies/relationships#hasUnit" });

	@Override
	public void processSubtree(String pid, TreeNodeProcessor processor) {
		processor.process(pid);
		for (RelationshipTuple rel : getAPIM().getRelationships(pid, null)) {
			if (TREE_PREDICATES.contains(rel.getPredicate())) {
				try {
					processSubtree(rel.getObject(), processor);
				} catch (Exception ex) {
					LOGGER.warning("Error processing subtree, skipping:" + ex);
				}
			}
		}
	}

	@Override
	public Set<String> getPids(String pid) {
		final Set<String> retval = new HashSet<String>();
		processSubtree(pid, new TreeNodeProcessor() {

			@Override
			public void process(String pid) {
				retval.add(pid);
			}
		});
		return retval;
	}

	@Override
	public InputStream getDataStream(String pid, String datastreamName) throws IOException {
		String datastream = configuration.getFedoraHost() + "/get/" + pid + "/" + datastreamName;
		HttpURLConnection con = (HttpURLConnection) RESTHelper.openConnection(datastream, configuration.getFedoraLogin(), configuration.getFedoraPassword());
		con.connect();
		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream thumbInputStream = con.getInputStream();
			return thumbInputStream;
		}
		throw new IOException("404");
	}

	@Override
	public String getMimeTypeForStream(String pid, String datastreamName) throws IOException {
		HttpURLConnection con = (HttpURLConnection) RESTHelper.openConnection(dsProfileForPid(datastreamName, pid), configuration.getFedoraLogin(),
				configuration.getFedoraPassword());
		InputStream stream = con.getInputStream();
		try {
			Document parseDocument = XMLUtils.parseDocument(stream, true);
			return mimetypeFromProfile(parseDocument);
		} catch (ParserConfigurationException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		} catch (XPathExpressionException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		}
	}

	@Override
	public boolean isDigitalObjectPresent(String uuid) {
		String objUrl = obj(uuid);
		LOGGER.info("Reading dc +" + objUrl);
		byte[] bytes = null;
		try {
			bytes = getAPIM().getObjectXML(uuid);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, uuid + " in not in the repository, please insert this model first!" + e.getMessage(), e);
			return false;
		}
		return bytes != null;
	}
}
