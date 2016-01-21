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

import java.io.File;
import java.io.FileFilter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.ImageResolverDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.FindAltoOcrFilesAction;
import cz.mzk.editor.shared.rpc.action.FindAltoOcrFilesResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class FindAltoOcrFilesHandler
        implements ActionHandler<FindAltoOcrFilesAction, FindAltoOcrFilesResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(FindAltoOcrFilesHandler.class.getPackage()
            .toString());

    /** The input queue dao. */
    @Inject
    private ImageResolverDAO imageResolverDAO;

    private static final String ALTO = "ALTO";
    private static final String TXT = "txt";

    /**
     * {@inheritDoc}
     */

    @Override
    public FindAltoOcrFilesResult execute(FindAltoOcrFilesAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: FindAltoOcrFilesHandler");
        ServerUtils.checkExpiredSession();

        String oldJpgFsPath = null;

        try {
            oldJpgFsPath = imageResolverDAO.getOldJpgFsPath(action.getPath());
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }

        if (oldJpgFsPath != null) {
            String dirPath = oldJpgFsPath.substring(0, oldJpgFsPath.lastIndexOf("/"));
            List<String> foundAltoList = findAltoAndTxt(dirPath, ALTO);
            List<String> foundTxtList = findAltoAndTxt(dirPath, TXT);
            return new FindAltoOcrFilesResult(foundAltoList, foundTxtList);
        } else {
            return new FindAltoOcrFilesResult(null, null);
        }
    }

    /**
     * Scan directory structure.
     * 
     * @param path
     *        the path
     * @return the list
     */
    private List<String> findAltoAndTxt(String path, final String typeToFind) {

        List<String> fileNames = new ArrayList<String>();
        File pathFile = new File(path + File.separator + typeToFind + File.separator);

        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                if (typeToFind.equals(ALTO)) {
                    //                    return file.isFile() && file.getName().toLowerCase().contains("alto")
                    //&& 
                    return file.isFile() && file.getName().toLowerCase().endsWith(".xml");
                } else {
                    return file.isFile() && file.getName().toLowerCase().endsWith(".txt");
                }
            }
        };

        File[] files = pathFile.listFiles(filter);
        for (int i = 0; (files != null && i < files.length); i++) {

            if (typeToFind.equals(ALTO)) {
                SAXReader reader = new SAXReader();
                try {
                    reader.setValidation(true);
                    reader.read(files[i]);
                } catch (DocumentException e) {
                }
            }

            fileNames.add(path + File.separator + typeToFind + File.separator + files[i].getName());
        }
        return fileNames;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<FindAltoOcrFilesAction> getActionType() {
        return FindAltoOcrFilesAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(FindAltoOcrFilesAction arg0, FindAltoOcrFilesResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub
    }

}
