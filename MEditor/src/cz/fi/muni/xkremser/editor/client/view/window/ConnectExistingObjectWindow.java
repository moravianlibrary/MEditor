
package cz.fi.muni.xkremser.editor.client.view.window;

import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;

/**
 * @author Jiri Kremser
 * @version 13. 11. 2011
 */
public abstract class ConnectExistingObjectWindow
        extends UniversalWindow {

    private final TextItem uuidField;

    private DigitalObjectModel model;

    private final HTMLFlow availability;

    private final IButton connect;

    private final LangConstants lang;

    public ConnectExistingObjectWindow(final LangConstants lang, boolean connectToExisting) {
        super(175, 440, connectToExisting ? lang.connectToExisting() : lang.connectExistingTo());

        this.lang = lang;
        setEdgeOffset(15);

        RegExpValidator regExpValidator = new RegExpValidator();
        regExpValidator
                .setExpression("^.*:([\\da-fA-F]){8}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){12}$");

        connect = new IButton();
        final IButton check = new IButton();
        uuidField = new TextItem();
        uuidField.setTitle("PID");
        uuidField.setHint("<nobr>" + lang.withoutPrefix() + "</nobr>");
        uuidField.setValidators(regExpValidator);
        uuidField.setWidth(250);
        uuidField.addKeyPressHandler(new com.smartgwt.client.widgets.form.fields.events.KeyPressHandler() {

            @Override
            public void onKeyPress(com.smartgwt.client.widgets.form.fields.events.KeyPressEvent event) {
                if (event.getKeyName().equals("Enter") && !connect.getDisabled()) {
                    doActiton(uuidField);
                }
            }
        });
        uuidField.addChangedHandler(new com.smartgwt.client.widgets.form.fields.events.ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                String text = (String) event.getValue();
                if (text != null && !"".equals(text)) {
                    check.setDisabled(false);
                } else {
                    check.setDisabled(true);
                    connect.setDisabled(true);
                }
            }
        });
        connect.setTitle(lang.connect());
        connect.setDisabled(true);
        check.setDisabled(true);
        connect.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (uuidField.validate()) {
                    doActiton(uuidField);
                    animateHide(AnimationEffect.FLY, null, 300);
                    destroy();
                }
            }
        });
        check.setTitle(lang.checkAvailability());
        availability = new HTMLFlow(lang.availability() + ": ");
        availability.setExtraSpace(5);
        check.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (uuidField.validate()) {
                    checkAvailability(uuidField);
                }
            }
        });
        DynamicForm form = new DynamicForm();
        form.setMargin(15);
        form.setWidth(100);
        form.setHeight(15);
        form.setFields(uuidField);

        check.setAutoFit(true);
        check.setExtraSpace(8);
        connect.setAutoFit(true);

        HLayout buttons = new HLayout();
        buttons.setMembers(check, connect);
        HTMLFlow header = new HTMLFlow(lang.enterPID());
        header.setExtraSpace(3);
        addItem(header);
        addItem(availability);
        addItem(form);
        addItem(buttons);
        centerInPage();
        animateShow(AnimationEffect.FLY, null, 300);
        uuidField.focusInItem();
    }

    private String getStatus(boolean available, LangConstants lang) {
        if (available) {
            return lang.availability() + ": <span class='greenFont'>" + lang.isRunning() + "</span>";
        } else {
            return lang.availability() + ": <span class='redFont'>" + lang.isNotRunning() + "</span>";
        }
    }

    public TextItem getUuidField() {
        return uuidField;
    }

    public void changeModel(DigitalObjectModel model) {
        this.model = model;
        availability.setContents(getStatus(model != null, lang));
        connect.setDisabled(model == null);
    }

    protected abstract void doActiton(TextItem uuidField);

    protected abstract void checkAvailability(TextItem uuidField);
}
