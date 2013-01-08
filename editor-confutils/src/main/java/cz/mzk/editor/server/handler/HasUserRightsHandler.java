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

import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.HasUserRightsAction;
import cz.mzk.editor.shared.rpc.action.HasUserRightsResult;

/**
 * @author Matous Jobanek
 * @version Dec 20, 2012
 */
public class HasUserRightsHandler
        implements ActionHandler<HasUserRightsAction, HasUserRightsResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(HasUserRightsHandler.class.getPackage().toString());

    /**
     * {@inheritDoc}
     */
    @Override
    public HasUserRightsResult execute(HasUserRightsAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: HasUserRightsAction");
        ServerUtils.checkExpiredSession();

        Boolean[] ok = new Boolean[action.getRights().length];
        for (int i = 0; i < ok.length; i++)
            ok[i] = false;
        int index = 0;
        for (EDITOR_RIGHTS right : action.getRights()) {

            ok[index++] = (ServerUtils.checkUserRightOrAll(right));
        }

        return new HasUserRightsResult(ok);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<HasUserRightsAction> getActionType() {
        return HasUserRightsAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(HasUserRightsAction arg0, HasUserRightsResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub

    }

}
