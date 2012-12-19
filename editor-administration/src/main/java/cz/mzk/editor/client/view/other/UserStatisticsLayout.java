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
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

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
                                DispatchAsync dispatcher) {
        this.dispatcher = dispatcher;
        this.userId = userRec.getAttributeAsString(Constants.ATTR_ID);
        this.chartsLayout = new HLayout();

        HTMLFlow nameFlow =
                new HTMLFlow(HtmlCode.title(userRec.getAttributeAsString(Constants.ATTR_NAME) + " "
                                                    + userRec.getAttribute(Constants.ATTR_SURNAME),
                                            3));
        nameFlow.setHeight(30);
        addMember(nameFlow);
        addMember(chartsLayout);

        htmlPieFlow = new HTMLFlow(html.replace("%s", PIE_CHART_NESTED_DIV_ID + userId));
        htmlPieFlow.setWidth("40%");
        htmlPieFlow.setHeight(300);
        chartsLayout.addMember(htmlPieFlow);

        htmlLineFlow = new HTMLFlow(html.replace("%s", LINE_CHART_NESTED_DIV_ID + userId));
        htmlLineFlow.setWidth("60%");
        htmlLineFlow.setHeight(300);
        chartsLayout.addMember(htmlLineFlow);

        setShowEdges(true);
        setEdgeSize(3);
        setEdgeOpacity(60);
        setPadding(5);
        setExtraSpace(5);

        final ModalWindow mw = new ModalWindow(chartsLayout);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        setdata(model, dateFrom, dateTo, segVal, mw);
    }

    private void setdata(String model, Date dateFrom, Date dateTo, final String segVal, final ModalWindow mw) {

        GetUserStatisticDataAction statisticDataAction =
                new GetUserStatisticDataAction(userId, model, dateFrom, dateTo, segVal);
        dispatcher.execute(statisticDataAction, new DispatchCallback<GetUserStatisticDataResult>() {

            @Override
            public void callback(GetUserStatisticDataResult result) {
                if (result.getData() != null) {
                    setData(result.getData());
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
    }

    protected abstract void afterDraw();

    protected abstract void setData(Map<EditorDate, IntervalStatisticData> data);

    protected void showChart(final String[] names, final int[] values) {

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {

                int pieIndex = chartsLayout.getMemberNumber(htmlPieFlow);
                htmlPieFlow.removeFromParent();
                if (pieIndex < 0) {
                    chartsLayout.addMember(htmlPieFlow);
                } else {
                    chartsLayout.addMember(htmlPieFlow, pieIndex);
                }
                ChartUtils.drawPieChart(JSOHelper.convertToJavaScriptArray(names),
                                        JSOHelper.convertToJavaScriptArray(values),
                                        values.length,
                                        //                             title,
                                        PIE_CHART_NESTED_DIV_ID + userId);

                int index = chartsLayout.getMemberNumber(htmlLineFlow);
                htmlLineFlow.removeFromParent();
                if (index < 0) {
                    chartsLayout.addMember(htmlLineFlow);
                } else {
                    chartsLayout.addMember(htmlLineFlow, index);
                }
                ChartUtils.drawLineChart(JSOHelper.convertToJavaScriptArray(names),
                                         JSOHelper.convertToJavaScriptArray(values),
                                         values.length,
                                         //                              title,
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

}
