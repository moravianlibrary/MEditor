package cz.mzk.editor.server.quartz.jobs;

import cz.mzk.editor.shared.erraiPortable.QuartzJobAction;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.api.messaging.RequestDispatcher;
import org.quartz.JobKey;

/**
 * @author: Martin Rumanek
 * @version: 9.4.13
 */
public abstract class ProgressJob {
    private static RequestDispatcher erraiDispatcher;
    private int percentDone = 0;
    private JobKey jobKey;

    protected static RequestDispatcher getErraiDispatcher() {
        return erraiDispatcher;
    }

    public static void setErraiDispatcher(RequestDispatcher erraiDispatcher) {
        ProgressJob.erraiDispatcher = erraiDispatcher;
    }

    public int getPercentDone() {
        return percentDone;
    }

    protected void setPercentDone(int percentDone) {
        if (erraiDispatcher != null && jobKey != null) {
            if (percentDone != this.percentDone) {
                MessageBuilder.createMessage()
                        .toSubject("QuartzBroadcastReceiver").signalling()
                        .with("jobDetail", new QuartzJobAction(jobKey.getGroup(),
                                jobKey.getName(), QuartzJobAction.Action.PROGRESS, percentDone))
                        .noErrorHandling().sendNowWith(erraiDispatcher);
            }
        }
        this.percentDone = percentDone;
    }

    protected void setJobKey(JobKey jobKey) {
        this.jobKey = jobKey;
    }
}
