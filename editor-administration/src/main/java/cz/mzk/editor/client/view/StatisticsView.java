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
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.presenter.StatisticsPresenter;
import cz.mzk.editor.client.uihandlers.StatisticsUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.STATISTICS_SEGMENTATION;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.other.MonthsUtils;
import cz.mzk.editor.client.view.other.UserStatisticsLayout;
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.IntervalStatisticData;
import cz.mzk.editor.shared.rpc.UserInfoItem;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoAction;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoResult;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class StatisticsView
        extends ViewWithUiHandlers<StatisticsUiHandlers>
        implements StatisticsPresenter.MyView {

    private final VStack mainLayout;
    private final LangConstants lang;
    private final DispatchAsync dispatcher;
    private final EventBus eventBus;

    private DateItem fromDate;
    private DateItem toDate;
    private ListGrid selectedUsersGrid;
    private IButton showButton;
    private SelectItem segmentation;
    private SelectItem selObject;
    private final VLayout statPart;
    private CheckboxItem showCharts;
    private CheckboxItem showChartsForAll;
    private HLayout selLayout;

    private String[] intervals = null;

    @Inject
    public StatisticsView(final EventBus eventBus, final LangConstants lang, DispatchAsync dispatcher) {
        this.mainLayout = new VStack();
        this.lang = lang;
        this.dispatcher = dispatcher;
        this.eventBus = eventBus;
        this.statPart = new VLayout();

        mainLayout.setWidth100();
        mainLayout.setPadding(10);
        mainLayout.setOverflow(Overflow.AUTO);

        setSelectionLayout();

        mainLayout.addMember(statPart);
    }

    private void setSelectionLayout() {

        selLayout = new HLayout(2);
        selLayout.setWidth100();
        selLayout.setHeight("25%");
        selLayout.setShowEdges(true);
        selLayout.setEdgeSize(3);
        selLayout.setEdgeOpacity(60);
        selLayout.setPadding(5);

        selLayout.addMember(getUserSelect());

        VLayout selectionAndCharts = new VLayout();
        selectionAndCharts.addMember(getSelObjDate());
        selectionAndCharts.addMember(getChartsChooser());
        selectionAndCharts.setShowEdges(true);
        selectionAndCharts.setWidth100();
        selectionAndCharts.setEdgeSize(2);
        selectionAndCharts.setEdgeOpacity(60);
        selectionAndCharts.setPadding(5);

        selectionAndCharts.setExtraSpace(10);
        selectionAndCharts.setLayoutAlign(Alignment.LEFT);

        showButton = new IButton(lang.show());
        showButton.setLayoutAlign(VerticalAlignment.BOTTOM);
        showButton.setDisabled(true);

        VLayout paramsAndButtonLayout = new VLayout(2);
        paramsAndButtonLayout.addMember(selectionAndCharts);
        paramsAndButtonLayout.setWidth("50%");
        paramsAndButtonLayout.addMember(showButton);

        selLayout.addMember(paramsAndButtonLayout);

        final HashMap<String, Integer[]> userValues = new HashMap<String, Integer[]>();

        showButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (statPart.getMembers().length > 0) {
                    statPart.removeMembers(statPart.getMembers());
                    selLayout.setWidth100();
                    statPart.setWidth100();
                }
                ListGridRecord[] selRecords = selectedUsersGrid.getRecords();
                if (!userValues.isEmpty()) {
                    userValues.clear();
                }
                if (selRecords.length > 1) {
                    selLayout.setWidth(selLayout.getWidth() - 20);
                    statPart.setWidth(statPart.getWidth() - 20);
                }

                intervals = null;

                if (selRecords.length > 0) {
                    setLayout(selRecords, 0);
                }
            }

            private void setLayout(final ListGridRecord[] selRecords, final int index) {
                statPart.addMember(new UserStatisticsLayout(selRecords != null ? selRecords[index] : null,
                                                            selObject.getValueAsString(),
                                                            fromDate.getValueAsDate(),
                                                            toDate.getValueAsDate(),
                                                            segmentation.getValueAsString(),
                                                            dispatcher,
                                                            lang) {

                    @Override
                    protected void afterDraw() {
                        if (selRecords.length > index + 1) {
                            setLayout(selRecords, index + 1);
                        } else if (!showChartsForAll.isDisabled() && showChartsForAll.getValueAsBoolean()) {
                            setLayout(null, 0);
                        }
                    }

                    @Override
                    protected void setData(Map<EditorDate, IntervalStatisticData> data, String userName) {
                        if (data != null) {

                            final Integer[] values = new Integer[data.size()];

                            STATISTICS_SEGMENTATION seg =
                                    STATISTICS_SEGMENTATION.parseString(segmentation.getValueAsString());
                            if (intervals == null) setIntervals(data, seg);

                            int index = data.size() - 1;
                            for (EditorDate keyDate : data.keySet()) {

                                IntervalStatisticData inteval = data.get(keyDate);
                                if (seg == STATISTICS_SEGMENTATION.DAYS) {
                                    values[index--] = inteval.getValue();

                                } else if (seg == STATISTICS_SEGMENTATION.WEEKS) {
                                    values[index--] = inteval.getValue();

                                } else if (seg == STATISTICS_SEGMENTATION.MONTHS) {
                                    values[index--] = inteval.getValue();

                                } else if (seg == STATISTICS_SEGMENTATION.YEARS) {
                                    values[index--] = inteval.getValue();
                                }
                            }

                            userValues.put(userName, values);

                            showChartAndTable(intervals, values, showCharts.getValueAsBoolean());
                        } else {
                            showChartAndTable(intervals, userValues, showCharts.getValueAsBoolean());
                        }
                    }

                });
            }
        });

        selLayout.setExtraSpace(5);
        selLayout.setBackgroundColor("white");
        mainLayout.addMember(selLayout);
    }

    private void setIntervals(Map<EditorDate, IntervalStatisticData> data, STATISTICS_SEGMENTATION seg) {
        intervals = new String[data.size()];
        int index = data.size() - 1;

        for (EditorDate keyDate : data.keySet()) {
            IntervalStatisticData inteval = data.get(keyDate);

            if (seg == STATISTICS_SEGMENTATION.DAYS) {
                intervals[index--] = inteval.getFromDate().toString();

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

                intervals[index--] =
                        dayFrom + monthFrom + ". - " + inteval.getToDate().toString().replaceAll(" ", "");

            } else if (seg == STATISTICS_SEGMENTATION.MONTHS) {
                intervals[index--] = MonthsUtils.getMonths(lang).get(inteval.getFromDate().getMonth());

            } else if (seg == STATISTICS_SEGMENTATION.YEARS) {
                intervals[index--] = String.valueOf(inteval.getFromDate().getYear());
            }
        }
    }

    private HLayout getChartsChooser() {

        HLayout chartsChooserLayout = new HLayout();
        chartsChooserLayout.setWidth(200);

        showCharts = new CheckboxItem("showCharts", HtmlCode.bold(lang.showCharts()));
        showCharts.setDefaultValue(true);

        segmentation = new SelectItem("segmentation", HtmlCode.bold(lang.withSegmentation()));

        LinkedHashMap<String, String> segValuesByValue = new LinkedHashMap<String, String>();
        segValuesByValue.put(STATISTICS_SEGMENTATION.YEARS.getValue(), lang.years());
        segValuesByValue.put(STATISTICS_SEGMENTATION.MONTHS.getValue(), lang.months());
        segValuesByValue.put(STATISTICS_SEGMENTATION.WEEKS.getValue(), lang.weeks());
        segValuesByValue.put(STATISTICS_SEGMENTATION.DAYS.getValue(), lang.days());

        segmentation.setValueMap(segValuesByValue);
        segmentation.setDefaultValue(STATISTICS_SEGMENTATION.DAYS.getValue());
        segmentation.setWrapTitle(false);
        segmentation.setTitleStyle("");

        DynamicForm showChartsForm = new DynamicForm();
        showChartsForm.setItems(showCharts);
        showChartsForm.setExtraSpace(30);

        DynamicForm segChartsForm = new DynamicForm();
        segChartsForm.setItems(segmentation);

        chartsChooserLayout.addMember(showChartsForm);
        chartsChooserLayout.addMember(segChartsForm);

        return chartsChooserLayout;
    }

    private void checkShowButton() {
        if (fromDate.getValueAsDate().getTime() <= toDate.getValueAsDate().getTime()
                && selectedUsersGrid.getRecords().length > 0) {
            showButton.setDisabled(false);
        } else {
            showButton.setDisabled(true);
        }
    }

    private HLayout getSelObjDate() {

        HLayout selObjDateLayout = new HLayout();
        selObjDateLayout.setWidth100();
        selObjDateLayout.setExtraSpace(10);

        final VLayout selObjLayout = new VLayout();
        final DynamicForm objectAndTime = new DynamicForm();
        selObject = new SelectItem("selectObject");
        selObject.setShowTitle(false);
        selObject.setWrapTitle(false);
        objectAndTime.setItems(selObject);

        HTMLFlow selObjFlow =
                new HTMLFlow(HtmlCode.title(lang.show() + " " + lang.insertedObjCount().toLowerCase(), 3));
        selObjFlow.setWidth100();
        selObjFlow.setHeight(30);

        selObjLayout.addMember(selObjFlow);
        selObjLayout.addMember(objectAndTime);

        VLayout selIntLayout = new VLayout();
        selIntLayout.setWidth("50%");
        DynamicForm dates = new DynamicForm();
        fromDate = new DateItem("from", HtmlCode.bold(lang.from()));
        toDate = new DateItem("to", HtmlCode.bold(lang.to()));

        fromDate.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                checkShowButton();
            }
        });
        toDate.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                checkShowButton();
            }
        });

        dates.setItems(fromDate, toDate);
        dates.setWidth(150);

        HTMLFlow intevalFlow = new HTMLFlow(HtmlCode.title(lang.inInterval(), 3));
        selObjFlow.setWidth(selObjFlow.getContents().length() * 5);
        intevalFlow.setHeight(30);
        selIntLayout.addMember(intevalFlow);
        selIntLayout.addMember(dates);

        selObjDateLayout.addMember(selObjLayout);
        selObjDateLayout.addMember(selIntLayout);
        return selObjDateLayout;
    }

    private HLayout getUserSelect() {
        HLayout userLayout = new HLayout();
        userLayout.setExtraSpace(5);

        selectedUsersGrid = new ListGrid();
        selectedUsersGrid.setShowAllRecords(true);
        selectedUsersGrid.setCanReorderRecords(true);
        selectedUsersGrid.setWidth100();
        ListGridField selectedUsersField = new ListGridField(Constants.ATTR_NAME, lang.selectedUsers());
        selectedUsersField.setCellFormatter(new CellFormatter() {

            @Override
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                return record.getAttributeAsString(Constants.ATTR_NAME) + " "
                        + record.getAttribute(Constants.ATTR_SURNAME);
            }
        });
        selectedUsersGrid.setFields(selectedUsersField);

        final ListGrid usersGrid = new ListGrid();
        usersGrid.setWidth("60%");
        usersGrid.setShowAllRecords(true);
        usersGrid.setSelectionType(SelectionStyle.SIMPLE);
        usersGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
        ListGridField nameField = new ListGridField(Constants.ATTR_NAME, lang.name());
        ListGridField surnameField = new ListGridField(Constants.ATTR_SURNAME, lang.lastName());
        usersGrid.setFields(nameField, surnameField);
        usersGrid.setSortField(Constants.ATTR_SURNAME);
        usersGrid.sort();

        GetUsersInfoAction getUsersAction = new GetUsersInfoAction();
        DispatchCallback<GetUsersInfoResult> usersCallback = new DispatchCallback<GetUsersInfoResult>() {

            @Override
            public void callback(GetUsersInfoResult result) {
                ListGridRecord[] allUsers = new ListGridRecord[result.getItems().size()];

                int index = 0;
                for (UserInfoItem userItem : result.getItems()) {
                    ListGridRecord user = new ListGridRecord();
                    user.setAttribute(Constants.ATTR_ID, userItem.getId());
                    user.setAttribute(Constants.ATTR_NAME, userItem.getName());
                    user.setAttribute(Constants.ATTR_SURNAME, userItem.getSurname());
                    allUsers[index++] = user;
                }
                usersGrid.setData(allUsers);
            }
        };
        dispatcher.execute(getUsersAction, usersCallback);

        VLayout selUserLayout = new VLayout();

        final DynamicForm showChartsForAllForm = new DynamicForm();
        String title = HtmlCode.bold(lang.show() + " " + lang.unifyingCharts().toLowerCase());
        showChartsForAll = new CheckboxItem("showCharts", title);
        showChartsForAll.setDefaultValue(true);
        showChartsForAll.setWrapTitle(true);
        showChartsForAll.setDisabled(true);
        showChartsForAllForm.setWidth(title.length() * 5);
        showChartsForAllForm.setItems(showChartsForAll);

        usersGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                ListGridRecord[] selectedRecords = usersGrid.getSelectedRecords();
                selectedUsersGrid.setData(selectedRecords);

                showChartsForAll.setDisabled(selectedRecords == null || selectedRecords.length < 2);
                checkShowButton();
            }
        });

        userLayout.addMember(usersGrid);

        selUserLayout.addMember(selectedUsersGrid);
        selUserLayout.addMember(showChartsForAllForm);
        selUserLayout.setWidth("40%");

        userLayout.addMember(selUserLayout);
        userLayout.setWidth("50%");
        return userLayout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return mainLayout;
    }

    /**
     * @return the selObject
     */
    @Override
    public SelectItem getSelObject() {
        return selObject;
    }

}
