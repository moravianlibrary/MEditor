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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.CheckRightsAction;
import cz.mzk.editor.shared.rpc.action.CheckRightsResult;

/**
 * @author Matous Jobanek
 * @version Nov 27, 2012
 */
public class CheckRightsHandler
        implements ActionHandler<CheckRightsAction, CheckRightsResult> {

    @Inject
    private UserDAO userDAO;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CheckRightsHandler.class.getPackage().toString());

    /**
     * {@inheritDoc}
     */
    @Override
    public CheckRightsResult execute(CheckRightsAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: CheckRightsAction");
        ServerUtils.checkExpiredSession();

        Map<String, List<String>> rightsRefByRole = null;
        Map<String, List<String>> rightsRefByUser = null;

        try {
            List<String> toRemove = userDAO.checkAllRights();

            if (toRemove != null && !toRemove.isEmpty()) {
                rightsRefByRole = new HashMap<String, List<String>>();
                rightsRefByUser = new HashMap<String, List<String>>();
                for (String toRemRight : toRemove) {
                    rightsRefByRole.put(toRemRight, new ArrayList<String>());
                    rightsRefByUser.put(toRemRight, new ArrayList<String>());
                }

                for (String toRemRight : toRemove) {
                    List<String> refRoles = userDAO.getReferencesToRight(toRemRight, true);
                    List<String> refUsers = userDAO.getReferencesToRight(toRemRight, false);
                    boolean toRem = true;

                    if (refRoles != null && !refRoles.isEmpty()) {
                        rightsRefByRole.get(toRemRight).addAll(refRoles);
                        toRem = false;
                    } else {
                        rightsRefByRole.remove(toRemRight);
                    }

                    if (refUsers != null && !refUsers.isEmpty()) {
                        rightsRefByUser.get(toRemRight).addAll(refUsers);
                        toRem = false;
                    } else {
                        rightsRefByRole.remove(toRemRight);
                    }

                    if (toRem) {
                        userDAO.removeRight(toRemRight);
                    }
                }
            }
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return new CheckRightsResult(rightsRefByRole, rightsRefByUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<CheckRightsAction> getActionType() {
        return CheckRightsAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(CheckRightsAction action, CheckRightsResult result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }

}
