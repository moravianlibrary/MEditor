package cz.mzk.editor.shared.erraiPortable;

import cz.mzk.editor.shared.rpc.ProcessItem;
import org.jboss.errai.common.client.api.annotations.Portable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Martin Rumanek
 * @version: 21.3.13
 */

@Portable
public class QuartzJobAction {

    @Portable
    public static enum Action {
        TO_BE_EXECUTED,EXECUTION_VETOED, WAS_EXECUTED
    }

    private String processGroup;

    private String processName;

    private Action action;

    public QuartzJobAction() {
    }

    public QuartzJobAction(String processGroup, String processName, Action action) {
        this.processGroup = processGroup;
        this.processName = processName;
        this.action = action;
    }

    public String getProcessGroup() {
        return processGroup;
    }

    public String getProcessName() {
        return processName;
    }

    public Action getAction() {
        return action;
    }
}


