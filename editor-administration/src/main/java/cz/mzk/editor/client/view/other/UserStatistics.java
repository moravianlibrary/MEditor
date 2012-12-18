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

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.util.Constants.STATISTICS_SEGMENTATION;
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.IntervalStatisticData;
import cz.mzk.editor.shared.rpc.action.GetUserStatisticDataAction;
import cz.mzk.editor.shared.rpc.action.GetUserStatisticDataResult;

/**
 * @author Matous Jobanek
 * @version Dec 12, 2012
 */
public class UserStatistics
        extends HLayout {

    private static String html = "<div id=\"%s\" style=\"position: absolute; z-index: 1000000\"> </div>";

    private final String PIE_CHART_NESTED_DIV_ID = "pie_chart_nested_div_id";
    private final String LINE_CHART_NESTED_DIV_ID = "line_chart_nested_div_id";
    private final HTMLFlow htmlPieFlow;
    private final HTMLFlow htmlLineFlow;
    private final String userId;

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
    public UserStatistics(String userId,
                          String model,
                          Date dateFrom,
                          Date dateTo,
                          final String segVal,
                          DispatchAsync dispatcher) {

        htmlPieFlow = new HTMLFlow(html.replace("%s", PIE_CHART_NESTED_DIV_ID + userId));
        htmlPieFlow.setWidth(600);
        htmlPieFlow.setHeight(300);
        addMember(htmlPieFlow);

        htmlLineFlow = new HTMLFlow(html.replace("%s", LINE_CHART_NESTED_DIV_ID + userId));
        htmlLineFlow.setWidth(700);
        htmlLineFlow.setHeight(300);
        addMember(htmlLineFlow);

        this.userId = userId;

        GetUserStatisticDataAction statisticDataAction =
                new GetUserStatisticDataAction(userId, model, dateFrom, dateTo, segVal);
        dispatcher.execute(statisticDataAction, new DispatchCallback<GetUserStatisticDataResult>() {

            @Override
            public void callback(GetUserStatisticDataResult result) {
                if (result.getData() != null) {

                    final String[] names = new String[result.getData().size()];
                    final int[] values = new int[result.getData().size()];

                    int index = result.getData().size() - 1;
                    for (EditorDate keyDate : result.getData().keySet()) {
                        STATISTICS_SEGMENTATION seg = STATISTICS_SEGMENTATION.parseString(segVal);
                        IntervalStatisticData inteval = result.getData().get(keyDate);
                        if (seg == STATISTICS_SEGMENTATION.DAYS) {
                            names[index] = inteval.getFromDate().toString();
                            values[index--] = inteval.getValue();

                        } else if (seg == STATISTICS_SEGMENTATION.WEEKS) {
                            String dayFrom = String.valueOf(inteval.getFromDate().getDay());
                            if (dayFrom.length() == 1) dayFrom = "0" + dayFrom;
                            String monthFrom = "";
                            if (inteval.getFromDate().getMonth() != inteval.getToDate().getMonth()) {
                                monthFrom = String.valueOf(inteval.getFromDate().getMonth());
                                if (monthFrom.length() == 1) {
                                    monthFrom = ".0" + monthFrom;
                                } else {
                                    monthFrom = "." + monthFrom;
                                }
                            }

                            names[index] =
                                    dayFrom + monthFrom + ". - "
                                            + inteval.getToDate().toString().replaceAll(" ", "");
                            values[index--] = inteval.getValue();

                        } else if (seg == STATISTICS_SEGMENTATION.MONTHS) {
                            names[index] = String.valueOf(inteval.getFromDate().getMonth());
                            values[index--] = inteval.getValue();

                        } else if (seg == STATISTICS_SEGMENTATION.YEARS) {
                            names[index] = String.valueOf(inteval.getFromDate().getYear());
                            values[index--] = inteval.getValue();
                        }
                    }

                    showChart(names, values, "Work");
                } else {
                    SC.warn("There is no data!!!");
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void callbackError(Throwable t) {
                super.callbackError(t);
            }
        });

    }

    private void showChart(final String[] names, final int[] values, final String title) {

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {

                int pieIndex = getMemberNumber(htmlPieFlow);
                htmlPieFlow.removeFromParent();
                if (pieIndex < 0) {
                    addMember(htmlPieFlow);
                } else {
                    addMember(htmlPieFlow, pieIndex);
                }
                drawPieChart(JSOHelper.convertToJavaScriptArray(names),
                             JSOHelper.convertToJavaScriptArray(values),
                             values.length,
                             title,
                             PIE_CHART_NESTED_DIV_ID + userId);

                int index = getMemberNumber(htmlLineFlow);
                htmlLineFlow.removeFromParent();
                if (index < 0) {
                    addMember(htmlLineFlow);
                } else {
                    addMember(htmlLineFlow, index);
                }
                drawLineChart(JSOHelper.convertToJavaScriptArray(names),
                              JSOHelper.convertToJavaScriptArray(values),
                              values.length,
                              title,
                              LINE_CHART_NESTED_DIV_ID + userId);

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

    public static native void drawPieChart(JavaScriptObject names,
                                           JavaScriptObject pages,
                                           int size,
                                           String title,
                                           String chartNestedDivId) /*-{

		var dataTable = new $wnd.google.visualization.DataTable();

		dataTable.addColumn('string', 'Task');
		dataTable.addColumn('number', 'Object per time');
		dataTable.addRows(size);

		for ( var i = 0; i < names.length; i++) {
			dataTable.setValue(i, 0, names[i]);
			dataTable.setValue(i, 1, pages[i]);
		}

		var chart = new $wnd.google.visualization.PieChart($doc
				.getElementById(chartNestedDivId));
		chart.draw(dataTable, {
			title : title,
			width : 500,
			height : 240,
			is3D : true
		});

    }-*/;

    public static native void drawLineChart(JavaScriptObject names,
                                            JavaScriptObject pages,
                                            int size,
                                            String title,
                                            String chartNestedDivId) /*-{

		var dataTable = new $wnd.google.visualization.DataTable();

		dataTable.addColumn('string', 'Task');
		dataTable.addColumn('number', 'Object per time');
		dataTable.addRows(size);

		for ( var i = 0; i < names.length; i++) {
			dataTable.setValue(i, 0, names[i]);
			dataTable.setValue(i, 1, pages[i]);
		}

		var chart = new $wnd.google.visualization.LineChart($doc
				.getElementById(chartNestedDivId));
		chart.draw(dataTable, {
			title : title,
			width : 600,
			height : 240,
			is3D : true
		});

    }-*/;

}
