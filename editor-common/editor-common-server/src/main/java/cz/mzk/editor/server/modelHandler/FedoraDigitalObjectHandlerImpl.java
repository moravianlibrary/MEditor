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

package cz.mzk.editor.server.modelHandler;

import cz.mzk.editor.client.mods.ModsCollectionClient;
import cz.mzk.editor.server.fedora.FedoraAccess;
import cz.mzk.editor.server.fedora.utils.FedoraUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.DigitalObjectDetail;
import cz.mzk.editor.shared.rpc.DublinCore;
import cz.mzk.editor.shared.rpc.Foxml;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class IDigitalObjectHandler.
 */
@Service
public class FedoraDigitalObjectHandlerImpl
        extends DigitalObjectHandler
        implements FedoraDigitalObjectHandler {

    /** The fedora access. */
    private final FedoraAccess fedoraAccess;
    private static final Logger LOGGER = Logger.getLogger(FedoraDigitalObjectHandlerImpl.class);

    /**
     * Instantiates a new fedora digital object handler.
     * 
     * @param fedoraAccess
     *        the fedora access
     */
    @Inject
    public FedoraDigitalObjectHandlerImpl(@Qualifier("securedFedoraAccess") FedoraAccess fedoraAccess) {
        this.fedoraAccess = fedoraAccess;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.server.modelHandler.CanGetObject#getDigitalObject
     * (java.lang.String)
     */
    @Override
    public DigitalObjectDetail getDigitalObject(String uuid) throws IOException {
        DigitalObjectModel model = FedoraUtils.getModel(uuid);
        DigitalObjectDetail detail = new DigitalObjectDetail(model, FedoraUtils.getRelated(uuid));
        detail.setDc(getDc(uuid, false));
        detail.setMods(getMods(uuid));
        Foxml foxml = handleFoxml(uuid, getFedoraAccess());
        detail.setFoxmlString(foxml.getFoxml());
        detail.setLabel(foxml.getLabel());
        detail.setOcr(handleOCR(uuid, getFedoraAccess()));
        detail.setFirstPageURL(FedoraUtils.findFirstPagePid(uuid));
        detail.setPdf(foxml.isPdf());
        return detail;
    }

    @Override
    public DigitalObjectDetail getDigitalObjectItems(String uuid, DigitalObjectModel childModel)
            throws IOException {
        DigitalObjectModel parentModel = FedoraUtils.getModel(uuid);
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
        detail.setDc(getDc(uuid, true));
        return detail;
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
        Document dcDocument = null;
        try {
            dcDocument = getFedoraAccess().getDC(uuid);
        } catch (IOException e) {
            LOGGER.error("Unable to get DC metadata for " + uuid + "[" + e.getMessage() + "]", e);
        }
        if (dcDocument != null) {
            return handleDc(uuid, dcDocument.getDocumentElement(), onlyTitleAndUuid);
        }
        return null;
    }

    /**
     * Handle mods.
     * 
     * @param uuid
     *        the uuid
     * @return the mods collection client
     */
    private ModsCollectionClient getMods(String uuid) {
        Document modsDocument = null;
        try {
            modsDocument = getFedoraAccess().getBiblioMods(uuid);
        } catch (IOException e) {
            LOGGER.error("Unable to get MODS metadata for " + uuid + "[" + e.getMessage() + "]", e);
        } catch (Exception e) {
            LOGGER.error("Unable to get MODS metadata for " + uuid + "[" + e.getMessage() + "]", e);
        }
        return handleMods(modsDocument);
    }
}
