
package cz.fi.muni.xkremser.editor.request4Adding.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class RequestForAdding
        implements EntryPoint {

    private boolean msgVisible = false;

    @Override
    public void onModuleLoad() {
        // TODO Auto-generated method stub
        final RequestConstants lang = GWT.create(RequestConstants.class);
        final RequestForAddingServiceAsync service = GWT.create(RequestForAddingService.class);

        AsyncCallback<String> callback = new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
                // TODO: Do something with errors.
            }

            @Override
            public void onSuccess(String result) {
                showGUI(result, lang, service);
            }
        };
        service.getName(callback);

    }

    public synchronized boolean isMsgVisible() {
        return msgVisible;
    }

    public synchronized void setMsgVisible(boolean msgVisible) {
        this.msgVisible = msgVisible;
    }

    private void showGUI(String name, final RequestConstants lang, final RequestForAddingServiceAsync service) {
        VLayout layout = new VLayout();
        layout.setPadding(20);
        layout.setWidth("98%");
        layout.setAlign(Alignment.CENTER);

        HTMLFlow langSelection = new HTMLFlow();
        langSelection.setHeight(17);
        langSelection.setWidth(63);
        final boolean en =
                LocaleInfo.getCurrentLocale().getLocaleName() != null
                        && LocaleInfo.getCurrentLocale().getLocaleName().startsWith("en");
        langSelection.setStyleName(en ? "langSelectionEN" : "langSelectionCZ");
        langSelection.setCursor(Cursor.HAND);
        langSelection.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                RequestForAdding.langRefresh(en ? "cs_CZ" : "en_US");
            }
        });
        langSelection.setExtraSpace(30);

        HTMLFlow line1 = new HTMLFlow("<H2>" + lang.authFailed() + "</H2>");
        line1.setExtraSpace(15);
        HTMLFlow line2 = new HTMLFlow(lang.recordMissing() + name + "." + "<br /> " + lang.ifYouWant());
        line2.setExtraSpace(10);

        final Canvas anim = new Canvas();
        anim.setExtraSpace(450);
        anim.setPadding(5);
        anim.setOverflow(Overflow.HIDDEN);
        anim.setBorder("1px solid #6a6a6a");
        anim.setBackgroundColor("#C3D9FF");
        anim.setDragAppearance(DragAppearance.TARGET);
        anim.setSmoothFade(true);
        anim.setLeft(100);
        anim.setTop(250);
        anim.setWidth(350);
        anim.setHeight(120);
        anim.setVisible(false);

        Button addButton = new Button(lang.send());
        addButton.setExtraSpace(25);
        addButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                AsyncCallback<String> callback = new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        // TODO: Do something with errors.
                    }

                    @Override
                    public void onSuccess(String result) {
                        if (result == null) {
                            result = lang.exist();
                        } else {
                            result = lang.added1() + "\n" + lang.added2() + result;
                        }
                        anim.setContents(result);
                        anim.animateShow(AnimationEffect.FADE, null, 750);
                        setMsgVisible(true);
                    }
                };
                service.ask(callback);
            }
        });

        layout.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (isMsgVisible()) {
                    anim.animateHide(AnimationEffect.FADE, null, 750);
                    setMsgVisible(false);
                }
            }
        });
        layout.addMember(langSelection);
        layout.addMember(line1);
        layout.addMember(line2);
        layout.addMember(addButton);
        layout.addMember(anim);
        layout.addMember(new HTMLFlow(" "));

        RootPanel.get().add(layout);
    }

    public static native void langRefresh(String locale)/*-{
                                                        													var pos = $wnd.location.search.indexOf('&locale=');
                                                        													var params = $wnd.location.search;
                                                        													if (pos == -1) {
                                                        													$wnd.location.search = params + '&locale=' + locale;
                                                        													} else {
                                                        													$wnd.location.search = params.substring(0, pos) + '&locale='
                                                        													+ locale + params.substring(pos + 13, params.length);
                                                        													}
                                                        													}-*/;

}
