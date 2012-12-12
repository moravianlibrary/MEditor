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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.Layout;

/**
 * @author Matous Jobanek
 * @version Dec 12, 2012
 */
public class ChartsUtils {

    public static void showChart(final String[] names,
                                 final int[] pages,
                                 final HTMLFlow htmlFlow,
                                 final String flowId,
                                 final Layout parent,
                                 final String title) {

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {

                int index = parent.getMemberNumber(htmlFlow);
                htmlFlow.removeFromParent();
                if (index < 0) {
                    parent.addMember(htmlFlow);
                } else {
                    parent.addMember(htmlFlow, index);
                }
                drawChart(JSOHelper.convertToJavaScriptArray(names),
                          JSOHelper.convertToJavaScriptArray(pages),
                          title,
                          flowId);

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

    public static native void drawChart(JavaScriptObject names,
                                        JavaScriptObject pages,
                                        String title,
                                        String chartNestedDivId) /*-{

		var dataTable = new $wnd.google.visualization.DataTable();

		dataTable.addColumn('string', 'Task');
		dataTable.addColumn('number', 'Object per time');
		dataTable.addRows(5);

		for ( var i = 0; i < names.length; i++) {
			dataTable.setValue(i, 0, names[i]);
			dataTable.setValue(i, 1, pages[i]);
		}

		var chart = new $wnd.google.visualization.PieChart($doc
				.getElementById(chartNestedDivId));
		chart.draw(dataTable, {
			title : title,
			width : 400,
			height : 240,
			is3D : true
		});

    }-*/;

}
