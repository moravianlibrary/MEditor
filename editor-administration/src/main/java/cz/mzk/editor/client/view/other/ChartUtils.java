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

/**
 * @author Matous Jobanek
 * @version Dec 18, 2012
 */
public class ChartUtils {

    public static native void drawPieChart(JavaScriptObject names,
                                           JavaScriptObject pages,
                                           int size,
                                           String chartNestedDivId,
                                           int width) /*-{

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
			width : width,
			height : 240,
			is3D : true
		});

    }-*/;

    public static native void drawLineChart(JavaScriptObject names,
                                            JavaScriptObject pages,
                                            int size,
                                            String chartNestedDivId,
                                            int width) /*-{

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
			colors : [ '#9d7100' ],
			legend : 'none',
			width : width,
			height : 240,
			is3D : true
		});

    }-*/;

    public static native void drawBarChart(JavaScriptObject names,
                                           JavaScriptObject pages,
                                           int size,
                                           String chartNestedDivId,
                                           int width) /*-{

		var dataTable = new $wnd.google.visualization.DataTable();

		dataTable.addColumn('string', 'Task');
		dataTable.addColumn('number', 'Object per time');
		dataTable.addRows(size);

		for ( var i = 0; i < names.length; i++) {
			dataTable.setValue(i, 0, names[i]);
			dataTable.setValue(i, 1, pages[i]);
		}

		var chart = new $wnd.google.visualization.BarChart($doc
				.getElementById(chartNestedDivId));
		chart.draw(dataTable, {
			colors : [ '#00d287' ],
			legend : 'none',
			width : width,
			height : 240
		});

    }-*/;

}
