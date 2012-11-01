package cz.mzk.editor.server.quartz;

import com.google.inject.Injector;
import com.gwtplatform.dispatch.shared.ActionException;
import cz.mzk.editor.server.handler.ConvertToJPEG2000Handler;
import cz.mzk.editor.server.handler.ScanFolderHandler;
import cz.mzk.editor.shared.rpc.ImageItem;
import cz.mzk.editor.shared.rpc.action.ConvertToJPEG2000Action;
import cz.mzk.editor.shared.rpc.action.ScanFolderAction;
import cz.mzk.editor.shared.rpc.action.ScanFolderResult;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.util.List;

/**
 * @author: Martin Rumanek
 * @version: 1.11.12
 */
public class ConvertRecords implements InterruptableJob {

    private boolean continueWithNext = true;

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(ConvertImages.class.getPackage().toString());

    private Injector guice = null;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        guice = (Injector) dataMap.get("Injector");

        String model = dataMap.getString("model");
        String code = dataMap.getString("code");
        ScanFolderAction action = new ScanFolderAction(model, code, null);
        ScanFolderHandler scanFolderHandler = guice.getInstance(ScanFolderHandler.class);

        try {
            ScanFolderResult result = scanFolderHandler.execute(action, null);
            convert(result);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }

    private void convert(ScanFolderResult result) {
        LOGGER.debug("convert start");
        final List<ImageItem> itemList = result == null ? null : result.getItems();
        final List<ImageItem> toAdd = result == null ? null : result.getToAdd();
        if (toAdd != null && !toAdd.isEmpty()) {
            for (ImageItem item : toAdd) {
                if (!continueWithNext) break;
                convertItem(item, itemList);
            }
        } else {
            //doTheRest(itemList); TODO?
        }

    }

    private void convertItem(ImageItem item, final List<ImageItem> itemList) {
        ConvertToJPEG2000Action action = new ConvertToJPEG2000Action(item);
        ConvertToJPEG2000Handler handler = guice.getInstance(ConvertToJPEG2000Handler.class);
        try {
            handler.execute(action, null);
        } catch (ActionException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        continueWithNext = false;
    }
}
