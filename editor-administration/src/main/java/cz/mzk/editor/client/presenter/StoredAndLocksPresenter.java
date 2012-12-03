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

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.NameTokens.ADMIN_MENU_BUTTONS;
import cz.mzk.editor.client.uihandlers.StoredAndLocksUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.event.MenuButtonClickedEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class HistoryPresenter.
 * 
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class StoredAndLocksPresenter
        extends Presenter<StoredAndLocksPresenter.MyView, StoredAndLocksPresenter.MyProxy>
        implements StoredAndLocksUiHandlers {

    /** The lang. */
    private final LangConstants lang;

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The left presenter. */
    private final AdminMenuPresenter leftPresenter;

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<StoredAndLocksUiHandlers> {

    }

    /**
     * The Interface MyProxy.
     */
    @ProxyCodeSplit
    @NameToken(NameTokens.ADMIN_MENU_BUTTONS.STORED_AND_LOCKS)
    public interface MyProxy
            extends ProxyPlace<StoredAndLocksPresenter> {

    }

    /**
     * Instantiates a new stored and locks presenter.
     * 
     * @param eventBus
     *        the event bus
     * @param view
     *        the view
     * @param proxy
     *        the proxy
     * @param lang
     *        the lang
     * @param dispatcher
     *        the dispatcher
     * @param leftPresenter
     *        the left presenter
     */
    @Inject
    public StoredAndLocksPresenter(EventBus eventBus,
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

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReset() {
        super.onReset();
        RevealContentEvent.fire(this, Constants.TYPE_ADMIN_LEFT_CONTENT, leftPresenter);
        getEventBus().fireEvent(new MenuButtonClickedEvent(ADMIN_MENU_BUTTONS.STORED_AND_LOCKS, false));
    }

    /**
     * Reveal in parent. {@inheritDoc}
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, Constants.TYPE_ADMIN_MAIN_CONTENT, this);
    }

}
