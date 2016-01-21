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

package cz.mzk.editor.client.view.window;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.shared.erraiPortable.QuartzJobAction;
import cz.mzk.editor.shared.rpc.ProcessItem;
import cz.mzk.editor.shared.rpc.action.QuartzScheduleJobsAction;
import cz.mzk.editor.shared.rpc.action.QuartzScheduleJobsResult;
import org.jboss.errai.bus.client.api.ClientMessageBus;
import org.jboss.errai.bus.client.api.Subscription;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.bus.client.api.messaging.MessageCallback;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Martin Rumanek
 * @version 29.8.2012
 */
public class SchedulerWindow
        extends UniversalWindow {

    private final ListGrid jobsGrid;
    @SuppressWarnings("unused")
    private final LangConstants lang;
    private final ClientMessageBus messageBus;

    /**
     * @param eventBus
     * @param lang
     * @param dispatcher
     */
    public SchedulerWindow(final EventBus eventBus, final LangConstants lang, final DispatchAsync dispatcher, final ClientMessageBus bus) {
        super(630, 600, "Scheduler", eventBus, 50);
        this.lang = lang;
        this.messageBus = bus;
        final Map<ProcessItem, Progressbar> progressbars = new HashMap<ProcessItem, Progressbar>();

        jobsGrid = new ListGrid() {

            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {
                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("action")) {
                    final ImgButton button = new ImgButton();
                    button.setSrc("icons/16/close.png");
                    button.setSize("16", "16");
                    button.setShowTitle(false);
                    button.setShowRollOver(false);
                    button.setShowDown(false);
                    button.addHoverHandler(new HoverHandler() {

                        @Override
                        public void onHover(HoverEvent event) {
                            button.setPrompt(lang.killProcess());
                        }
                    });
                    button.addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                            killJob(record, dispatcher);

                        }
                    });
                    return button;
                } else if (fieldName.equals("progressbar")) {
                    final Progressbar progressbar = new Progressbar();
                    progressbar.setBreadth(15);
                    progressbar.setLength(120);
                    progressbars.put(new ProcessItem(record.getAttributeAsString("group"), record.getAttributeAsString("name")), progressbar);

                    if (record.getAttributeAsInt("progressbar") != null) {
                        progressbar.setPercentDone(record.getAttributeAsInt("progressbar"));
                    }
                    return progressbar;
                }
                return null;

            }
        };
        jobsGrid.setShowRecordComponents(true);
        jobsGrid.setShowRecordComponentsByCell(true);
        ListGridField groupField = new ListGridField("group", lang.groupProcess());
        ListGridField nameField = new ListGridField("name", lang.nameProcess());
        ListGridField progressbar = new ListGridField("progressbar", "Progressbar"); //TODO-MR lang
        ListGridField actionField = new ListGridField("action", lang.actionProcess(), 50);
        actionField.setAlign(Alignment.CENTER);
        jobsGrid.setFields(groupField, nameField, progressbar, actionField);

        getJobs(dispatcher);
        centerInPage();
        addItem(jobsGrid);
        final Subscription subscription = messageBus.subscribe("QuartzBroadcastReceiver", new MessageCallback() {

            @Override
            public void callback(Message message) {
                QuartzJobAction jobAction = message.get(QuartzJobAction.class, "jobDetail");
                ListGridRecord processRecord = new ListGridRecord();
                processRecord.setAttribute("group", jobAction.getProcessGroup());
                processRecord.setAttribute("name", jobAction.getProcessName());
                switch (jobAction.getAction()) {
                    case TO_BE_EXECUTED:
                        jobsGrid.addData(processRecord);
                        break;
                    case EXECUTION_VETOED:
                        for (ListGridRecord record : jobsGrid.getRecords()) {
                            if (record.getAttribute("group").equals(jobAction.getProcessGroup()) &&
                                    record.getAttribute("name").equals(jobAction.getProcessName())) {
                                jobsGrid.removeData(record);
                            }
                        }
                        break;
                    case WAS_EXECUTED:
                        for (ListGridRecord record : jobsGrid.getRecords()) {
                            if (record.getAttribute("group").equals(jobAction.getProcessGroup()) &&
                                    record.getAttribute("name").equals(jobAction.getProcessName())) {
                                jobsGrid.removeData(record);
                            }
                        }
                        break;
                    case PROGRESS:
                        Progressbar bar = progressbars.get(new ProcessItem(jobAction.getProcessGroup(), jobAction.getProcessName()));
                        if (bar != null) {
                            bar.setPercentDone(jobAction.getPercentDone());
                        }
                }
            }
        });

        this.addCloseClickHandler(new CloseClickHandler() {
            @Override
            public void onCloseClick(CloseClickEvent closeClickEvent) {
                subscription.remove();
            }
        });

        show();
    }

    private void getJobs(final DispatchAsync dispatcher) {
        dispatcher.execute(new QuartzScheduleJobsAction(null),
                new DispatchCallback<QuartzScheduleJobsResult>() {

                    @Override
                    public void callback(QuartzScheduleJobsResult result) {
                        Record[] jobs = new Record[result.getJobs().size()];
                        int i = 0;
                        for (ProcessItem process : result.getJobs()) {
                            Record processRecord = new Record();
                            processRecord.setAttribute("group", process.getProcessGroup());
                            processRecord.setAttribute("name", process.getProcessName());
                            processRecord.setAttribute("progressbar", process.getPercentDone());
                            jobs[i++] = processRecord;
                        }
                        jobsGrid.setData(jobs);
                    }

                });

    }

    private void killJob(final Record record, DispatchAsync dispatcher) {
        ProcessItem process =
                new ProcessItem(record.getAttributeAsString("group"), record.getAttributeAsString("name"));

        dispatcher.execute(new QuartzScheduleJobsAction(process),
                new DispatchCallback<QuartzScheduleJobsResult>() {

                    @Override
                    public void callback(QuartzScheduleJobsResult result) {
                        jobsGrid.removeData(record);
                        jobsGrid.redraw();
                    }

                });
    }
}