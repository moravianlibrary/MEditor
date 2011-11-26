
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

import cz.fi.muni.xkremser.editor.client.LangConstants;

public abstract class UuidWindow
        extends UniversalWindow {

    private final TextItem uuidField;

    /**
     * @param modsCollection
     *        the original modsCollectionClient
     * @param lang
     *        the lang
     */
    public UuidWindow(LangConstants lang) {
        super(155, 440, "PID");

        setEdgeOffset(15);

        RegExpValidator regExpValidator = new RegExpValidator();
        regExpValidator
                .setExpression("^.*:([\\da-fA-F]){8}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){12}$");

        final IButton open = new IButton();
        uuidField = new TextItem();
        uuidField.setTitle("PID");
        uuidField.setHint("<nobr>" + lang.withoutPrefix() + "</nobr>");
        uuidField.setValidators(regExpValidator);
        uuidField.setWidth(250);
        uuidField.addKeyPressHandler(new com.smartgwt.client.widgets.form.fields.events.KeyPressHandler() {

            @Override
            public void onKeyPress(com.smartgwt.client.widgets.form.fields.events.KeyPressEvent event) {
                if (event.getKeyName().equals("Enter") && !open.getDisabled()) {
                    doActiton(uuidField);
                }
            }
        });
        uuidField.addChangedHandler(new com.smartgwt.client.widgets.form.fields.events.ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                String text = (String) event.getValue();
                if (text != null && !"".equals(text)) {
                    open.setDisabled(false);
                } else {
                    open.setDisabled(true);
                }
            }
        });
        open.setTitle(lang.open());
        open.setDisabled(true);
        open.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (uuidField.validate()) {
                    doActiton(uuidField);
                }
            }
        });

        DynamicForm form = new DynamicForm();
        form.setMargin(20);
        form.setWidth(140);
        form.setHeight(15);
        form.setFields(uuidField);
        addItem(new HTMLFlow(lang.enterPID()));
        addItem(form);
        addItem(open);
        centerInPage();
        animateShow(AnimationEffect.FLY, null, 300);
        uuidField.focusInItem();
    }

    public TextItem getUuidField() {
        return uuidField;
    }

    protected abstract void doActiton(TextItem uuidField);
}
