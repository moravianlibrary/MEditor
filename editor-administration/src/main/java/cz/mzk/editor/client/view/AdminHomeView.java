/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
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
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.presenter.AdminHomePresenter;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeView.
 */
public class AdminHomeView
        extends ViewImpl
        implements AdminHomePresenter.MyView {

    /** The Constant LOADING. */
    private static final int LOADING = -1;

    /** The Constant NOT_AVAIL. */
    private static final int NOT_AVAIL = 0;

    /** The Constant AVAIL. */
    private static final int AVAIL = 1;

    /** The layout. */
    private final VStack layout;

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
        layout = new VStack();
        layout.setHeight100();
        layout.setPadding(15);
        HTMLFlow html1 = new HTMLFlow();
        html1.setContents(lang.introduction());
        html1.setExtraSpace(15);

        HTMLFlow meditTest =
                new HTMLFlow("<ul><li><a href='http://127.0.0.1:8888/MEditor.html?gwt.codesvr=127.0.0.1:9997'>Meditor test</a> </li></ul>");

        //        checkButton = new IButton(lang.checkAvailability());
        //        checkButton.setAutoFit(true);
        //        checkButton.setExtraSpace(60);

        DataSource dataSource = new DataSource();
        dataSource.setID("regularExpression");

        HTMLFlow html3 = new HTMLFlow();
        html3.setHeight("*");
        html3.setLayoutAlign(VerticalAlignment.BOTTOM);
        html3.setContents(lang.credits());
        html3.setHeight(20);

        layout.addMember(html1);
        layout.addMember(meditTest);
        layout.addMember(html3);
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