
package cz.fi.muni.xkremser.editor.client.view.other;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;

import cz.fi.muni.xkremser.editor.client.LangConstants;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

public abstract class InfoTab
        extends Tab {

    private final TextItem labelItem;
    private final String originalLabel;
    private final DigitalObjectModel model;
    private final IButton quickEdit = new IButton();
    private Button lockInfoButton = null;
    private final LangConstants lang;
    final Layout lockLayout;

    public InfoTab(String title,
                   String icon,
                   final LangConstants lang,
                   final DigitalObjectDetail detail,
                   String type,
                   String firstPageURL) {
        super(title, icon);
        this.lang = lang;
        String label = detail.getLabel();
        this.model = detail.getModel();;
        this.originalLabel = label;
        this.lockLayout = new Layout();

        VStack layout = new VStack();
        layout.setPadding(15);
        DublinCore dc = detail.getDc();
        final String lockOwner = detail.getLockInfo().getLockOwner();

        if (lockOwner != null) {
            showLockInfoButton("".equals(lockOwner));
        }

        HTMLFlow info = new HTMLFlow("<h2>" + lang.doInfo() + "</h2>");
        info.setExtraSpace(25);
        HTMLFlow pid =
                new HTMLFlow("<b>PID:</b> "
                        + (dc.getIdentifier() != null && dc.getIdentifier().size() > 0 ? dc.getIdentifier()
                                .get(0) : lang.noTitle()));
        pid.setExtraSpace(5);
        HTMLFlow tit =
                new HTMLFlow("<b>"
                        + lang.name()
                        + ":</b> "
                        + (dc.getTitle() != null && dc.getTitle().size() > 0 ? dc.getTitle().get(0)
                                : lang.noTitle()));
        tit.setExtraSpace(5);
        HTMLFlow typ = new HTMLFlow("<b>" + lang.dcType() + ":</b> " + type);
        typ.setExtraSpace(5);
        boolean isPage = DigitalObjectModel.PAGE.equals(model);
        String imgTitle = isPage ? lang.fullImg() : lang.doFirstPage();
        HTMLFlow prev = new HTMLFlow("<b>" + imgTitle + ":</b>");
        prev.setExtraSpace(5);

        labelItem = new TextItem();
        labelItem.setTitle("<b>Label</b>");
        labelItem.setTitleStyle("color: black;");
        labelItem.setValue(label);
        if (label.length() > 25) {
            labelItem.setWidth(label.length() + 200);
        }
        labelItem.setLength(100);
        final DynamicForm form = new DynamicForm();
        form.setFields(labelItem);
        form.setWidth(150);
        form.setExtraSpace(5);
        HTMLFlow img =
                new HTMLFlow("<img class='infoTabImg' src='./images/full/" + (isPage ? "" : "uuid:")
                        + firstPageURL + "' />");

        quickEdit.setTitle(lang.quickEdit());
        quickEdit.setExtraSpace(15);

        if (lockInfoButton != null) {
            if (lockLayout.hasMember(lockInfoButton)) {
                lockLayout.removeMember(lockInfoButton);
            }
            lockLayout.addMember(lockInfoButton);
        }
        lockLayout.setAutoHeight();

        layout.setMembers(lockLayout, info, pid, tit, typ, form, quickEdit, prev, img);

        setPane(layout);
    }

    public String getLabelItem() {
        return labelItem.getValueAsString();
    }

    public String getOriginalLabel() {
        return originalLabel;
    }

    public DigitalObjectModel getModel() {
        return model;
    }

    public IButton getQuickEdit() {
        return quickEdit;
    }

    public Button getLockInfoButton() {
        return lockInfoButton;
    }

    public void showLockInfoButton(boolean isLockByUser) {
        if (lockInfoButton != null && lockLayout.hasMember(lockInfoButton)) {
            lockLayout.removeMember(lockInfoButton);
        }
        lockInfoButton = new Button();
        lockInfoButton.setTitle(lang.lockInfoButton());
        lockInfoButton.setWidth(160);

        if (isLockByUser) {
            lockInfoButton.setIcon("icons/16/lock_lock_all.png");
        } else {
            lockInfoButton.setIcon("icons/16/lock_lock_all_red.png");
        }

        lockInfoButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getCurrentLockInfo();
            }
        });
        lockLayout.addMember(lockInfoButton);
        lockInfoButton.setRight(180);
    }

    public void hideLockInfoButton() {
        if (lockInfoButton != null && lockLayout.hasMember(lockInfoButton)) {
            lockLayout.removeMember(lockInfoButton);
        }
    }

    protected abstract void getCurrentLockInfo();
}
