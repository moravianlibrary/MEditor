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

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.UnableToInterruptJobException;

import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.quartz.Quartz;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.ProcessItem;
import cz.mzk.editor.shared.rpc.action.QuartzScheduleJobsAction;
import cz.mzk.editor.shared.rpc.action.QuartzScheduleJobsResult;

/**
 * @author Martin Rumanek
 * @version 29.8.2012
 */
public class QuartzScheduleJobsHandler
        implements ActionHandler<QuartzScheduleJobsAction, QuartzScheduleJobsResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(QuartzScheduleJobsHandler.class.getPackage()
            .toString());

    @Inject
    Quartz quartz;

    /**
     * {@inheritDoc}
     */
    @Override
    public QuartzScheduleJobsResult execute(QuartzScheduleJobsAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: QuartzScheduleJobsAction");
        ServerUtils.checkExpiredSession();

        if (!ServerUtils.checkUserRightOrAll(EDITOR_RIGHTS.LONG_RUNNING_PROCESS)) {
            LOGGER.warn("Bad authorization in " + this.getClass().toString());
            throw new ActionException("Bad authorization in " + this.getClass().toString());
        }

        if (action.getKillItem() != null) {
            JobKey key =
                    new JobKey(action.getKillItem().getProcessName(), action.getKillItem().getProcessGroup());

            try {
                quartz.getScheduler().interrupt(key);
            } catch (UnableToInterruptJobException e) {
                LOGGER.error(e.getMessage());
            }
        }

        List<ProcessItem> jobs = null;

        try {
            jobs = getCurrentlyRunningJobs();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return new QuartzScheduleJobsResult(jobs);
    }

    private List<ProcessItem> getCurrentlyRunningJobs() throws SchedulerException {
        List<ProcessItem> jobs = new ArrayList<ProcessItem>();

        for (JobExecutionContext context : quartz.getScheduler().getCurrentlyExecutingJobs()) {
            JobKey key = context.getJobDetail().getKey();
            jobs.add(new ProcessItem(key.getGroup(), key.getName()));
        }

        return jobs;

    }

    //    private List<String> getTriggers() throws SchedulerException {
    //        List<String> triggers = new ArrayList<String>();
    //        for (String group : quartz.getScheduler().getJobGroupNames()) {
    //            for (JobKey jobKey : quartz.getScheduler().getJobKeys(GroupMatcher.<JobKey> groupEquals(group))) {
    //                for (Trigger tg : quartz.getScheduler().getTriggersOfJob(jobKey)) {
    //                    triggers.add(tg.toString());
    //                }
    //            }
    //        }
    //        return triggers;
    //    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<QuartzScheduleJobsAction> getActionType() {
        return QuartzScheduleJobsAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(QuartzScheduleJobsAction action,
                     QuartzScheduleJobsResult result,
                     ExecutionContext context) throws ActionException {
        // TODO undo method

    }

}
