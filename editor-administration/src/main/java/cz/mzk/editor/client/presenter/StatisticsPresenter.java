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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.NameTokens.ADMIN_MENU_BUTTONS;
import cz.mzk.editor.client.config.EditorClientConfiguration;
import cz.mzk.editor.client.other.LabelAndModelConverter;
import cz.mzk.editor.client.uihandlers.StatisticsUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.NamedGraphModel;
import cz.mzk.editor.shared.event.ConfigReceivedEvent;
import cz.mzk.editor.shared.event.ConfigReceivedEvent.ConfigReceivedHandler;
import cz.mzk.editor.shared.event.MenuButtonClickedEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class HistoryPresenter.
 * 
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class StatisticsPresenter
        extends Presenter<StatisticsPresenter.MyView, StatisticsPresenter.MyProxy>
        implements StatisticsUiHandlers {

    /** The left presenter. */
    private final AdminMenuPresenter leftPresenter;

    private final EditorClientConfiguration config;

    private final LangConstants lang;

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<StatisticsUiHandlers> {

        SelectItem getSelObject();
    }

    /**
     * The Interface MyProxy.
     */
    @ProxyCodeSplit
    @NameToken(NameTokens.ADMIN_MENU_BUTTONS.STATISTICS)
    public interface MyProxy
            extends ProxyPlace<StatisticsPresenter> {

    }

    /**
     * Instantiates a new statistics presenter.
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
    public StatisticsPresenter(EventBus eventBus,
                               MyView view,
                               MyProxy proxy,
                               final LangConstants lang,
                               final DispatchAsync dispatcher,
                               final EditorClientConfiguration config,
                               final AdminMenuPresenter leftPresenter) {
        super(eventBus, view, proxy);
        this.leftPresenter = leftPresenter;
        this.config = config;
        this.lang = lang;
        bind();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onBind() {
        super.onBind();
        if (config != null && config.getConfiguration() != null) {
            setSelDocument(config.getDocumentTypes());
        }

        addRegisteredHandler(ConfigReceivedEvent.getType(), new ConfigReceivedHandler() {

            @Override
            public void onConfigReceived(ConfigReceivedEvent event) {
                String[] documentTypes;
                if (event.isStatusOK()) {
                    documentTypes = config.getDocumentTypes();
                } else {
                    documentTypes = EditorClientConfiguration.Constants.DOCUMENT_DEFAULT_TYPES;
                }
                setSelDocument(documentTypes);
            }
        });
    }

    private void setSelDocument(String[] documentTypes) {
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
                    String labelModel = LabelAndModelConverter.getLabelFromModel().get(lastObj.getValue());

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
        getView().getSelObject().setValueMap(models);
        if (isPage) getView().getSelObject().setDefaultValue(DigitalObjectModel.PAGE.getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReset() {
        super.onReset();
        RevealContentEvent.fire(this, Constants.TYPE_ADMIN_LEFT_CONTENT, leftPresenter);
        getEventBus().fireEvent(new MenuButtonClickedEvent(ADMIN_MENU_BUTTONS.STATISTICS, false));
    }

    /**
     * Reveal in parent. {@inheritDoc}
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, Constants.TYPE_ADMIN_MAIN_CONTENT, this);
    }

}
