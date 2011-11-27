
package cz.fi.muni.xkremser.editor.client.view.window;

import java.util.List;

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
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;

/**
 * @author Jiri Kremser
 * @version 13. 11. 2011
 */
public abstract class ConnectExistingObjectWindow
        extends UniversalWindow {

    private final TextItem uuidField;

    private final DigitalObjectModel subject;

    private DigitalObjectModel object;

    private final HTMLFlow availability;

    private final HTMLFlow compatibility;

    private final HTMLFlow info;

    private final IButton connect;

    private final LangConstants lang;

    private final boolean connectToExisting;

    public ConnectExistingObjectWindow(final LangConstants lang,
                                       boolean connectToExisting,
                                       DigitalObjectModel subject) {
        super(210, 450, connectToExisting ? lang.connectToExisting() : lang.connectExistingTo());
        this.lang = lang;
        this.subject = subject;
        this.connectToExisting = connectToExisting;
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
                    hide();
                }
            }
        });
        check.setTitle(lang.checkAvailability());
        availability = new HTMLFlow(lang.availability() + ": ");
        check.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (uuidField.validate()) {
                    checkAvailability(uuidField);
                }
            }
        });

        compatibility = new HTMLFlow(lang.compatibility() + ": ");
        compatibility.setExtraSpace(3);
        info = new HTMLFlow("");
        info.setExtraSpace(5);
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
        HTMLFlow header = new HTMLFlow("<b>" + lang.enterPID() + "</b>");
        header.setExtraSpace(10);
        addItem(header);
        addItem(availability);
        addItem(compatibility);
        addItem(info);
        addItem(form);
        addItem(buttons);
        centerInPage();
        show();
        uuidField.focusInItem();
    }

    private String getStatus(boolean available, LangConstants lang) {
        if (available) {
            return lang.availability() + ": <span class='greenFont'>" + lang.isRunning() + "</span>";
        } else {
            return lang.availability() + ": <span class='redFont'>" + lang.isNotRunning() + "</span>";
        }
    }

    private String getCompatibility(boolean available, LangConstants lang) {
        if (available) {
            return lang.compatibility() + ": <span class='greenFont'>" + lang.compatibilityOk() + "</span>";
        } else {
            return lang.compatibility() + ": <span class='redFont'>" + lang.compatibilityNotOk() + "</span>";
        }
    }

    public TextItem getUuidField() {
        return uuidField;
    }

    public void changeModel(DigitalObjectModel model) {
        this.object = model;
        boolean ok = model != null;
        availability.setContents(getStatus(ok, lang));
        connect.setDisabled(!ok);
        if (!ok) {
            info.setContents("<i>" + lang.objectNotPresent() + "</i>");
            compatibility.setContents(getCompatibility(false, lang));
        } else {
            info.setContents("");
            checkModelCompatibility();
        }
    }

    private void checkModelCompatibility() {
        DigitalObjectModel parent = connectToExisting ? object : subject;
        DigitalObjectModel child = connectToExisting ? subject : object;
        List<DigitalObjectModel> possibleChildren = NamedGraphModel.getChildren(parent);
        boolean ok = possibleChildren != null && possibleChildren.contains(child);
        compatibility.setContents(getCompatibility(ok, lang));
        connect.setDisabled(!ok);
        if (!ok) {
            StringBuffer sb = new StringBuffer();
            sb.append("<i>").append(lang.objectOfType()).append(" ").append(parent.getValue()).append(" ")
                    .append(lang.canContain()).append(": ");
            if (possibleChildren != null && possibleChildren.size() != 0) {
                for (DigitalObjectModel model : possibleChildren) {
                    sb.append(model.getValue()).append(", ");
                }
                sb.deleteCharAt(sb.length() - 2);
            } else {
                sb.append("<i>").append(lang.none());
            }
            sb.append("</i>");
            info.setContents(sb.toString());
        } else {
            info.setContents("");
        }
    }

    public DigitalObjectModel getModel() {
        return this.object;
    }

    protected abstract void doActiton(TextItem uuidField);

    protected abstract void checkAvailability(TextItem uuidField);
}
