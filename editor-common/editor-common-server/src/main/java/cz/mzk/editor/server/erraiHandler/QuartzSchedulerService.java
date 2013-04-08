package cz.mzk.editor.server.erraiHandler;

import cz.mzk.editor.server.quartz.Quartz;
import cz.mzk.editor.server.quartz.jobs.ConvertImages;
import cz.mzk.editor.shared.erraiPortable.QuartzJobAction;
import cz.mzk.editor.shared.rpc.ProcessItem;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.framework.MessageBus;
import org.jboss.errai.bus.client.framework.RequestDispatcher;
import org.jboss.errai.bus.server.annotations.Service;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.JobKey;

import javax.inject.Inject;

@Service
public class QuartzSchedulerService {

    @Inject
    public QuartzSchedulerService(final RequestDispatcher dispatcher, final Quartz quartz) {

        ConvertImages.setErraiDispatcher(dispatcher);

        try {
            quartz.getScheduler().getListenerManager().addJobListener(new JobListener() {
                @Override
                public String getName() {
                    return "Quartz Scheduler Service";
                }

                @Override
                public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
                    try {
                        JobKey jobDetail = jobExecutionContext.getJobDetail().getKey();
                        MessageBuilder.createMessage()
                                .toSubject("QuartzBroadcastReceiver")
                                .with("jobDetail", new QuartzJobAction(jobDetail.getGroup(),
                                        jobDetail.getName(), QuartzJobAction.Action.TO_BE_EXECUTED))
                                .noErrorHandling().sendNowWith(dispatcher);
                    } catch (Exception e) {

                    }

                }

                @Override
                public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
                }

                @Override
                public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
                    try {
                        JobKey jobDetail = jobExecutionContext.getJobDetail().getKey();
                        MessageBuilder.createMessage()
                                .toSubject("QuartzBroadcastReceiver")
                                .with("jobDetail", new QuartzJobAction(jobDetail.getGroup(),
                                        jobDetail.getName(), QuartzJobAction.Action.WAS_EXECUTED))
                                .noErrorHandling().sendNowWith(dispatcher);
                    } catch (Exception ef) {

                    }
                }

            });
        } catch (SchedulerException e) {
            //TODO-MR log
        }

    }


}
