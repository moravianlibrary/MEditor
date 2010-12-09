/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.handler;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.RDFModels;
import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDigitalObjectDetailResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;

// TODO: Auto-generated Javadoc
/**
 * The Class GetDigitalObjectDetailHandler.
 */
public class PutDigitalObjectDetailHandler implements ActionHandler<PutDigitalObjectDetailAction, PutDigitalObjectDetailResult> {

	/** The logger. */
	private final Log logger;
	private static final String PART_1 = "<kramerius:";
	private static final String PART_2 = " rdf:resource=\"info:fedora/uuid:";
	private static final String PART_3 = "\"></kramerius:";
	private static final String PART_4 = ">\n";

	@Inject
	@Named("securedFedoraAccess")
	private FedoraAccess fedoraAccess;

	@Inject
	private NamespaceContext nsContext;

	private final EditorConfiguration configuration;

	/** The injector. */
	@Inject
	Injector injector;

	/**
	 * Instantiates a new gets the digital object detail handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param handler
	 *          the handler
	 */
	@Inject
	public PutDigitalObjectDetailHandler(final Log logger, final EditorConfiguration configuration) {
		this.logger = logger;
		this.configuration = configuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
	 * .gwtplatform.dispatch.shared.Action,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public PutDigitalObjectDetailResult execute(final PutDigitalObjectDetailAction action, final ExecutionContext context) throws ActionException {
		if (action == null || action.getDetail() == null)
			throw new NullPointerException("getDetail()");
		AbstractDigitalObjectDetail detail = action.getDetail();
		StringBuilder sb = new StringBuilder();
		StringBuilder contentBuilder = new StringBuilder();
		// page structure
		if (detail.hasPages()) {
			if (detail.getPages() == null || detail.getPages().size() == 0) {
				System.out.println("no pages");
			} else {
				String relation = RDFModels.convertToRdf(KrameriusModel.PAGE);
				for (PageDetail page : detail.getPages()) {
					sb.append(PART_1).append(relation).append(PART_2).append(page.getUuid()).append(PART_3).append(relation).append(PART_4);
				}
			}
		}
		// container structure
		if (detail.hasContainers() != 0) {
			for (int i = 0; i < detail.hasContainers(); i++) {
				if (detail.getContainers().size() <= i || detail.getContainers().get(i) == null || detail.getContainers().get(i).size() == 0) {
					System.out.println("no container " + i);
				} else {
					String relation = RDFModels.convertToRdf(detail.getChildContainerModels().get(i));
					for (AbstractDigitalObjectDetail obj : detail.getContainers().get(i)) {
						sb.append(PART_1).append(relation).append(PART_2).append(obj.getUuid()).append(PART_3).append(relation).append(PART_4);
					}
				}
			}
		}

		if ("ee250440-b65d-11dd-83e5-000d606f5dc6".equals(detail.getUuid())) {
			Document relsExt = null;
			try {
				relsExt = fedoraAccess.getRelsExt(detail.getUuid());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				String xPathStr = "/rdf:RDF/rdf:Description/kramerius:hasPage";
				String paretnStr = "/rdf:RDF/rdf:Description";
				XPathFactory xpfactory = XPathFactory.newInstance();
				XPath xpath1 = xpfactory.newXPath();
				XPath xpath2 = xpfactory.newXPath();
				xpath1.setNamespaceContext(nsContext);
				xpath2.setNamespaceContext(nsContext);
				XPathExpression expr1 = xpath1.compile(paretnStr);
				XPathExpression expr2 = xpath2.compile(xPathStr);
				NodeList nodes1 = (NodeList) expr1.evaluate(relsExt, XPathConstants.NODESET);
				Element parent = null;
				if (nodes1.getLength() != 0) {
					parent = (Element) nodes1.item(0);
				}
				NodeList nodes2 = (NodeList) expr2.evaluate(relsExt, XPathConstants.NODESET);
				for (int i = 0, lastIndex = nodes2.getLength() - 1; i <= lastIndex; i++) {
					parent.removeChild(nodes2.item(i));
				}

				TransformerFactory transFactory = TransformerFactory.newInstance();
				Transformer transformer = null;
				try {
					transformer = transFactory.newTransformer();
				} catch (TransformerConfigurationException e) {
					e.printStackTrace();
				}
				StringWriter buffer = new StringWriter();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				try {
					transformer.transform(new DOMSource(relsExt), new StreamResult(buffer));
				} catch (TransformerException e) {
					e.printStackTrace();
				}

				String str = buffer.toString();
				String head = str.substring(0, str.indexOf(Constants.RELS_EXT_LAST_ELEMENT));
				String tail = str.substring(str.indexOf(Constants.RELS_EXT_LAST_ELEMENT), str.length());
				contentBuilder.append(head).append(sb).append(tail);

			} catch (XPathExpressionException e) {
			}

			String url = configuration.getFedoraHost() + "/objects/" + Constants.FEDORA_UUID_PREFIX + detail.getUuid() + "/datastreams/RELS-EXT";
			String usr = configuration.getFedoraLogin();
			String pass = configuration.getFedoraPassword();
			String content = contentBuilder.toString();

			RESTHelper.put(url, content, usr, pass);

		}
		sb.toString();

		// dublin core
		if (detail.isDcChanged()) {

		}
		// mods
		if (detail.isModsChanged()) {

		}
		ModsCollectionClient modsCollection = detail.getMods();
		// parse input
		BiblioModsUtils.toXML(BiblioModsUtils.toMods(modsCollection));

		return new PutDigitalObjectDetailResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<PutDigitalObjectDetailAction> getActionType() {
		return PutDigitalObjectDetailAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(PutDigitalObjectDetailAction action, PutDigitalObjectDetailResult result, ExecutionContext context) throws ActionException {
		// idempotency -> no need for undo
	}
}