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

import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.InputQueueItemDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.GetIngestInfoAction;
import cz.mzk.editor.shared.rpc.action.GetIngestInfoResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class GetIngestInfoHandler
        implements ActionHandler<GetIngestInfoAction, GetIngestInfoResult> {

    @Inject
    private InputQueueItemDAO inputQueueDAO;

    private static final Logger LOGGER = Logger.getLogger(GetIngestInfoHandler.class.getPackage().toString());

    /**
     * {@inheritDoc}
     */

    @Override
    public GetIngestInfoResult execute(GetIngestInfoAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetIngestInfoAction " + action.getPath());
        ServerUtils.checkExpiredSession();

        try {
            return new GetIngestInfoResult(inputQueueDAO.getIngestInfo(action.getPath()));
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new ActionException(e);
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<GetIngestInfoAction> getActionType() {
        return GetIngestInfoAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(GetIngestInfoAction arg0, GetIngestInfoResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub
    }

}
