/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.server.fedora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import org.fedora.api.FedoraAPIA;
import org.fedora.api.FedoraAPIAService;
import org.fedora.api.FedoraAPIM;
import org.fedora.api.FedoraAPIMService;
import org.fedora.api.ObjectFactory;

import cz.fi.muni.xkremser.editor.client.ConnectionException;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.LexerException;
import cz.fi.muni.xkremser.editor.server.fedora.utils.PIDParser;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;
import cz.fi.muni.xkremser.editor.server.fedora.utils.XMLUtils;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces;
import cz.fi.muni.xkremser.editor.shared.domain.FedoraRelationship;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;

import static cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils.getDjVuImage;
import static cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils.getFedoraDatastreamsList;

// TODO: Auto-generated Javadoc
/**
 * Default implementation of fedoraAccess.
 * 
 * @see FedoraAccess
 * @author pavels
 */
public class FedoraAccessImpl
        implements FedoraAccess {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(FedoraAccessImpl.class);

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The ns context. */
    private final NamespaceContext nsContext;

    /**
     * Instantiates a new fedora access impl.
     * 
     * @param configuration
     *        the configuration
     * @param nsContext
     *        the ns context
     */
    @Inject
    public FedoraAccessImpl(EditorConfiguration configuration, NamespaceContext nsContext) {
        super();
        this.configuration = configuration;
        this.nsContext = nsContext;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getRelsExt(java
     * .lang .String)
     */
    @Override
    public Document getRelsExt(String uuid) throws IOException {
        String relsExtUrl = relsExtUrl(uuid);
        LOGGER.debug("Reading rels ext +" + relsExtUrl);
        InputStream docStream =
                RESTHelper.get(relsExtUrl,
                               configuration.getFedoraLogin(),
                               configuration.getFedoraPassword(),
                               true);
        if (docStream == null) {
            throw new ConnectionException("Cannot get RELS EXT stream.");
        }
        try {
            return XMLUtils.parseDocument(docStream, true);
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } finally {
            docStream.close();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getKrameriusModel
     * (org.w3c.dom.Document)
     */
    @Override
    public DigitalObjectModel getDigitalObjectModel(Document relsExt) {
        try {
            Element foundElement =
                    XMLUtils.findElement(relsExt.getDocumentElement(),
                                         "hasModel",
                                         FedoraNamespaces.FEDORA_MODELS_URI);
            if (foundElement != null) {
                String sform = foundElement.getAttributeNS(FedoraNamespaces.RDF_NAMESPACE_URI, "resource");
                PIDParser pidParser = new PIDParser(sform);
                pidParser.disseminationURI();
                DigitalObjectModel model = DigitalObjectModel.parseString(pidParser.getObjectId());
                return model;
            } else
                throw new IllegalArgumentException("cannot find model of ");
        } catch (DOMException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        } catch (LexerException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getKrameriusModel
     * (java.lang.String)
     */
    @Override
    public DigitalObjectModel getDigitalObjectModel(String uuid) throws IOException {
        return getDigitalObjectModel(getRelsExt(uuid));
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getBiblioMods(java
     * .lang.String)
     */
    @Override
    public Document getBiblioMods(String uuid) throws IOException {
        String biblioModsUrl = biblioMods(uuid);
        LOGGER.debug("Reading bibliomods +" + biblioModsUrl);
        InputStream docStream =
                RESTHelper.get(biblioModsUrl,
                               configuration.getFedoraLogin(),
                               configuration.getFedoraPassword(),
                               true);
        if (docStream == null) return null;
        try {
            return XMLUtils.parseDocument(docStream, true);
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } finally {
            docStream.close();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getDC(java.lang
     * .String )
     */
    @Override
    public Document getDC(String uuid) throws IOException {
        String dcUrl = dc(uuid);
        LOGGER.debug("Reading dc +" + dcUrl);
        InputStream docStream =
                RESTHelper.get(dcUrl,
                               configuration.getFedoraLogin(),
                               configuration.getFedoraPassword(),
                               false);
        if (docStream == null) return null;
        try {
            return XMLUtils.parseDocument(docStream, true);
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } finally {
            docStream.close();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getOcr(java.lang.
     * String)
     */
    @Override
    public String getOcr(String uuid) {
        String ocrUrl = ocr(uuid);
        LOGGER.debug("Reading OCR +" + ocrUrl);
        InputStream docStream = null;
        try {
            docStream =
                    RESTHelper.get(ocrUrl,
                                   configuration.getFedoraLogin(),
                                   configuration.getFedoraPassword(),
                                   true);
            if (docStream == null) return null;
        } catch (IOException e) {
            // ocr is not available
            e.printStackTrace();
            return null;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(docStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Reading ocr +" + ocrUrl, e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.error("Closing stream +" + ocrUrl, e);
                    e.printStackTrace();
                } finally {
                    br = null;
                }
            }
            try {
                if (docStream != null) docStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                docStream = null;
            }
        }
        return sb.toString();
    }

    /**
     * Gets the uuids.
     * 
     * @param uuid
     *        the uuid
     * @param xPathString
     *        the x path string
     * @return the uuids
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
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
                elms.add(pidParser.getNamespaceId() + ":" + pidParser.getObjectId());
            }
            return elms;
        } catch (XPathExpressionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (LexerException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Load xml from.
     * 
     * @param xml
     *        the xml
     * @return the org.w3c.dom. document
     * @throws SAXException
     *         the sAX exception
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public static org.w3c.dom.Document loadXMLFrom(String xml) throws org.xml.sax.SAXException,
            java.io.IOException {
        return loadXMLFrom(new java.io.ByteArrayInputStream(xml.getBytes()));
    }

    /**
     * Load xml from.
     * 
     * @param is
     *        the is
     * @return the org.w3c.dom. document
     * @throws SAXException
     *         the sAX exception
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public static org.w3c.dom.Document loadXMLFrom(java.io.InputStream is) throws org.xml.sax.SAXException,
            java.io.IOException {
        javax.xml.parsers.DocumentBuilderFactory factory =
                javax.xml.parsers.DocumentBuilderFactory.newInstance();
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

    @Override
    public List<String> getChildrenUuid(String uuid,
                                        DigitalObjectModel parentModel,
                                        DigitalObjectModel childModel) throws IOException {
        FedoraRelationship relation = NamedGraphModel.getRelationship(parentModel, childModel);
        return getUuids(uuid, relation.getXPathNamespaceAwareQuery() + FedoraRelationship.ATTR_RESOURCE_SUFIX);
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getPagesUuid(java
     * .lang.String)
     */
    @Override
    public List<String> getPagesUuid(String uuid) throws IOException {
        return getUuids(uuid, "/rdf:RDF/rdf:Description/kramerius:hasPage/@rdf:resource");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getIsOnPagesUuid(
     * java.lang.String)
     */
    @Override
    public List<String> getIsOnPagesUuid(String uuid) throws IOException {
        return getUuids(uuid, "/rdf:RDF/rdf:Description/kramerius:isOnPage/@rdf:resource");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getIntCompPartsUuid
     * (java.lang.String)
     */
    @Override
    public List<String> getIntCompPartsUuid(String uuid) throws IOException {
        return getUuids(uuid, "/rdf:RDF/rdf:Description/kramerius:hasIntCompPart/@rdf:resource");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getMonographUnitsUuid
     * (java.lang.String)
     */
    @Override
    public List<String> getMonographUnitsUuid(String uuid) throws IOException {
        return getUuids(uuid, "/rdf:RDF/rdf:Description/kramerius:hasUnit/@rdf:resource");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getPeriodicalItemsUuid
     * (java.lang.String)
     */
    @Override
    public List<String> getPeriodicalItemsUuid(String uuid) throws IOException {
        return getUuids(uuid, "/rdf:RDF/rdf:Description/kramerius:hasItem/@rdf:resource");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getVolumesUuid(java
     * .lang.String)
     */
    @Override
    public List<String> getVolumesUuid(String uuid) throws IOException {
        return getUuids(uuid, "/rdf:RDF/rdf:Description/kramerius:hasVolume/@rdf:resource");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getThumbnail(java
     * .lang.String)
     */
    @Override
    public InputStream getThumbnail(String uuid) throws IOException {
        HttpURLConnection con =
                (HttpURLConnection) RESTHelper.openConnection(FedoraUtils.getThumbnailFromFedora(uuid),
                                                              configuration.getFedoraLogin(),
                                                              configuration.getFedoraPassword(),
                                                              true);
        InputStream thumbInputStream = con.getInputStream();
        return thumbInputStream;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getImageFULL(java
     * .lang.String)
     */
    @Override
    public InputStream getImageFULL(String uuid) throws IOException {
        HttpURLConnection con =
                (HttpURLConnection) RESTHelper.openConnection(getDjVuImage(uuid),
                                                              configuration.getFedoraLogin(),
                                                              configuration.getFedoraPassword(),
                                                              false);
        con.connect();
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream thumbInputStream = con.getInputStream();
            return thumbInputStream;
        }
        throw new IOException("404");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#isImageFULLAvailable
     * (java.lang.String)
     */
    @Override
    public boolean isImageFULLAvailable(String uuid) throws IOException {
        HttpURLConnection con =
                (HttpURLConnection) RESTHelper.openConnection(getFedoraDatastreamsList(uuid),
                                                              configuration.getFedoraLogin(),
                                                              configuration.getFedoraPassword(),
                                                              true);
        InputStream stream = con.getInputStream();
        try {
            Document parseDocument = XMLUtils.parseDocument(stream, true);
            return datastreamInListOfDatastreams(parseDocument, FedoraUtils.IMG_FULL_STREAM);
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (XPathExpressionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } finally {
            con.disconnect();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#isContentAccessible
     * (java.lang.String)
     */
    @Override
    public boolean isContentAccessible(String uuid) throws IOException {
        return true;
    }

    /**
     * Datastream in list of datastreams.
     * 
     * @param datastreams
     *        the datastreams
     * @param dsId
     *        the ds id
     * @return true, if successful
     * @throws XPathExpressionException
     *         the x path expression exception
     */
    private boolean datastreamInListOfDatastreams(Document datastreams, String dsId)
            throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile("/objectDatastreams/datastream[@dsid='" + dsId + "']");
        Node oneNode = (Node) expr.evaluate(datastreams, XPathConstants.NODE);
        return (oneNode != null);

    }

    /**
     * Mimetype from profile.
     * 
     * @param profileDoc
     *        the profile doc
     * @return the string
     * @throws XPathExpressionException
     *         the x path expression exception
     */
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

    /**
     * Full image profile.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String fullImageProfile(String uuid) {
        return dsProfile("IMG_FULL", uuid);
    }

    /**
     * Thumb image profile.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String thumbImageProfile(String uuid) {
        return dsProfile("IMG_THUMB", uuid);
    }

    /**
     * Dc profile.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String dcProfile(String uuid) {
        return dsProfile("DC", uuid);
    }

    /**
     * Biblio mods profile.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String biblioModsProfile(String uuid) {
        return dsProfile("BIBLIO_MODS", uuid);
    }

    /**
     * Rels ext profile.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String relsExtProfile(String uuid) {
        return dsProfile("RELS-EXT", uuid);
    }

    /**
     * Ds profile.
     * 
     * @param ds
     *        the ds
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String dsProfile(String ds, String uuid) {
        String fedoraObject = configuration.getFedoraHost() + "/objects/" + uuid;
        return fedoraObject + "/datastreams/" + ds + "?format=text/xml";
    }

    /**
     * Ds profile for pid.
     * 
     * @param ds
     *        the ds
     * @param pid
     *        the pid
     * @return the string
     */
    public String dsProfileForPid(String ds, String pid) {
        String fedoraObject = configuration.getFedoraHost() + "/objects/" + pid;
        return fedoraObject + "/datastreams/" + ds + "?format=text/xml";
    }

    /**
     * Biblio mods.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String biblioMods(String uuid) {
        String fedoraObject = configuration.getFedoraHost() + "/get/" + uuid;
        return fedoraObject + "/BIBLIO_MODS";
    }

    /**
     * Dc.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String dc(String uuid) {
        String fedoraObject = configuration.getFedoraHost() + "/get/" + uuid;
        return fedoraObject + "/DC";
    }

    /**
     * Ocr.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String ocr(String uuid) {
        String fedoraObject =
                configuration.getFedoraHost() + "/objects/" + uuid + "/datastreams/TEXT_OCR/content";
        return fedoraObject;
    }

    /**
     * Obj.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String obj(String uuid) {
        String fedoraObject = configuration.getFedoraHost() + "/get/" + uuid;
        return fedoraObject;
    }

    /**
     * Obj foxml.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String objFoxml(String uuid) {
        String fedoraObject = configuration.getFedoraHost() + "/objects/" + uuid + "/objectXML";
        return fedoraObject;
    }

    /**
     * Rels ext url.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    public String relsExtUrl(String uuid) {
        String url = configuration.getFedoraHost() + "/get/" + uuid + "/RELS-EXT";
        return url;
    }

    /** The API mport. */
    private FedoraAPIM APIMport;

    /** The API aport. */
    private FedoraAPIA APIAport;

    /** The of. */
    private ObjectFactory of;

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getAPIA()
     */
    @Override
    public FedoraAPIA getAPIA() {
        if (APIAport == null) {
            initAPIA();
        }
        return APIAport;
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getAPIM()
     */
    @Override
    public FedoraAPIM getAPIM() {
        if (APIMport == null) {
            initAPIM();
        }
        return APIMport;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getObjectFactory()
     */
    @Override
    public ObjectFactory getObjectFactory() {
        if (of == null) {
            of = new ObjectFactory();
        }
        return of;
    }

    /**
     * Inits the apia.
     */
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
            APIAservice =
                    new FedoraAPIAService(new URL(configuration.getFedoraLogin() + "/wsdl?api=API-A"),
                                          new QName("http://www.fedora.info/definitions/1/0/api/",
                                                    "Fedora-API-A-Service"));
        } catch (MalformedURLException e) {
            LOGGER.error("InvalidURL API-A:" + e);
            throw new RuntimeException(e);
        }
        APIAport = APIAservice.getPort(FedoraAPIA.class);
        ((BindingProvider) APIAport).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
        ((BindingProvider) APIAport).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, pwd);

    }

    /**
     * Inits the apim.
     */
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
            APIMservice =
                    new FedoraAPIMService(new URL(configuration.getFedoraHost() + "/wsdl?api=API-M"),
                                          new QName("http://www.fedora.info/definitions/1/0/api/",
                                                    "Fedora-API-M-Service"));
        } catch (MalformedURLException e) {
            LOGGER.error("InvalidURL API-M:" + e);
            throw new RuntimeException(e);
        }
        APIMport = APIMservice.getPort(FedoraAPIM.class);
        ((BindingProvider) APIMport).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
        ((BindingProvider) APIMport).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, pwd);

    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getMimeTypeForStream
     * (java.lang.String, java.lang.String)
     */
    @Override
    public String getMimeTypeForStream(String pid, String datastreamName) throws IOException {
        HttpURLConnection con =
                (HttpURLConnection) RESTHelper.openConnection(dsProfileForPid(datastreamName, pid),
                                                              configuration.getFedoraLogin(),
                                                              configuration.getFedoraPassword(),
                                                              true);
        InputStream stream = con.getInputStream();
        try {
            Document parseDocument = XMLUtils.parseDocument(stream, true);
            return mimetypeFromProfile(parseDocument);
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (XPathExpressionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#isDigitalObjectPresent
     * (java.lang.String)
     */
    @Override
    public boolean isDigitalObjectPresent(String uuid) {
        String objUrl = obj(uuid);
        LOGGER.debug("Reading dc +" + objUrl);
        byte[] bytes = null;
        try {
            bytes = getAPIM().getObjectXML(uuid);
        } catch (Exception e) {
            LOGGER.error(uuid + " in not in the repository, please insert this model first!" + e.getMessage(),
                         e);
            return false;
        }
        return bytes != null;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess#getFOXML(java.lang
     * .String)
     */
    @Override
    public String getFOXML(String uuid) {
        InputStream docStream = getFOXMLInputStream(uuid);
        if (docStream == null) {
            return null;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(docStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Reading foxml +" + objFoxml(uuid));
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.error("Closing stream +" + objFoxml(uuid));
                    e.printStackTrace();
                } finally {
                    br = null;
                }
            }
            try {
                if (docStream != null) docStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                docStream = null;
            }
        }
        return sb.toString();
    }

    @Override
    public InputStream getFOXMLInputStream(String uuid) {
        String objUrl = objFoxml(uuid);
        LOGGER.debug("Reading foxml +" + objUrl);
        InputStream docStream = null;
        try {
            docStream =
                    RESTHelper.get(objUrl,
                                   configuration.getFedoraLogin(),
                                   configuration.getFedoraPassword(),
                                   true);
            if (docStream == null) return null;
        } catch (IOException e1) {
            try {
                if (docStream != null) docStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                docStream = null;
            }
            LOGGER.error("Reading foxml +" + objUrl, e1);
            e1.printStackTrace();
            return null;
        }
        return docStream;
    }

}
