
package cz.fi.muni.xkremser.editor.client.view.window;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

import cz.fi.muni.xkremser.editor.client.LangConstants;

public abstract class UuidWindow
        extends Window {

    private final TextItem uuidField;

    /**
     * @param modsCollection
     *        the original modsCollectionClient
     * @param lang
     *        the lang
     */
    public UuidWindow(LangConstants lang) {
        super();
        setHeight(150);
        setWidth(370);
        setEdgeOffset(15);
        setCanDragResize(true);
        setShowEdges(true);
        setTitle("PID");
        setShowMinimizeButton(false);
        setIsModal(true);
        setShowModalMask(true);

        RegExpValidator regExpValidator = new RegExpValidator();
        regExpValidator
                .setExpression("^.*:([\\da-fA-F]){8}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){12}$");

        final ButtonItem open = new ButtonItem();
        uuidField = new TextItem();
        uuidField.setTitle("PID");
        uuidField.setHint("<nobr>" + lang.withoutPrefix() + "</nobr>");
        uuidField.setValidators(regExpValidator);
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
        open.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                doActiton(uuidField);
            }
        });

        DynamicForm form = new DynamicForm();
        form.setMargin(30);
        form.setWidth(100);
        form.setHeight(15);
        form.setFields(uuidField, open);
        addItem(form);
        centerInPage();
        show();
        uuidField.focusInItem();
    }

    public TextItem getUuidField() {
        return uuidField;
    }

    protected abstract void doActiton(TextItem uuidField);
}
