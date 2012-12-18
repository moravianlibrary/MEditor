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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
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
import cz.mzk.editor.client.config.EditorClientConfiguration;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.other.LabelAndModelConverter;
import cz.mzk.editor.client.presenter.StatisticsPresenter;
import cz.mzk.editor.client.uihandlers.StatisticsUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.STATISTICS_SEGMENTATION;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.other.UserStatistics;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.NamedGraphModel;
import cz.mzk.editor.shared.event.ConfigReceivedEvent;
import cz.mzk.editor.shared.event.ConfigReceivedEvent.ConfigReceivedHandler;
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
    private final EditorClientConfiguration config;

    private DateItem fromDate;
    private DateItem toDate;
    private ListGrid selectedUsersGrid;
    private IButton showButton;
    private SelectItem segmentation;
    private SelectItem selObject;
    private final VLayout statPart;

    @Inject
    public StatisticsView(final EventBus eventBus,
                          final LangConstants lang,
                          DispatchAsync dispatcher,
                          final EditorClientConfiguration config) {
        this.mainLayout = new VStack();
        this.lang = lang;
        this.dispatcher = dispatcher;
        this.config = config;
        this.eventBus = eventBus;
        this.statPart = new VLayout();

        mainLayout.setWidth100();

        setMaps();
        setSelectionLayout();

        mainLayout.addMember(statPart);

    }

    private void setMaps() {

    }

    private void setSelectionLayout() {

        HLayout selLayout = new HLayout();
        selLayout.setWidth100();
        selLayout.setHeight("25%");
        selLayout.setShowEdges(true);
        selLayout.setEdgeSize(3);
        selLayout.setEdgeOpacity(60);
        selLayout.setMargin(10);
        selLayout.setPadding(5);

        selLayout.addMember(setUserSelect());

        VLayout selectionAndCharts = new VLayout();
        selectionAndCharts.addMember(getSelObjDate());
        selectionAndCharts.addMember(getChartsChooser());
        selectionAndCharts.setShowEdges(true);
        selectionAndCharts.setEdgeSize(2);
        selectionAndCharts.setEdgeOpacity(60);
        selectionAndCharts.setPadding(5);
        selectionAndCharts.setWidth(450);
        selectionAndCharts.setExtraSpace(10);
        selectionAndCharts.setLayoutAlign(Alignment.LEFT);

        selLayout.addMember(selectionAndCharts);

        showButton = new IButton(lang.show());
        showButton.setLayoutAlign(VerticalAlignment.BOTTOM);
        showButton.setDisabled(true);

        showButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final String segVal = segmentation.getValueAsString();
                statPart.removeMembers(statPart.getMembers());
                for (ListGridRecord rec : selectedUsersGrid.getRecords()) {
                    statPart.addMember(new UserStatistics(rec.getAttributeAsString(Constants.ATTR_ID),
                                                          selObject.getValueAsString(),
                                                          fromDate.getValueAsDate(),
                                                          toDate.getValueAsDate(),
                                                          segVal,
                                                          dispatcher));
                }
            }
        });

        selLayout.addMember(showButton);
        mainLayout.addMember(selLayout);
    }

    private HLayout getChartsChooser() {

        HLayout chartsChooserLayout = new HLayout();
        chartsChooserLayout.setWidth(200);

        final CheckboxItem showCharts = new CheckboxItem("showCharts", HtmlCode.bold(lang.showCharts()));
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

        showCharts.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                if (showCharts.getValueAsBoolean()) {
                    segmentation.show();
                } else {
                    segmentation.hide();
                }

            }
        });

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
        selObjDateLayout.setWidth(400);
        selObjDateLayout.setExtraSpace(10);

        VLayout selObjLayout = new VLayout();
        DynamicForm objectAndTime = new DynamicForm();
        selObject = new SelectItem("selectObject");
        selObject.setShowTitle(false);
        selObject.setWrapTitle(false);
        objectAndTime.setItems(selObject);

        HTMLFlow selObjFlow = new HTMLFlow(HtmlCode.title(lang.showInsertedCount(), 3));
        selObjFlow.setWidth(250);
        selObjLayout.addMember(selObjFlow);
        selObjLayout.addMember(objectAndTime);

        VLayout selIntLayout = new VLayout();
        selIntLayout.setWidth(100);
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
        selIntLayout.addMember(intevalFlow);
        selIntLayout.addMember(dates);

        eventBus.addHandler(ConfigReceivedEvent.getType(), new ConfigReceivedHandler() {

            @Override
            public void onConfigReceived(ConfigReceivedEvent event) {
                String[] documentTypes;
                if (event.isStatusOK()) {
                    documentTypes = config.getDocumentTypes();
                } else {
                    documentTypes = EditorClientConfiguration.Constants.DOCUMENT_DEFAULT_TYPES;
                }

                LinkedHashMap<String, String> models = new LinkedHashMap<String, String>();
                boolean isPage = false;

                for (String docType : documentTypes) {

                    try {
                        ArrayList<DigitalObjectModel> modelList = new ArrayList<DigitalObjectModel>();
                        modelList.add(DigitalObjectModel.parseString(docType));
                        LabelAndModelConverter.setLabelAndModelConverter(lang);

                        while (!modelList.isEmpty()) {
                            DigitalObjectModel lastObj = modelList.remove(modelList.size() - 1);
                            if (!isPage && lastObj == DigitalObjectModel.PAGE) isPage = true;
                            String labelModel =
                                    LabelAndModelConverter.getLabelFromModel().get(lastObj.getValue());

                            if (!models.containsKey(labelModel)) {
                                models.put(lastObj.getValue(), labelModel);
                            }

                            List<DigitalObjectModel> children = NamedGraphModel.getChildren(lastObj);
                            if (children != null) {
                                modelList.addAll(children);
                            }

                        }

                    } catch (RuntimeException e) {
                        SC.warn(lang.operationFailed() + ": " + e);
                    }
                }
                selObject.setValueMap(models);
                if (isPage) selObject.setValue(DigitalObjectModel.PAGE.getValue());
            }
        });

        selObjDateLayout.addMember(selObjLayout);
        selObjDateLayout.addMember(selIntLayout);
        return selObjDateLayout;
    }

    private HLayout setUserSelect() {
        HLayout userLayout = new HLayout();
        userLayout.setExtraSpace(10);

        selectedUsersGrid = new ListGrid();
        selectedUsersGrid.setWidth(200);
        selectedUsersGrid.setShowAllRecords(true);
        selectedUsersGrid.setCanReorderRecords(true);
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
        usersGrid.setWidth(350);
        usersGrid.setShowAllRecords(true);
        usersGrid.setSelectionType(SelectionStyle.SIMPLE);
        usersGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
        ListGridField nameField = new ListGridField(Constants.ATTR_NAME, lang.name());
        ListGridField surnameField = new ListGridField(Constants.ATTR_SURNAME, lang.surname());
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
        CheckboxItem showChartsForAll =
                new CheckboxItem("showCharts", HtmlCode.bold(lang.showUnifyingCharts()));
        showChartsForAll.setDefaultValue(true);
        showChartsForAllForm.setItems(showChartsForAll);
        showChartsForAllForm.setDisabled(true);
        showChartsForAllForm.setWidth(100);

        usersGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                ListGridRecord[] selectedRecords = usersGrid.getSelectedRecords();
                selectedUsersGrid.setData(selectedRecords);
                if (selectedRecords != null && selectedRecords.length > 1)
                    showChartsForAllForm.setDisabled(false);
                checkShowButton();
            }
        });

        userLayout.addMember(usersGrid);

        selUserLayout.addMember(selectedUsersGrid);
        selUserLayout.addMember(showChartsForAllForm);

        userLayout.addMember(selUserLayout);
        userLayout.setWidth(550);
        return userLayout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return mainLayout;
    }

}
