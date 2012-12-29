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

package cz.mzk.editor.server.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.InputQueueItem;

/**
 * @author Matous Jobanek
 * @version Oct 24, 2012
 */
public class ConversionDAOImpl
        extends AbstractDAO
        implements ConversionDAO {

    private static final Logger LOGGER = Logger.getLogger(ConversionDAOImpl.class);

    //    conversion (id, editor_user_id, timestamp, input_queue_directory_path)

    /** The Constant INSERT_CONVERSION_ITEM_STATEMENT. */
    public static final String INSERT_CONVERSION_ITEM_STATEMENT =
            "INSERT INTO "
                    + Constants.TABLE_CONVERSION
                    + " (editor_user_id, timestamp, input_queue_directory_path) VALUES ((?), (CURRENT_TIMESTAMP), (?))";

    public static final String SELECT_CONVERSION_INFO =
            "SELECT lastTimestamp, (lastTimestamp < (NOW() - INTERVAL '%s day')) AS isOlder FROM (SELECT MAX(timestamp) AS lastTimestamp FROM "
                    + Constants.TABLE_CONVERSION
                    + " WHERE input_queue_directory_path = (?) GROUP BY timestamp ORDER BY timestamp DESC LIMIT '1') c";

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertConversionInfo(String directoryPath) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            if (daoUtils.checkInputQueue(directoryPath, null, true)) {
                insertSt = getConnection().prepareStatement(INSERT_CONVERSION_ITEM_STATEMENT);
                insertSt.setLong(1, getUserId(true));
                insertSt.setString(2, DAOUtilsImpl.directoryPathToRightFormat(directoryPath));

                if (insertSt.executeUpdate() == 1) {
                    LOGGER.debug("DB has been updated: The directory: " + directoryPath
                            + " has been converted.");
                } else {
                    LOGGER.error("DB has not been updated! " + insertSt);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<InputQueueItem> getConversionInfo(ArrayList<InputQueueItem> data, int numberOfDays)
            throws DatabaseException {
        PreparedStatement selectSt = null;

        for (InputQueueItem item : data) {
            try {
                selectSt =
                        getConnection().prepareStatement(String.format(SELECT_CONVERSION_INFO,
                                                                       String.valueOf(numberOfDays)));

                selectSt.setString(1, DAOUtilsImpl.directoryPathToRightFormat(item.getPath()));
                ResultSet rs = selectSt.executeQuery();

                if (rs.next()) {
                    item.setConversionDate(formatTimestampToSeconds(rs.getTimestamp("lastTimestamp")));
                    if (numberOfDays > 0) {
                        item.setConverted(!rs.getBoolean("isOlder"));
                    } else {
                        item.setConverted(true);
                    }
                }

            } catch (SQLException e) {
                LOGGER.error("Select statement: " + selectSt + " " + e.getMessage());
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }
        return data;
    }
}
