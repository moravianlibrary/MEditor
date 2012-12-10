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

package cz.mzk.editor.client.view;

import java.util.HashMap;

import javax.inject.Inject;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.presenter.StatisticsPresenter;
import cz.mzk.editor.client.uihandlers.StatisticsUiHandlers;
import cz.mzk.editor.client.view.other.UserSelect;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class StatisticsView
        extends ViewWithUiHandlers<StatisticsUiHandlers>
        implements StatisticsPresenter.MyView {

    private final VStack mainLayout;
    private SelectItem users;
    private final LangConstants lang;
    private final DispatchAsync dispatcher;

    private static String html =
            "<div id=\"chart_nested_div\" style=\"position: absolute; z-index: 1000000\"> </div>";
    private HTMLFlow htmlFlow;

    class SalesRecord
            extends Record {

        SalesRecord(String region, String product, Integer sales) {
            setAttribute("region", region);
            setAttribute("product", product);
            setAttribute("sales", sales);
        }
    };

    @Inject
    public StatisticsView(final EventBus eventBus, final LangConstants lang, DispatchAsync dispatcher) {
        this.mainLayout = new VStack();

        mainLayout.setWidth("90%");
        htmlFlow = new HTMLFlow(html);
        htmlFlow.setWidth(500);
        htmlFlow.setHeight(300);
        mainLayout.addMember(htmlFlow);
        System.err.println(htmlFlow.getInnerHTML());
        System.err.println(htmlFlow.getID());
        this.lang = lang;
        this.dispatcher = dispatcher;
        setUserSelect();

        final HashMap<String, Integer> values = new HashMap<String, Integer>();
        values.put("pesta", 10);
        values.put("jiranova", 20);
        values.put("viola", 80);
        values.put("sapakova", 70);

        final String[] names = {"pesta", "jiranova", "viola", "sapakova"};
        final int[] pages = {10, 20, 80, 70};

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                // Create a pie chart visualization.
                //                PieChart pie =
                //                        new PieChart(createTable("Statistics test", values), createOptions("Statistics test"));
                //
                //                pie.addSelectHandler(createSelectHandler(pie));
                //
                //                UniversalWindow window = new UniversalWindow(300, 300, "", eventBus, 20);
                //                window.addItem(pie);
                //                window.show();
                //                window.centerInPage();
                //
                mainLayout.removeMember(htmlFlow);
                htmlFlow = new HTMLFlow(html);
                htmlFlow.setWidth(500);
                htmlFlow.setHeight(300);
                mainLayout.addMember(htmlFlow);
                drawChart(JSOHelper.convertToJavaScriptArray(names),
                          JSOHelper.convertToJavaScriptArray(pages));

            }
        };

        VisualizationUtils.loadVisualizationApi(runnable, PieChart.PACKAGE);
        Window.addResizeHandler(new ResizeHandler() {

            @Override
            public void onResize(ResizeEvent event) {
                VisualizationUtils.loadVisualizationApi(runnable, PieChart.PACKAGE);
            }
        });
    }

    private native void drawChart(JavaScriptObject names, JavaScriptObject pages) /*-{

		// Create our data table.
		var dataTable = new $wnd.google.visualization.DataTable();

		dataTable.addColumn('string', 'Task');
		dataTable.addColumn('number', 'Hours per Day');
		dataTable.addRows(5);

		for ( var i = 0; i < names.length; i++) {
			dataTable.setValue(i, 0, names[i]);
			dataTable.setValue(i, 1, pages[i]);
		}

		var chart = new $wnd.google.visualization.PieChart($doc
				.getElementById('chart_nested_div'));
		chart.draw(dataTable, {
			width : 400,
			height : 240
		});

    }-*/;

    private void setUserSelect() {
        users = new UserSelect(lang.users(), dispatcher);
        users.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
            }
        });

        DynamicForm usersForm = new DynamicForm();
        usersForm.setItems(users);

        mainLayout.addMember(usersForm);
    }

    //    private Options createOptions(String title) {
    //        Options options = Options.create();
    //        options.setWidth(400);
    //        options.setHeight(240);
    //        options.set3D(true);
    //        options.setTitle(title);
    //        return options;
    //    }
    //
    //    private SelectHandler createSelectHandler(final PieChart chart) {
    //        return new SelectHandler() {
    //
    //            @Override
    //            public void onSelect(SelectEvent event) {
    //                String message = "";
    //
    //                // May be multiple selections.
    //                JsArray<Selection> selections = chart.getSelections();
    //
    //                for (int i = 0; i < selections.length(); i++) {
    //                    // add a new line for each selection
    //                    message += i == 0 ? "" : "\n";
    //
    //                    Selection selection = selections.get(i);
    //
    //                    if (selection.isCell()) {
    //                        // isCell() returns true if a cell has been selected.
    //
    //                        // getRow() returns the row number of the selected cell.
    //                        int row = selection.getRow();
    //                        // getColumn() returns the column number of the selected cell.
    //                        int column = selection.getColumn();
    //                        message += "cell " + row + ":" + column + " selected";
    //                    } else if (selection.isRow()) {
    //                        // isRow() returns true if an entire row has been selected.
    //
    //                        // getRow() returns the row number of the selected row.
    //                        int row = selection.getRow();
    //                        message += "row " + row + " selected";
    //                    } else {
    //                        // unreachable
    //                        message += "Pie chart selections should be either row selections or cell selections.";
    //                        message += "  Other visualizations support column selections as well.";
    //                    }
    //                }
    //
    //                SC.warn(message);
    //            }
    //        };
    //    }
    //
    //    private AbstractDataTable createTable(String title, Map<String, Integer> values) {
    //        DataTable data = DataTable.create();
    //        data.addColumn(ColumnType.STRING, "Task");
    //        data.addColumn(ColumnType.NUMBER, "Hours per Day");
    //        data.addRows(values.size());
    //        int index = 0;
    //        if (values != null) {
    //            for (String valueType : values.keySet()) {
    //                data.setValue(index, 0, valueType);
    //                data.setValue(index++, 1, values.get(valueType));
    //            }
    //        }
    //
    //        return data;
    //    }
    //
    //    

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return mainLayout;
    }

}
