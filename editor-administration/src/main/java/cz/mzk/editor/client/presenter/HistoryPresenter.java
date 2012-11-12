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

package cz.mzk.editor.client.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.util.SC;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.uihandlers.HistoryUiHandlers;
import cz.mzk.editor.shared.event.GetHistoryEvent;
import cz.mzk.editor.shared.event.GetHistoryEvent.GetHistoryHandler;
import cz.mzk.editor.shared.event.GetHistoryItemInfoEvent;
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.HistoryItem;
import cz.mzk.editor.shared.rpc.HistoryItemInfo;
import cz.mzk.editor.shared.rpc.action.GetHistoryAction;
import cz.mzk.editor.shared.rpc.action.GetHistoryItemInfoAction;
import cz.mzk.editor.shared.rpc.action.GetHistoryItemInfoResult;
import cz.mzk.editor.shared.rpc.action.GetHistoryResult;

// TODO: Auto-generated Javadoc
/**
 * The Class HistoryPresenter.
 * 
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class HistoryPresenter
        extends Presenter<HistoryPresenter.MyView, HistoryPresenter.MyProxy>
        implements HistoryUiHandlers {

    /** The lang. */
    private final LangConstants lang;

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The left presenter. */
    private final AdminMenuPresenter leftPresenter;

    /** The downloaded history. */
    Map<String, List<HistoryItem>> downloadedHistory = new HashMap<String, List<HistoryItem>>();

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<HistoryUiHandlers> {

        /**
         * @param historyItems
         */
        void setHistoryItems(List<HistoryItem> historyItems, boolean isUserH);

        void showHistoryItemInfo(HistoryItemInfo historyItemInfo,
                                 HistoryItem eventHistoryItem,
                                 boolean isUserH);

    }

    /**
     * The Interface MyProxy.
     */
    @ProxyCodeSplit
    @NameToken(NameTokens.ADMIN_MENU_BUTTONS.HISTORY)
    public interface MyProxy
            extends ProxyPlace<HistoryPresenter> {

    }

    /**
     * Instantiates a new history presenter.
     * 
     * @param eventBus
     *        the event bus
     * @param view
     *        the view
     * @param proxy
     *        the proxy
     * @param lang
     *        the lang
     */
    @Inject
    public HistoryPresenter(EventBus eventBus,
                            MyView view,
                            MyProxy proxy,
                            final LangConstants lang,
                            final DispatchAsync dispatcher,
                            final AdminMenuPresenter leftPresenter) {
        super(eventBus, view, proxy);
        this.lang = lang;
        this.dispatcher = dispatcher;
        this.leftPresenter = leftPresenter;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(GetHistoryEvent.getType(), new GetHistoryHandler() {

            @Override
            public void onGetHistory(GetHistoryEvent event) {
                final Long editorUsedId = event.getEditorUsedId();
                String uuid = event.getUuid();
                final EditorDate lowerLimit = event.getLowerLimit();
                final EditorDate upperLimit = event.getUpperLimit();
                final String keyOfMapped =
                        getKeyOfMapped(editorUsedId != null ? editorUsedId.toString() : uuid,
                                       lowerLimit,
                                       upperLimit);
                if (downloadedHistory.containsKey(keyOfMapped)) {
                    getView().setHistoryItems(downloadedHistory.get(keyOfMapped), editorUsedId != null);
                } else {
                    GetHistoryAction historyAction =
                            new GetHistoryAction(editorUsedId, uuid, lowerLimit, upperLimit);
                    DispatchCallback<GetHistoryResult> historyCallback =
                            new DispatchCallback<GetHistoryResult>() {

                                @Override
                                public void callback(GetHistoryResult result) {
                                    List<HistoryItem> historyItems = result.getHistoryItems();
                                    downloadedHistory.put(keyOfMapped, historyItems);
                                    getView().setHistoryItems(historyItems, editorUsedId != null);
                                }

                                @Override
                                public void onFailure(Throwable caught) {
                                    super.onFailure(caught);
                                }
                            };
                    dispatcher.execute(historyAction, historyCallback);
                }
            }
        });

        addRegisteredHandler(GetHistoryItemInfoEvent.getType(),
                             new GetHistoryItemInfoEvent.GetHistoryItemInfoHandler() {

                                 @Override
                                 public void onGetHistoryItemInfo(GetHistoryItemInfoEvent event) {
                                     getHistoryItemInfo(event.getHistoryItem(), event.isUserH());
                                 }
                             });

    }

    private String getKeyOfMapped(String id, EditorDate lowerLimit, EditorDate upperLimit) {
        return id + "#" + lowerLimit.toString() + "#" + upperLimit.toString();
    }

    private void getHistoryItemInfo(final HistoryItem eventHistoryItem, final boolean isUserH) {
        GetHistoryItemInfoAction itemInfoAction =
                new GetHistoryItemInfoAction(eventHistoryItem.getId(), eventHistoryItem.getTableName());

        DispatchCallback<GetHistoryItemInfoResult> itemInfoCallback =
                new DispatchCallback<GetHistoryItemInfoResult>() {

                    @Override
                    public void callback(GetHistoryItemInfoResult result) {
                        if (result.getHistoryItemInfo() != null) {
                            getView().showHistoryItemInfo(result.getHistoryItemInfo(),
                                                          eventHistoryItem,
                                                          isUserH);
                        } else {
                            SC.warn(lang.operationFailed());
                        }
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        super.onFailure(caught);
                    }
                };
        dispatcher.execute(itemInfoAction, itemInfoCallback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReset() {
        super.onReset();
        RevealContentEvent.fire(this, AdminPresenter.TYPE_ADMIN_LEFT_CONTENT, leftPresenter);
    }

    /**
     * Reveal in parent. {@inheritDoc}
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, AdminPresenter.TYPE_ADMIN_MAIN_CONTENT, this);
    }

}
