package cz.mzk.editor.shared.erraiPortable;

import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * @author: Martin Rumanek
 * @version: 21.3.13
 */

@Portable
public class QuartzJobAction {

    @Portable
    public static enum Action {
        TO_BE_EXECUTED, EXECUTION_VETOED, WAS_EXECUTED, PROGRESS
    }

    private String processGroup;

    private String processName;

    private Action action;

    private Integer percentDone = 0;

    public QuartzJobAction() {
    }

    public QuartzJobAction(String processGroup, String processName, Action action) {
        this.processGroup = processGroup;
        this.processName = processName;
        this.action = action;
    }

    public QuartzJobAction(String processGroup, String processName, Action action, int percent) {
        this.processGroup = processGroup;
        this.processName = processName;
        this.action = action;
        this.percentDone = percent;
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

    public Integer getPercentDone() {
        return percentDone;
    }


}


