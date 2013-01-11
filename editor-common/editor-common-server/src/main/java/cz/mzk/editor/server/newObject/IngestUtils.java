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

package cz.mzk.editor.server.newObject;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.DigitalObjectDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.config.EditorConfiguration.ServerConstants;
import cz.mzk.editor.server.util.RESTHelper;

/**
 * @author Matous Jobanek
 * @version Oct 23, 2012
 */
public class IngestUtils {

    public static final Logger LOGGER = Logger.getLogger(IngestUtils.class);
    private static final Logger INGEST_LOGGER = Logger.getLogger(ServerConstants.INGEST_LOG_ID);

    @Inject
    private static EditorConfiguration config;

    /** The dao utils. */
    @Inject
    private static DigitalObjectDAO digitalObjectDAO;

    /**
     * Ingest.
     * 
     * @param foxml
     *        the foxml
     * @param label
     *        the label
     * @param uuid
     *        the uuid
     * @param model
     *        the model
     * @param topLevelUuid
     *        the top level uuid
     * @param inputDirPath
     *        the input dir path
     * @return true, if successful
     */
    public static boolean ingest(String foxml,
                                 String label,
                                 String uuid,
                                 String model,
                                 String topLevelUuid,
                                 String inputDirPath) {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Ingesting the digital object with PID" + (!uuid.contains("uuid:") ? " uuid:" : " ")
                    + uuid + " label:" + label + ", model: " + model);
        }
        String login = config.getFedoraLogin();
        String password = config.getFedoraPassword();
        String url = config.getFedoraHost() + "/objects/new";
        boolean success = RESTHelper.post(url, foxml, login, password, false);

        if (LOGGER.isInfoEnabled() && success) {
            LOGGER.info("Object ingested -- uuid:" + uuid + " label: " + label + ", model: " + model);
        }

        if (topLevelUuid != null && inputDirPath != null && INGEST_LOGGER.isInfoEnabled()) {
            INGEST_LOGGER.info(String.format("%s %16s %s", uuid, model, label));
        }
        if (success) {
            if (topLevelUuid != null && inputDirPath != null) {
                try {
                    if (!uuid.equals(topLevelUuid)) {
                        digitalObjectDAO.insertNewDigitalObject(uuid,
                                                                model,
                                                                label,
                                                                inputDirPath,
                                                                topLevelUuid,
                                                                false);
                    } else {
                        digitalObjectDAO.updateTopObjectTime(uuid);
                    }
                } catch (DatabaseException e) {
                    LOGGER.error("DB ERROR!!!: " + e.getMessage() + ": " + e);
                    e.printStackTrace();
                }
            }
        } else {
            LOGGER.error("Unable to ingest object uuid:" + uuid + " label: " + label + ", model: " + model);
        }
        return success;
    }
}
