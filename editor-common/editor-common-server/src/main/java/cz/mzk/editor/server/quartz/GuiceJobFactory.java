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

package cz.mzk.editor.server.quartz;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

/**
 * @author Martin Rumanek
 * @version Aug 27, 2012
 */
public class GuiceJobFactory
        implements JobFactory {


    @Inject
    public GuiceJobFactory(final Injector guice) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler arg1) throws SchedulerException {
//        JobDetail jobDetail = bundle.getJobDetail();
//        jobDetail.getJobDataMap().put("Injector", guice);
//        Class<? extends Job> jobClass = jobDetail.getJobClass();
//        return guice.getInstance(jobClass);
        return null;
    }
}
