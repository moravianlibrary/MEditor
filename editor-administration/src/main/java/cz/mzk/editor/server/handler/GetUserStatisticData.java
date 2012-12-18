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

package cz.mzk.editor.server.handler;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants.STATISTICS_SEGMENTATION;
import cz.mzk.editor.server.util.EditorDateUtils;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.action.GetUserStatisticDataAction;
import cz.mzk.editor.shared.rpc.action.GetUserStatisticDataResult;

/**
 * @author Matous Jobanek
 * @version Dec 18, 2012
 */
public class GetUserStatisticData
        implements ActionHandler<GetUserStatisticDataAction, GetUserStatisticDataResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(GetUsersInfoHandler.class.getPackage().toString());

    /**
     * {@inheritDoc}
     */
    @Override
    public GetUserStatisticDataResult execute(GetUserStatisticDataAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetUsersInfoAction");
        ServerUtils.checkExpiredSession();

        long userId = Long.parseLong(action.getUserId());

        //        action.getDateFrom()

        EditorDate fromDate = EditorDateUtils.getEditorDate(action.getDateFrom(), true);
        EditorDate toDate = EditorDateUtils.getEditorDate(action.getDateTo(), true);
        DigitalObjectModel model = DigitalObjectModel.parseString(action.getModel());
        STATISTICS_SEGMENTATION segmentation = STATISTICS_SEGMENTATION.parseString(action.getSegmentation());

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<GetUserStatisticDataAction> getActionType() {
        return GetUserStatisticDataAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(GetUserStatisticDataAction arg0, GetUserStatisticDataResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub

    }

}
