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

package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;

import org.fedora.api.RelationshipTuple;

import cz.fi.muni.xkremser.editor.client.ConnectionException;
import cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.client.domain.FedoraNamespaces;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.DCUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;
import cz.fi.muni.xkremser.editor.server.valueobj.metadata.Foxml;
import cz.fi.muni.xkremser.editor.server.valueobj.metadata.FoxmlHandler;

import cz.fi.muni.xkremser.editor.shared.valueobj.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class IDigitalObjectHandler.
 */
public class DigitalObjectHandlerImpl
        implements DigitalObjectHandler {

    /** The fedora access. */
    private final FedoraAccess fedoraAccess;
    private static final Logger LOGGER = Logger.getLogger(DigitalObjectHandlerImpl.class);

    /**
     * Instantiates a new digital object handler.
     * 
     * @param fedoraAccess
     *        the fedora access
     */
    @Inject
    public DigitalObjectHandlerImpl(@Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
        this.fedoraAccess = fedoraAccess;
    }

    /**
     * Gets the related.
     * 
     * @param uuid
     *        the uuid
     * @return the related
     */
    private ArrayList<ArrayList<String>> getRelated(final String uuid) {
        List<RelationshipTuple> triplets = FedoraUtils.getSubjectPids(uuid);
        ArrayList<ArrayList<String>> returnList = new ArrayList<ArrayList<String>>(triplets.size());
        for (RelationshipTuple triplet : triplets) {
            ArrayList<String> relatedRecord = new ArrayList<String>(2);
            String subject = triplet.getSubject().substring((Constants.FEDORA_INFO_PREFIX).length());
            String predicate = null;
            if (triplet.getPredicate().startsWith(Constants.FEDORA_INFO_PREFIX)) {
                predicate = triplet.getPredicate().substring(Constants.FEDORA_INFO_PREFIX.length());
            } else {
                predicate =
                        triplet.getPredicate()
                                .substring(FedoraNamespaces.ONTOLOGY_RELATIONSHIP_NAMESPACE_URI.length());
            }

            relatedRecord.add(subject);
            relatedRecord.add(predicate);
            returnList.add(relatedRecord);
        }
        return returnList;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.server.modelHandler.CanGetObject#getDigitalObject
     * (java.lang.String)
     */

    @Override
    public DigitalObjectDetail getDigitalObject(String uuid) throws IOException {
        DigitalObjectModel model = getModel(uuid);
        DigitalObjectDetail detail = new DigitalObjectDetail(model, getRelated(uuid));
        detail.setDc(handleDc(uuid, false));
        detail.setMods(handleMods(uuid));
        Foxml foxml = FoxmlHandler.handleFoxml(uuid, getFedoraAccess());
        detail.setFoxmlString(foxml.getFoxml());
        detail.setLabel(foxml.getLabel());
        detail.setOcr(handleOCR(uuid));
        detail.setFirstPageURL(FedoraUtils.findFirstPagePid(uuid));
        return detail;
    }

    @Override
    public DigitalObjectDetail getDigitalObjectItems(String uuid, DigitalObjectModel childModel)
            throws IOException {
        DigitalObjectModel parentModel = getModel(uuid);
        DigitalObjectDetail detail = new DigitalObjectDetail();
        List<String> uuids = fedoraAccess.getChildrenUuid(uuid, parentModel, childModel);
        List<DigitalObjectDetail> children = new ArrayList<DigitalObjectDetail>(uuids.size());
        for (String pid : uuids) {
            children.add(getDigitalObjectName(pid));
        }
        detail.setItems(children);
        return detail;
    }

    private DigitalObjectDetail getDigitalObjectName(String uuid) throws IOException {
        DigitalObjectDetail detail = new DigitalObjectDetail();
        detail.setDc(handleDc(uuid, true));
        return detail;
    }

    private DigitalObjectModel getModel(String uuid) throws IOException {
        DigitalObjectModel model = null;
        try {
            model = fedoraAccess.getDigitalObjectModel(uuid);
        } catch (ConnectionException e) {
            LOGGER.error("Digital object " + uuid + " is not in the repository. " + e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            LOGGER.warn("Could not get model of object " + uuid + ". Using generic model handler.", e);
            throw e;
        }
        return model;
    }

    /**
     * Gets the fedora access.
     * 
     * @return the fedora access
     */
    public FedoraAccess getFedoraAccess() {
        return fedoraAccess;
    }

    /**
     * Handle dc.
     * 
     * @param uuid
     *        the uuid
     * @param onlyTitleAndUuid
     *        the only title and uuid
     * @return the dublin core
     */
    protected DublinCore handleDc(String uuid, boolean onlyTitleAndUuid) {
        DublinCore dc = null;
        Document dcDocument = null;
        try {
            dcDocument = getFedoraAccess().getDC(uuid);
            if (onlyTitleAndUuid) {
                dc = new DublinCore();
                dc.addTitle(DCUtils.titleFromDC(dcDocument));
                dc.addIdentifier(uuid);
            } else {
                dc = DCUtils.getDC(dcDocument);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to get DC metadata for " + uuid + "[" + e.getMessage() + "]", e);
        }
        return dc;
    }

    /**
     * Handle ocr.
     * 
     * @param uuid
     *        the uuid
     * @return the string
     */
    protected String handleOCR(String uuid) {
        return getFedoraAccess().getOcr(uuid);
    }

    /**
     * Handle mods.
     * 
     * @param uuid
     *        the uuid
     * @return the mods collection client
     */
    protected ModsCollectionClient handleMods(String uuid) {
        ModsCollectionClient modsClient = null;
        ModsCollection mods = null;
        Document modsDocument = null;
        try {
            modsDocument = getFedoraAccess().getBiblioMods(uuid);
            mods = BiblioModsUtils.getMods(modsDocument);
            modsClient = BiblioModsUtils.toModsClient(mods);
        } catch (IOException e) {
            LOGGER.error("Unable to get MODS metadata for " + uuid + "[" + e.getMessage() + "]", e);
        } catch (Exception e) {
            LOGGER.error("Unable to get MODS metadata for " + uuid + "[" + e.getMessage() + "]", e);
        }
        return modsClient;
    }

}
