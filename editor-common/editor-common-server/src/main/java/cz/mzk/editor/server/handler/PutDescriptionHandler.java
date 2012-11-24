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

package cz.mzk.editor.server.handler;

import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.DescriptionDAO;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.PutDescriptionAction;
import cz.mzk.editor.shared.rpc.action.PutDescriptionResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class PutDescriptionHandler
        implements ActionHandler<PutDescriptionAction, PutDescriptionResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger
            .getLogger(PutDescriptionHandler.class.getPackage().toString());

    /** The description dao. */
    @Inject
    private DescriptionDAO descriptionDAO;

    /** The user dao. */
    @Inject
    UserDAO userDAO;

    /**
     * Instantiates a new put recently modified handler.
     */
    @Inject
    public PutDescriptionHandler() {
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public PutDescriptionResult execute(final PutDescriptionAction action, final ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: PutDescriptionAction " + action.getUuid());
        ServerUtils.checkExpiredSession();

        if (action.getUuid() == null || "".equals(action.getUuid()))
            throw new NullPointerException("getUuid()");
        if (action.getDescription() == null) throw new NullPointerException("getDescription()");

        try {
            descriptionDAO.checkUserDescription(action.getUuid(), action.getDescription());
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }
        return new PutDescriptionResult();

    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<PutDescriptionAction> getActionType() {
        return PutDescriptionAction.class;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
     * gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.shared.Result,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public void undo(PutDescriptionAction action, PutDescriptionResult result, ExecutionContext context)
            throws ActionException {
        // TODO undo method

    }
}