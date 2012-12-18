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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.shared.event.GetHistoryEvent;
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.action.GetHistoryDaysAction;
import cz.mzk.editor.shared.rpc.action.GetHistoryDaysResult;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserAction;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserResult;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public abstract class HistoryDays
        extends VLayout {

    private final DispatchAsync dispatcher;
    private final LangConstants lang;

    private final HistoryListGrid historyTopGrid;
    private HistoryListGrid historyMonthGrid = null;
    private HistoryListGrid historyYearGrid = null;

    private final EditorDate today;
    private final EditorDate yesterday;

    private static final String ATTR_TEXT_TO_SHOW = "textToShow";
    private static final String ATTR_DATE = "date";

    private List<EditorDate> thisMonth = null;
    private static Map<Integer, String> months = null;
    private final EventBus eventBus;
    private Long userId;
    private String uuid;
    private List<EditorDate> historyData = new ArrayList<EditorDate>();

    private final class HistoryListGrid
            extends ListGrid {

        private final EditorDate relevantDay;
        private Map<Integer, List<EditorDate>> thisYearMonths = null;
        private Map<String, List<EditorDate>> years = null;
        private String attrTextToShow = "";
        List<EditorDate> allData = null;

        /**
         * 
         */
        public HistoryListGrid(EditorDate relevantDay, String textToShow, final boolean showHeader) {
            super();
            this.relevantDay = relevantDay;
            attrTextToShow = textToShow;
            ListGridField history = new ListGridField(attrTextToShow);
            setFields(history);
            setWidth100();
            setShowHeader(showHeader);

            setCanSort(false);

            addCellClickHandler(new CellClickHandler() {

                @Override
                public void onCellClick(CellClickEvent event) {
                    EditorDate selDay = (EditorDate) event.getRecord().getAttributeAsObject(ATTR_DATE);
                    if (selDay == null) {
                        String text = event.getRecord().getAttributeAsString(attrTextToShow);
                        if (months.containsValue(text)) {
                            addNewHistoryGrid(thisYearMonths.get(getIntMonth(text)), text, true, !showHeader);

                        } else if (lang.thisMonth().equals(text)) {
                            addNewHistoryGrid(thisMonth, text, true, !showHeader);
                        } else if (lang.allItems().equals(text)) {
                            if (allData == null) {
                                allData = new ArrayList<EditorDate>();
                                if (thisMonth != null) allData.addAll(thisMonth);
                                allData.addAll(historyData);
                            }
                            addNewHistoryGrid(allData, text, true, !showHeader);
                        } else if (years.containsKey(text)) {
                            addNewHistoryGrid(years.get(text), text, false, !showHeader);
                        } else {
                            SC.warn("There is a problem with history mapping. Please contact the administrator!");
                        }
                    } else {
                        if (!showHeader) removeAllSubgrids();
                        eventBus.fireEvent(new GetHistoryEvent(userId, uuid, selDay, selDay));
                    }
                }
            });
        }

        public void clean() {
            thisYearMonths = null;
            years = null;
            allData = null;
            setData(new ListGridRecord[] {});
        }

        public void analyzeHistoryData(List<EditorDate> historyDataToAnalyze,
                                       int historyDataIndex,
                                       ListGridRecord[] historyItems) {
            for (EditorDate day : historyDataToAnalyze) {
                if (relevantDay != null) {
                    if (day.getYear() == relevantDay.getYear()) {
                        if (thisYearMonths == null) {
                            thisYearMonths = new HashMap<Integer, List<EditorDate>>();
                        }
                        if (thisYearMonths.containsKey(day.getMonth())) {
                            thisYearMonths.get(day.getMonth()).add(day);
                        } else {
                            List<EditorDate> month = new ArrayList<EditorDate>();
                            month.add(day);
                            thisYearMonths.put(day.getMonth(), month);
                            historyItems[historyDataIndex++] =
                                    getDayItem(months.get(day.getMonth()), attrTextToShow);
                        }
                    } else {
                        if (years == null) {
                            years = new HashMap<String, List<EditorDate>>();
                        }
                        if (years.containsKey(String.valueOf(day.getYear()))) {
                            years.get(String.valueOf(day.getYear())).add(day);
                        } else {
                            List<EditorDate> year = new ArrayList<EditorDate>();
                            year.add(day);
                            years.put(String.valueOf(day.getYear()), year);
                            historyItems[historyDataIndex++] =
                                    getDayItem(String.valueOf(day.getYear()), attrTextToShow);
                        }
                    }
                } else {
                    historyItems[historyDataIndex++] = getDayItem(day, attrTextToShow);
                }
            }
            setData(historyItems);
        }
    }

    private void addNewHistoryGrid(List<EditorDate> history,
                                   String textToShow,
                                   boolean isMonth,
                                   boolean fromTop) {
        if (fromTop) {
            removeAllSubgrids();
        } else {
            if (historyMonthGrid != null && contains(historyMonthGrid)) {
                removeMember(historyMonthGrid);
            }
        }
        if (isMonth) {
            historyMonthGrid = new HistoryListGrid(null, textToShow, true);
            historyMonthGrid.analyzeHistoryData(history, 0, new ListGridRecord[history.size()]);
            addMember(historyMonthGrid);
        } else {
            historyYearGrid = new HistoryListGrid(history.get(0), textToShow, true);
            historyYearGrid.analyzeHistoryData(history, 0, new ListGridRecord[history.size()]);
            addMember(historyYearGrid);
        }
        eventBus.fireEvent(new GetHistoryEvent(userId, uuid, history.get(history.size() - 1), history.get(0)));
    }

    /**
     * Removes the all subgrids.
     */
    private void removeAllSubgrids() {
        if (historyMonthGrid != null && contains(historyMonthGrid)) {
            removeMember(historyMonthGrid);
        }
        if (historyYearGrid != null && contains(historyYearGrid)) {
            removeMember(historyYearGrid);
        }
    }

    /**
     * Instantiates a new history grid.
     * 
     * @param dispatcher
     *        the dispatcher
     */
    public HistoryDays(LangConstants lang,
                       DispatchAsync dispatcher,
                       EventBus eventBus,
                       Long userId,
                       String uuid) {
        super();
        this.dispatcher = dispatcher;
        this.lang = lang;
        this.eventBus = eventBus;

        setMonth();

        setWidth("20%");
        setMargin(5);
        setShowEdges(true);
        setEdgeSize(4);
        setEdgeOpacity(60);
        setAnimateMembers(true);

        Date todayDate = new Date();
        Date yesterdayDate = new Date(todayDate.getTime() - 86400000);
        today =
                new EditorDate(Integer.parseInt(DateTimeFormat.getFormat("dd").format(todayDate)),
                               Integer.parseInt(DateTimeFormat.getFormat("MM").format(todayDate)),
                               Integer.parseInt(DateTimeFormat.getFormat("yyyy").format(todayDate)));
        yesterday =
                new EditorDate(Integer.parseInt(DateTimeFormat.getFormat("dd").format(yesterdayDate)),
                               Integer.parseInt(DateTimeFormat.getFormat("MM").format(yesterdayDate)),
                               Integer.parseInt(DateTimeFormat.getFormat("yyyy").format(yesterdayDate)));

        historyTopGrid = new HistoryListGrid(today, ATTR_TEXT_TO_SHOW, false);

        getHistory(userId, uuid);

        addMember(historyTopGrid);

    }

    /**
     * Gets the history.
     * 
     * @param id
     *        the id
     * @param uuid2
     * @return the history
     */
    protected abstract void getHistory(Long id, String uuid2);

    protected void clean() {
        removeAllSubgrids();
        thisMonth = null;
        historyTopGrid.clean();
    }

    public void setHistoryDays(List<EditorDate> hisData) {
        this.historyData = hisData;
        ListGridRecord[] historyItems = new ListGridRecord[25 + (historyData.size() - 14) / 12];
        int historyDataIndex = 0;

        if (!historyData.isEmpty()) {
            historyItems[historyDataIndex++] = getDayItem(lang.allItems(), ATTR_TEXT_TO_SHOW);

            if (today.compareTo(historyData.get(historyDataIndex - 1)) == 0) {
                historyItems[historyDataIndex++] = getDayItem(today, lang.today(), ATTR_TEXT_TO_SHOW);
            }
            if (historyData.size() >= historyDataIndex
                    && yesterday.compareTo(historyData.get(historyDataIndex - 1)) == 0) {
                historyItems[historyDataIndex++] = getDayItem(yesterday, lang.yesterday(), ATTR_TEXT_TO_SHOW);
            }

            boolean thisMonthAdded = false;
            if (thisMonth != null) {
                historyItems[historyDataIndex++] = getDayItem(lang.thisMonth(), ATTR_TEXT_TO_SHOW);
                thisMonthAdded = true;
            }
            for (EditorDate day : historyData) {
                if (day.getMonth() == today.getMonth() && day.getYear() == today.getYear()) {
                    if (thisMonth == null) {
                        thisMonth = new ArrayList<EditorDate>();
                    }
                    thisMonth.add(day);
                    if (!thisMonthAdded) {
                        historyItems[historyDataIndex++] = getDayItem(lang.thisMonth(), ATTR_TEXT_TO_SHOW);
                        thisMonthAdded = true;
                    }
                } else {
                    break;
                }
            }
            if (thisMonth != null) {
                historyData.removeAll(thisMonth);
            }
            if (!historyData.isEmpty()) {
                historyTopGrid.analyzeHistoryData(historyData, historyDataIndex, historyItems);
            } else {
                historyTopGrid.setData(historyItems);
            }
        }
    }

    private ListGridRecord getDayItem(EditorDate day, String attrToShow) {
        return getDayItem(day, day.toString(), attrToShow);
    }

    private ListGridRecord getDayItem(EditorDate day, String textToShow, String attrToShow) {
        ListGridRecord dayItem = new ListGridRecord();
        dayItem.setAttribute(ATTR_DATE, day);
        dayItem.setAttribute(attrToShow, textToShow);
        return dayItem;
    }

    private ListGridRecord getDayItem(String textToShow, String attrToShow) {
        ListGridRecord dayItem = new ListGridRecord();
        dayItem.setAttribute(attrToShow, textToShow);
        return dayItem;
    }

    private int getIntMonth(String month) {
        for (Integer num : months.keySet()) {
            if (months.get(num).equals(month)) return num;
        }
        SC.warn("There is a problem with months mapping. Please contact the administrator!");
        return 0;
    }

    private void setMonth() {
        months = new HashMap<Integer, String>(12);
        months.put(1, lang.january());
        months.put(2, lang.february());
        months.put(3, lang.march());
        months.put(4, lang.april());
        months.put(5, lang.may());
        months.put(6, lang.june());
        months.put(7, lang.july());
        months.put(8, lang.august());
        months.put(9, lang.september());
        months.put(10, lang.october());
        months.put(11, lang.november());
        months.put(12, lang.december());
    }

    /**
     * @param userId
     *        the userId to set
     */
    protected void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the historyTopGrid
     */
    protected HistoryListGrid getHistoryTopGrid() {
        return historyTopGrid;
    }

    /**
     * @return the dispatcher
     */
    public DispatchAsync getDispatcher() {
        return dispatcher;
    }

    /**
     * @param uuid
     *        the uuid to set
     */
    protected void setUuid(String uuid) {
        this.uuid = uuid;
    }

    protected void callForDays() {
        final ModalWindow mw = new ModalWindow(getHistoryTopGrid());
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        if (userId != null && userId < 0) {
            GetLoggedUserAction getLoggedUserAction = new GetLoggedUserAction();
            DispatchCallback<GetLoggedUserResult> callback = new DispatchCallback<GetLoggedUserResult>() {

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void callback(GetLoggedUserResult result) {
                    setUserId(result.getUserId());
                    callForDays(mw);
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void callbackError(Throwable t) {
                    super.callbackError(t);
                    mw.hide();
                }
            };
            dispatcher.execute(getLoggedUserAction, callback);
        } else {
            callForDays(mw);
        }

    }

    private void callForDays(final ModalWindow mw) {
        GetHistoryDaysAction getHistoryDaysAction = new GetHistoryDaysAction(userId, uuid);
        DispatchCallback<GetHistoryDaysResult> callback = new DispatchCallback<GetHistoryDaysResult>() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void callback(GetHistoryDaysResult result) {
                List<EditorDate> days = result.getDays();
                Collections.sort(days);
                setHistoryDays(days);
                mw.hide();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void callbackError(Throwable t) {
                super.callbackError(t);
                mw.hide();
            }
        };
        getDispatcher().execute(getHistoryDaysAction, callback);
    }

}
