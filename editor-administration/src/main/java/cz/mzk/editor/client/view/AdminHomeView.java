/*
 * Metadata Editor
 * @author Matous Jobanek
 * 
 * 
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

import javax.inject.Inject;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.presenter.AdminHomePresenter;
import cz.mzk.editor.client.uihandlers.AdminHomeUiHandlers;
import cz.mzk.editor.client.util.HtmlCode;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeView.
 */
public class AdminHomeView
        extends ViewWithUiHandlers<AdminHomeUiHandlers>
        implements AdminHomePresenter.MyView {

    /** The layout. */
    private final HStack layout;

    private final LangConstants lang;

    // @Inject
    // public void setLang(LangConstants lang) {
    // this.lang = lang;
    // }

    /**
     * Instantiates a new home view.
     */
    @Inject
    public AdminHomeView(LangConstants lang) {
        this.lang = lang;
        layout = new HStack();
        layout.setHeight100();
        layout.setWidth100();
        layout.setPadding(15);
        layout.setAlign(Alignment.CENTER);

        layout.addMember(getMeditLayout());
    }

    private VLayout getMeditLayout() {

        VLayout meditLayout = new VLayout(2);
        meditLayout.setWidth("75%");
        meditLayout.setLayoutAlign(Alignment.CENTER);
        meditLayout.setAlign(Alignment.CENTER);

        ImgButton meditImg = new ImgButton();
        meditImg.setSrc("MEdit.png");
        meditImg.setHeight(330);
        meditImg.setWidth(500);
        meditImg.setShowRollOver(false);
        meditImg.setShowDown(false);
        meditImg.setShowEdges(true);
        meditImg.setEdgeSize(3);

        meditImg.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().openMedit();
            }
        });

        meditLayout.addMember(getInfo(lang.introduction()));
        meditLayout.addMember(meditImg);
        meditLayout.addMember(getNews());

        return meditLayout;
    }

    private VLayout getNews() {

        VLayout newsLayout = new VLayout();
        newsLayout.addMember(new HTMLFlow(HtmlCode.title(lang.news() + ":", 3)));

        //      TODO

        return newsLayout;
    }

    private Layout getInfo(final String infoText) {
        final Layout info = new VLayout(1);
        info.setExtraSpace(20);
        info.setHeight(100);
        info.setLayoutAlign(Alignment.RIGHT);

        final HTMLFlow infoTextFlow = new HTMLFlow();
        infoTextFlow.setContents(infoText.substring(0, 200) + "...");
        infoTextFlow.setExtraSpace(15);

        final ImgButton readMoreLess = new ImgButton();
        readMoreLess.setSrc("icons/16/unwrap.png");
        readMoreLess.setShowRollOver(false);
        readMoreLess.setShowDown(false);
        readMoreLess.setWidth(16);
        readMoreLess.setHeight(16);
        readMoreLess.setPrompt(lang.readMore());
        readMoreLess.setShowHover(true);
        readMoreLess.setShowEdges(false);

        readMoreLess.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                info.removeMember(infoTextFlow);
                if (readMoreLess.getSrc().equals("icons/16/unwrap.png")) {
                    readMoreLess.setSrc("icons/16/wrap.png");
                    readMoreLess.setPrompt(lang.readLess());
                    infoTextFlow.setContents(infoText);
                } else {
                    readMoreLess.setSrc("icons/16/unwrap.png");
                    readMoreLess.setPrompt(lang.readMore());
                    infoTextFlow.setContents(infoText.substring(0, 200) + "...");
                }

                info.addMember(infoTextFlow, 0);
                infoTextFlow.redraw();
            }
        });

        info.addMember(infoTextFlow);
        info.addMember(readMoreLess);
        return info;
    }

    /**
     * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
     * 
     * @return the widget
     */

    @Override
    public Widget asWidget() {
        return layout;
    }

}