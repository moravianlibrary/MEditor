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

import java.text.ParseException;

import java.util.HashMap;
import java.util.List;

import cz.mzk.editor.client.util.Constants.STATISTICS_SEGMENTATION;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.HistoryItem;
import cz.mzk.editor.shared.rpc.HistoryItemInfo;
import cz.mzk.editor.shared.rpc.IntervalStatisticData;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public interface ActionDAO {

    /**
     * Gets the history days.
     * 
     * @param userId
     *        the user id
     * @param uuid
     *        the uuid
     * @return the history days
     * @throws DatabaseException
     *         the database exception
     */
    public List<EditorDate> getHistoryDays(Long userId, String uuid) throws DatabaseException;

    /**
     * Gets the history items.
     * 
     * @param editorUserId
     *        the editor user id
     * @param uuid
     *        the uuid
     * @param lowerLimit
     *        the lower limit
     * @param upperLimit
     *        the upper limit
     * @return the history items
     * @throws DatabaseException
     *         the database exception
     */
    List<HistoryItem> getHistoryItems(Long editorUserId,
                                      String uuid,
                                      EditorDate lowerLimit,
                                      EditorDate upperLimit) throws DatabaseException;

    /**
     * Gets the history item info.
     * 
     * @param id
     *        the id
     * @param tableName
     *        the table name
     * @return the history item info
     * @throws DatabaseException
     *         the database exception
     */
    HistoryItemInfo getHistoryItemInfo(Long id, String tableName) throws DatabaseException;

    /**
     * Gets the user statistics data.
     * 
     * @param userId
     *        the user id
     * @param model
     *        the model
     * @param dateFrom
     *        the date from
     * @param dateTo
     *        the date to
     * @param segmentation
     *        the segmentation
     * @return the user statistics data
     * @throws DatabaseException
     *         the database exception
     * @throws ParseException
     */
    HashMap<Integer, IntervalStatisticData> getUserStatisticsData(Long userId,
                                                                  DigitalObjectModel model,
                                                                  EditorDate dateFrom,
                                                                  EditorDate dateTo,
                                                                  STATISTICS_SEGMENTATION segmentation)
            throws DatabaseException, ParseException;
}
