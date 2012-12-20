/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

package cz.mzk.editor.client.view.other;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.IntervalStatisticData;
import cz.mzk.editor.shared.rpc.action.GetUserStatisticDataAction;
import cz.mzk.editor.shared.rpc.action.GetUserStatisticDataResult;

/**
 * @author Matous Jobanek
 * @version Dec 12, 2012
 */
public abstract class UserStatisticsLayout
        extends VLayout {

    private static String html = "<div id=\"%s\" style=\"position: absolute; z-index: 1000000\"> </div>";

    private final String PIE_CHART_NESTED_DIV_ID = "pie_chart_nested_div_id";
    private final String LINE_CHART_NESTED_DIV_ID = "line_chart_nested_div_id";
    private final HTMLFlow htmlPieFlow;
    private final HTMLFlow htmlLineFlow;
    private final String userId;
    private final HLayout chartsLayout;
    private final DispatchAsync dispatcher;
    private final String userName;
    private final LangConstants lang;

    class TableListGrid
            extends ListGrid {

        TableListGrid(String[] intervals, HashMap<String, Integer[]> userValues) {
            setWidth100();

            if (userValues.size() < 6) {
                setHeight(userValues.size() * 25 + 30);
            }
            setShowAllRecords(true);
            setCanDragSelect(true);

            List<ListGridField> fields = new ArrayList<ListGridField>();
            int fieldsIndex = 0;

            if (userValues.size() > 1) {
                ListGridField nameField = new ListGridField(Constants.ATTR_NAME, HtmlCode.bold(lang.name()));
                nameField.setWidth(140);
                fields.add(nameField);
            }
            ArrayList<Integer> indexes = new ArrayList<Integer>();

            for (String interv : intervals) {
                for (String user : userValues.keySet()) {
                    if (userValues.get(user)[fieldsIndex] > 0) {
                        indexes.add(fieldsIndex);
                        fields.add(new ListGridField(Constants.ATTR_INTERVAL + fieldsIndex, interv
                                .replaceAll(" ", "")));
                        break;
                    }
                }
                fieldsIndex++;
            }

            Integer layoutWidth = UserStatisticsLayout.this.getWidth();
            int width = indexes.size() * 115 + (userValues.size() > 1 ? 200 : 65);
            if (width < layoutWidth - 10) setWidth(width);

            fields.add(new ListGridField(Constants.ATTR_OBJECT, HtmlCode.bold(lang.inTotal())));
            ListGridField[] fieldsArray = new ListGridField[fields.size()];
            fields.toArray(fieldsArray);
            setFields(fieldsArray);

            ListGridRecord[] records = new ListGridRecord[intervals.length];

            fieldsIndex = 0;
            for (String user : userValues.keySet()) {

                ListGridRecord userRecord = new ListGridRecord();
                if (userValues.size() > 1) userRecord.setAttribute(Constants.ATTR_NAME, HtmlCode.bold(user));

                int overall = 0;
                for (Integer ind : indexes) {
                    int val = userValues.get(user)[ind];
                    overall += val;
                    userRecord.setAttribute(Constants.ATTR_INTERVAL + ind, val);
                }
                userRecord.setAttribute(Constants.ATTR_OBJECT, HtmlCode.bold(String.valueOf(overall)));
                records[fieldsIndex++] = userRecord;
            }
            setData(records);

        }
    }

    /**
     * Instantiates a new user statistics.
     * 
     * @param userId
     *        the user id
     * @param model
     *        the model
     * @param dateFrom
     *        the date from
     * @param dateTo
     *        the date to
     * @param segVal
     *        the seg val
     * @param dispatcher
     */
    public UserStatisticsLayout(Record userRec,
                                String model,
                                Date dateFrom,
                                Date dateTo,
                                final String segVal,
                                DispatchAsync dispatcher,
                                LangConstants lang) {
        this.dispatcher = dispatcher;
        this.userId = userRec != null ? userRec.getAttributeAsString(Constants.ATTR_ID) : null;
        this.chartsLayout = new HLayout();
        this.lang = lang;
        this.userName =
                userRec != null ? (userRec.getAttributeAsString(Constants.ATTR_NAME) + " " + userRec
                        .getAttribute(Constants.ATTR_SURNAME)) : lang.unifyingCharts();
        HTMLFlow nameFlow = new HTMLFlow(HtmlCode.title(userName, 3));
        nameFlow.setHeight(40);
        addMember(nameFlow);
        addMember(chartsLayout);

        htmlPieFlow = new HTMLFlow(html.replace("%s", PIE_CHART_NESTED_DIV_ID + userId));
        htmlPieFlow.setWidth("40%");
        htmlPieFlow.setHeight(250);
        htmlPieFlow.setExtraSpace((userRec != null) ? 10 : 50);
        chartsLayout.addMember(htmlPieFlow);

        htmlLineFlow = new HTMLFlow(html.replace("%s", LINE_CHART_NESTED_DIV_ID + userId));
        htmlLineFlow.setWidth("*");
        htmlLineFlow.setHeight(250);
        chartsLayout.addMember(htmlLineFlow);

        setShowEdges(true);
        setEdgeSize(3);
        setEdgeOpacity(60);
        setPadding(5);
        setExtraSpace(5);
        setHeight(280);

        final ModalWindow mw = new ModalWindow(chartsLayout);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        setdata(model, dateFrom, dateTo, segVal, mw);
    }

    private void setdata(String model, Date dateFrom, Date dateTo, final String segVal, final ModalWindow mw) {

        if (userId != null) {

            GetUserStatisticDataAction statisticDataAction =
                    new GetUserStatisticDataAction(userId, model, dateFrom, dateTo, segVal);
            dispatcher.execute(statisticDataAction, new DispatchCallback<GetUserStatisticDataResult>() {

                @Override
                public void callback(GetUserStatisticDataResult result) {
                    if (result.getData() != null) {
                        setData(result.getData(), userName);
                    } else {
                        SC.warn("There is no data!!!");
                    }
                    mw.hide();
                    afterDraw();
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void callbackError(Throwable t) {
                    super.callbackError(t);
                    mw.hide();
                    afterDraw();
                }
            });
        } else {
            setData(null, null);
            mw.hide();
        }
    }

    protected abstract void afterDraw();

    protected abstract void setData(Map<EditorDate, IntervalStatisticData> data, String name);

    protected void showChartAndTable(String[] intervals,
                                     HashMap<String, Integer[]> userValues,
                                     boolean showCharts) {
        showChartAndTable(intervals, null, userValues, showCharts);
    }

    protected void showChartAndTable(final String[] intervalsOrNames,
                                     final Integer[] values,
                                     boolean showCharts) {
        showChartAndTable(intervalsOrNames, values, null, showCharts);
    }

    private void showChartAndTable(final String[] intervalsOrNames,
                                   final Integer[] values,
                                   final HashMap<String, Integer[]> userValues,
                                   final boolean showCharts) {

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {

                if (showCharts) {
                    int pieIndex = chartsLayout.getMemberNumber(htmlPieFlow);
                    htmlPieFlow.removeFromParent();
                    if (pieIndex < 0) {
                        chartsLayout.addMember(htmlPieFlow);
                    } else {
                        chartsLayout.addMember(htmlPieFlow, pieIndex);
                    }

                    int index = chartsLayout.getMemberNumber(htmlLineFlow);
                    htmlLineFlow.removeFromParent();
                    if (index < 0) {
                        chartsLayout.addMember(htmlLineFlow);
                    } else {
                        chartsLayout.addMember(htmlLineFlow, index);
                    }
                } else {
                    removeMember(chartsLayout);
                }

                if (userValues == null) {
                    if (showCharts) {
                        ChartUtils.drawPieChart(JSOHelper.convertToJavaScriptArray(intervalsOrNames),
                                                JSOHelper.convertToJavaScriptArray(values),
                                                values.length,
                                                PIE_CHART_NESTED_DIV_ID + userId);

                        ChartUtils.drawLineChart(JSOHelper.convertToJavaScriptArray(intervalsOrNames),
                                                 JSOHelper.convertToJavaScriptArray(values),
                                                 values.length,
                                                 LINE_CHART_NESTED_DIV_ID + userId);
                    } else {
                        setHeight(100);
                        redraw();
                    }
                    HashMap<String, Integer[]> userVal = new HashMap<String, Integer[]>();
                    userVal.put(userName, values);
                    TableListGrid table = new TableListGrid(intervalsOrNames, userVal);
                    addMember(table);
                } else {
                    drawUnifyingChartsAndTables(intervalsOrNames, userValues, showCharts);
                }

            }
        };

        VisualizationUtils.loadVisualizationApi(runnable, PieChart.PACKAGE);

        Window.addResizeHandler(new ResizeHandler() {

            @Override
            public void onResize(ResizeEvent event) {
                com.google.gwt.user.client.Timer timer = new com.google.gwt.user.client.Timer() {

                    @Override
                    public void run() {
                        VisualizationUtils
                                .loadVisualizationApi(runnable,
                                                      com.google.gwt.visualization.client.visualizations.PieChart.PACKAGE);
                    }
                };
                timer.schedule(1);
            }
        });
    }

    private void drawUnifyingChartsAndTables(final String[] intervals,
                                             final HashMap<String, Integer[]> userValues,
                                             boolean showCharts) {

        String[] allNames = new String[userValues.size()];
        Integer[] allValues = new Integer[userValues.size()];

        int index = 0;
        for (String user : userValues.keySet()) {

            allNames[index] = user;

            int sumCount = 0;
            for (int value : userValues.get(user)) {
                sumCount += value;
            }
            allValues[index++] = sumCount;
        }

        if (showCharts) {
            ChartUtils.drawPieChart(JSOHelper.convertToJavaScriptArray(allNames),
                                    JSOHelper.convertToJavaScriptArray(allValues),
                                    allNames.length,
                                    PIE_CHART_NESTED_DIV_ID + userId);

            ChartUtils.drawBarChart(JSOHelper.convertToJavaScriptArray(allNames),
                                    JSOHelper.convertToJavaScriptArray(allValues),
                                    allNames.length,
                                    LINE_CHART_NESTED_DIV_ID + userId);
        } else {
            setHeight(userValues.size() * 25 + 30);
            redraw();
        }
        TableListGrid table = new TableListGrid(intervals, userValues);
        addMember(table);
    }
}
