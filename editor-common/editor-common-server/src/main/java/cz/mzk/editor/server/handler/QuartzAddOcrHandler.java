package cz.mzk.editor.server.handler;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import cz.mzk.editor.server.quartz.Quartz;
import cz.mzk.editor.server.quartz.jobs.AddOcrToObjects;
import cz.mzk.editor.server.quartz.jobs.ConvertImages;
import cz.mzk.editor.shared.rpc.action.QuartzAddOcrAction;
import cz.mzk.editor.shared.rpc.action.QuartzAddOcrResult;
import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * @author: Martin Rumanek
 * @version: 19.2.13
 */
public class QuartzAddOcrHandler implements ActionHandler<QuartzAddOcrAction, QuartzAddOcrResult> {

    private static final Logger LOGGER = Logger.getLogger(QuartzConvertImagesHandler.class.getPackage()
            .toString());
    @Inject
    Quartz quartz;

    @Override
    public QuartzAddOcrResult execute(QuartzAddOcrAction action, ExecutionContext context) throws ActionException {

        JobDetail job =
                JobBuilder.newJob(AddOcrToObjects.class)
                        .withIdentity(action.getUuid(), "Ocr")
                        .usingJobData("uuid", action.getUuid())
                        .build();

        Trigger trigger = TriggerBuilder.newTrigger().startNow().build();

        try {
            quartz.getScheduler().scheduleJob(job, trigger);
            return new QuartzAddOcrResult(true);
        } catch (SchedulerException e) {
            LOGGER.error(e.getMessage());
        }

        return new QuartzAddOcrResult(false);
    }

    @Override
    public Class<QuartzAddOcrAction> getActionType() {
        return QuartzAddOcrAction.class;
    }

    @Override
    public void undo(QuartzAddOcrAction quartzAddOcrAction, QuartzAddOcrResult quartzAddOcrResult, ExecutionContext executionContext) throws ActionException {

    }
}
