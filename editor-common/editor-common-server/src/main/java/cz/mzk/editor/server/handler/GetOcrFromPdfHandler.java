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
import java.io.FileInputStream;

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.GetOcrFromPdfAction;
import cz.mzk.editor.shared.rpc.action.GetOcrFromPdfResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class GetOcrFromPdfHandler
        implements ActionHandler<GetOcrFromPdfAction, GetOcrFromPdfResult> {

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    @Inject
    private EditorConfiguration conf;

    private static final Logger LOGGER = Logger.getLogger(GetOcrFromPdfHandler.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public GetOcrFromPdfResult execute(GetOcrFromPdfAction action, ExecutionContext context)
            throws ActionException {

        HttpSession session = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(session);

        return new GetOcrFromPdfResult(pdftoText(conf.getImagesPath() + File.separator + action.getUuid()
                + Constants.PDF_EXTENSION));
    }

    private String pdftoText(String fileName) throws ActionException {

        File pdfFile = new File(fileName);

        if (!pdfFile.isFile()) {
            LOGGER.error("The file: " + fileName + " does not exist.");
            throw new ActionException("Unable to parse the pdf file.");
        }

        PDFParser parser = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;
        PDDocument pdDoc = null;
        String parsedText;
        try {
            parser = new PDFParser(new FileInputStream(pdfFile));
        } catch (Exception e) {
            LOGGER.error("Unable to open PDF Parser.: " + e);
            e.printStackTrace();
            throw new ActionException("Unable to parse the pdf file.");
        }

        try {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
        } catch (Exception e) {
            LOGGER.error("An exception occured in parsing the PDF Document.");
            e.printStackTrace();
            throw new ActionException("Unable to parse the pdf file. " + e);
        } finally {
            try {
                if (cosDoc != null) cosDoc.close();
                if (pdDoc != null) pdDoc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return parsedText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<GetOcrFromPdfAction> getActionType() {
        // TODO Auto-generated method stub
        return GetOcrFromPdfAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(GetOcrFromPdfAction action, GetOcrFromPdfResult result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }

}
