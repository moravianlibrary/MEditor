/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import javax.inject.Inject;

import com.google.inject.name.Named;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.XMLUtils;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.FedoraRelationship;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;
import cz.fi.muni.xkremser.editor.shared.rpc.Foxml;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class StoredDigitalObjectHandlerImpl
        extends DigitalObjectHandler
        implements StoredDigitalObjectHandler {

    /** The fedora access. */
    private final FedoraAccess fedoraAccess;
    private static final Logger LOGGER = Logger.getLogger(StoredDigitalObjectHandlerImpl.class);

    /** The configuration. */
    private final EditorConfiguration configuration;

    /**
     * Instantiates a new fedora digital object handler.
     * 
     * @param fedoraAccess
     *        the fedora access
     */
    @Inject
    public StoredDigitalObjectHandlerImpl(@Named("securedFedoraAccess") FedoraAccess fedoraAccess,
                                          final EditorConfiguration configuration) {
        this.fedoraAccess = fedoraAccess;
        this.configuration = configuration;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ActionException
     */

    @Override
    public DigitalObjectDetail getStoredDigitalObject(String uuid,
                                                      String rltvFilePath,
                                                      DigitalObjectModel model) throws ActionException {

        DigitalObjectDetail detail = new DigitalObjectDetail(model, FedoraUtils.getRelated(uuid));
        String filePath = configuration.getUserDirectoriesPath() + rltvFilePath;

        Document foxmlDocument = getFoxmlDocument(filePath);
        detail.setDc(getDcStream(foxmlDocument, uuid, filePath));
        detail.setMods(getModsStrean(foxmlDocument));
        Foxml foxml = getFoxml(uuid, foxmlDocument);
        detail.setFoxmlString(foxml.getFoxml());
        detail.setLabel(foxml.getLabel());
        detail.setOcr(getOCR(foxmlDocument));
        detail.setFirstPageURL(FedoraUtils.findFirstPagePid(uuid));
        detail.setItems(getDigitalObjectItems(uuid, foxmlDocument, model));
        return detail;
    }

    /**
     * @param filePath
     * @return
     * @throws ActionException
     */

    private Document getFoxmlDocument(String filePath) throws ActionException {
        Document foxmlDocument = null;
        File foxmlFile = new File(filePath);
        if (foxmlFile.exists()) {
            FileInputStream foxmlFileStream;

            try {

                foxmlFileStream = new FileInputStream(foxmlFile);
                foxmlDocument = XMLUtils.parseDocument(foxmlFileStream);

            } catch (FileNotFoundException e) {
                LOGGER.error("The file: " + filePath + " has not been found. " + e);
                throw new ActionException(e);

            } catch (ParserConfigurationException e) {
                LOGGER.error("An error occured during parsing the file: " + filePath + " " + e);
                throw new ActionException(e);

            } catch (SAXException e) {
                LOGGER.error("An error occured during parsing the file: " + filePath + "." + e);
                throw new ActionException(e);

            } catch (IOException e) {
                LOGGER.error("An error occured during parsing the file: " + filePath + "." + e);
                throw new ActionException(e);
            }
        }
        return foxmlDocument;
    }

    public List<DigitalObjectDetail> getDigitalObjectItems(String uuid,
                                                           org.w3c.dom.Document foxmlDocument,
                                                           DigitalObjectModel parentModel) {

        List<DigitalObjectModel> childrenModel = NamedGraphModel.getChildren(parentModel);
        List<DigitalObjectDetail> children = new ArrayList<DigitalObjectDetail>();

        for (DigitalObjectModel childModel : childrenModel) {
            FedoraRelationship relation = NamedGraphModel.getRelationship(parentModel, childModel);
            children.addAll(getChildren(uuid, foxmlDocument, relation));
        }

        return children;
    }

    /**
     * @param relation
     * @return
     */

    private List<DigitalObjectDetail> getChildren(String uuid,
                                                  org.w3c.dom.Document foxmlDocument,
                                                  FedoraRelationship relation) {
        List<DigitalObjectDetail> children = null;
        String xPath =
                "//foxml:datastream[@ID=\'RELS-EXT\']/foxml:datastreamVersion[last()]"
                        + "/foxml:xmlContent/rdf:RDF/rdf:Description//kramerius:"
                        + relation.getStringRepresentation();

        NodeList childrenNodes = null;
        try {
            XPathExpression all = FedoraUtils.makeNSAwareXpath().compile(xPath);
            childrenNodes = (NodeList) all.evaluate(foxmlDocument, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            LOGGER.error("Unable to read a FOXML file during finding children of a digital object: " + uuid);
        }
        if (childrenNodes != null) {
            children = new ArrayList<DigitalObjectDetail>(childrenNodes.getLength());
            for (int i = 0; i < childrenNodes.getLength(); i++) {
                String childUuid =
                        ((Element) childrenNodes.item(i)).getAttribute("rdf:resource")
                                .substring((Constants.FEDORA_INFO_PREFIX).length());
                DigitalObjectDetail detail = new DigitalObjectDetail();
                detail.setDc(getDc(childUuid, true));
                children.add(detail);
            }
        }
        return children;
    }

    /**
     * @param uuid
     * @return
     */

    private String getOCR(org.w3c.dom.Document foxml) {
        return "OCR";
    }

    /**
     * @param foxml
     * @return
     */

    private ModsCollectionClient getModsStrean(org.w3c.dom.Document foxml) {
        org.w3c.dom.Document modsDocument = null;
        return handleMods(modsDocument);
    }

    /**
     * @param filePath
     * @param foxml
     * @return
     * @throws ActionException
     */

    private DublinCore getDcStream(org.w3c.dom.Document foxmlDocument, String uuid, String filePath)
            throws ActionException {

        String streamXPath = "//foxml:datastream[@ID=\'DC\']";

        try {

            Element dcElement = XMLUtils.getElement(foxmlDocument, streamXPath);
            return handleDc(uuid, dcElement, false);

        } catch (XPathExpressionException e) {
            LOGGER.error("An error occured during obtaining the DC stream from the file: " + filePath + " "
                    + e);
            throw new ActionException(e);
        }
    }

    private Foxml getFoxml(String uuid, org.w3c.dom.Document foxmlDocument) {
        Foxml foxml = handleFoxml(uuid, getFedoraAccess());
        foxml.setLabel(FoxmlUtils.getLabel(foxmlDocument));
        return foxml;
    }

    /**
     * Gets the fedora access.
     * 
     * @return the fedora access
     */
    private FedoraAccess getFedoraAccess() {
        return fedoraAccess;
    }

    /**
     * Gets dc.
     * 
     * @param uuid
     *        the uuid
     * @param onlyTitleAndUuid
     *        the only title and uuid
     * @return the dublin core
     */
    private DublinCore getDc(String uuid, boolean onlyTitleAndUuid) {
        org.w3c.dom.Document dcDocument = null;
        try {
            dcDocument = getFedoraAccess().getDC(uuid);
        } catch (IOException e) {
            LOGGER.error("Unable to get DC metadata for " + uuid + "[" + e.getMessage() + "]", e);
        }
        return handleDc(uuid, dcDocument.getDocumentElement(), onlyTitleAndUuid);
    }
}
