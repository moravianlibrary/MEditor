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

package cz.fi.muni.xkremser.editor.server.handler;

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;

import cz.fi.muni.xkremser.editor.shared.rpc.action.DownloadDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.DownloadDigitalObjectDetailResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class DownloadDigitalObjectDetailHandler
        implements ActionHandler<DownloadDigitalObjectDetailAction, DownloadDigitalObjectDetailResult> {

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /**
     * {@inheritDoc}
     */

    @Override
    public DownloadDigitalObjectDetailResult execute(DownloadDigitalObjectDetailAction action,
                                                     ExecutionContext context) throws ActionException {

        if (action == null || action.getDetail() == null) throw new NullPointerException("getDetail()");
        HttpSession session = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(session);

        return new DownloadDigitalObjectDetailResult(FedoraUtils.createWorkingCopyFoxmlAndStreams(action
                .getDetail(), false));

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<DownloadDigitalObjectDetailAction> getActionType() {
        return DownloadDigitalObjectDetailAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(DownloadDigitalObjectDetailAction arg0,
                     DownloadDigitalObjectDetailResult arg1,
                     ExecutionContext arg2) throws ActionException {
        // TODO Auto-generated method stub
    }
}
