/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2012  Martin Rumanek (martin.rumanek@mzk.cz)
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

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.ImageResolverDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.FindAltoOcrFilesBatchAction;
import cz.mzk.editor.shared.rpc.action.FindAltoOcrFilesBatchResult;

/**
 * @author Martin Rumanek
 * @version Aug 20, 2012
 */
public class FindAltoOcrFilesBatchHandler
        implements ActionHandler<FindAltoOcrFilesBatchAction, FindAltoOcrFilesBatchResult> {

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /** The input queue dao. */
    @Inject
    private ImageResolverDAO imageResolverDAO;

    private static final String ALTO = "ALTO";
    private static final String TXT = "txt";

    private static final String ALTOsuffix = ".xml";
    private static final String TXTsuffix = ".txt";

    /**
     *  {@inheritDoc}
     */
    @Override
    public FindAltoOcrFilesBatchResult execute(FindAltoOcrFilesBatchAction action, ExecutionContext context)
            throws ActionException {

        Map<String, String> altoFileNames = new HashMap<String, String>();
        Map<String, String> ocrFileNames = new HashMap<String, String>();

        HttpSession session = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(session);

        for (String path : action.getPaths()) {
            try {
                String oldJpgFsPath = imageResolverDAO.getOldJpgFsPath(path);
                if (oldJpgFsPath != null) {
                    altoFileNames.put(path, findAltoAndTxt(oldJpgFsPath, ALTO));
                    ocrFileNames.put(path, findAltoAndTxt(oldJpgFsPath, TXT));
                }

            } catch (DatabaseException e) {
                throw new ActionException(e);
            }
        }
        return new FindAltoOcrFilesBatchResult(altoFileNames, ocrFileNames);

    }

    private String findAltoAndTxt(String path, final String typeToFind) {
        String dirPath = path.substring(0, path.lastIndexOf("/"));
        String name = path.substring(path.lastIndexOf("/") + 1);
        String nameWithoutSuffix = name.substring(0, name.lastIndexOf("."));
        if (typeToFind.equals(ALTO)) {
            File alto =
                    new File(dirPath + File.separator + typeToFind + File.separator + nameWithoutSuffix
                            + ALTOsuffix);
            if (alto.exists()) {
                return alto.getAbsolutePath();
            }
        } else if (typeToFind.equals(TXT)) {
            File txt =
                    new File(dirPath + File.separator + typeToFind + File.separator + nameWithoutSuffix
                            + TXTsuffix);
            if (txt.exists()) {
                return txt.getAbsolutePath();
            }
        }
        return null;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<FindAltoOcrFilesBatchAction> getActionType() {
        return FindAltoOcrFilesBatchAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(FindAltoOcrFilesBatchAction arg0, FindAltoOcrFilesBatchResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub

    }

}
