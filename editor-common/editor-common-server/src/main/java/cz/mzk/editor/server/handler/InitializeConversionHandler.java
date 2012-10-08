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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.convert.Converter;
import cz.mzk.editor.shared.rpc.action.InitializeConversionAction;
import cz.mzk.editor.shared.rpc.action.InitializeConversionResult;

/**
 * @author Jiri Kremser
 * @version 27.4.2012
 */
public class InitializeConversionHandler
        implements ActionHandler<InitializeConversionAction, InitializeConversionResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(InitializeConversionHandler.class.getPackage()
            .toString());

    private static final String CONVERSION_START_TIME = "conversion.start";

    private static final Long MAX_CONVERSION_TIME = 1000L * 60 * 20;

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /**
     * Instantiates a new scan input queue handler.
     * 
     * @param configuration
     *        the configuration
     */
    @Inject
    public InitializeConversionHandler(final EditorConfiguration configuration) {
        this.configuration = configuration;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public InitializeConversionResult execute(final InitializeConversionAction action,
                                              final ExecutionContext exContext) throws ActionException {
        // parse input
        final boolean start = action.isStart();
        Converter converter = Converter.getInstance();
        if (!configuration.getAkkaOn()) {
            return new InitializeConversionResult(true);
        }
        HttpSession session = httpSessionProvider.get();
        ServletContext context = session.getServletContext();
        Long startTime = (Long) context.getAttribute(CONVERSION_START_TIME);
        boolean running = converter.isRunning() && startTime != null;
        boolean expired =
                startTime == null ? false : System.currentTimeMillis() - startTime > MAX_CONVERSION_TIME;

        if (start) {
            if (running && !expired) {
                return new InitializeConversionResult(false);
            } else if (running && expired) {
                converter.stop();
                LOGGER.info("Conversion stop (expired)");
                converter.start(configuration.getAkkaConvertWorkers());
                putConversionToken(context);
                LOGGER.info("Conversion start");
                return new InitializeConversionResult(true);
            } else {
                converter.start(configuration.getAkkaConvertWorkers());
                putConversionToken(context);
                LOGGER.info("Conversion start");
                return new InitializeConversionResult(true);
            }
        } else {
            if (converter.isRunning()) {
                converter.stop();
                removeConversionToken(context);
                LOGGER.info("Conversion stop");
                return new InitializeConversionResult(true);
            } else {
                return new InitializeConversionResult(false);
            }
        }
    }

    private void putConversionToken(ServletContext context) {
        context.setAttribute(CONVERSION_START_TIME, System.currentTimeMillis());
    }

    private void removeConversionToken(ServletContext context) {
        context.setAttribute(CONVERSION_START_TIME, null);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<InitializeConversionAction> getActionType() {
        return InitializeConversionAction.class;
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
    public void undo(InitializeConversionAction action,
                     InitializeConversionResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub

    }
}