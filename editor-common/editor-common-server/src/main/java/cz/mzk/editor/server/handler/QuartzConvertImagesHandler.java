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

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import cz.mzk.editor.server.quartz.ConvertImages;
import cz.mzk.editor.server.quartz.Quartz;
import cz.mzk.editor.shared.rpc.action.QuartzConvertImagesAction;
import cz.mzk.editor.shared.rpc.action.QuartzConvertImagesResult;
import cz.mzk.editor.shared.rpc.action.ScanFolderAction;
import cz.mzk.editor.shared.rpc.action.ScanFolderResult;

/**
 * @author Martin Rumanek
 * @version Aug 27, 2012
 */
public class QuartzConvertImagesHandler

        implements ActionHandler<QuartzConvertImagesAction, QuartzConvertImagesResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(QuartzConvertImagesHandler.class.getPackage()
            .toString());
    @Inject
    Quartz quartz;

    @Inject
    ScanFolderHandler scanFolderHandler;

    /**
     * {@inheritDoc}
     */
    @Override
    public QuartzConvertImagesResult execute(QuartzConvertImagesAction action, ExecutionContext context)
            throws ActionException {

        //        TODO
        //        LOGGER.debug("Processing action: ");
        //        ServerUtils.checkExpiredSession();
        // 

        JobDetail job =
                JobBuilder.newJob(ConvertImages.class)
                        .withIdentity(action.getModel() + ":" + action.getCode(), "Konverze")
                        .usingJobData("model", action.getModel()).usingJobData("code", action.getCode())
                        .build();

        Trigger trigger = TriggerBuilder.newTrigger().startNow().build();

        try {
            quartz.getScheduler().scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            LOGGER.error(e.getMessage());
        }

        ScanFolderAction scanAction = new ScanFolderAction(action.getModel(), action.getCode(), null);

        Integer numberOfImages = null;
        try {
            ScanFolderResult result = scanFolderHandler.execute(scanAction, null);
            numberOfImages = result.getToAdd().size();

        } catch (ActionException e) {

            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return new QuartzConvertImagesResult(numberOfImages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<QuartzConvertImagesAction> getActionType() {
        return QuartzConvertImagesAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(QuartzConvertImagesAction action,
                     QuartzConvertImagesResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub

    }
}
