package com.company.onlinestore.screen.storestaff;

import com.company.onlinestore.entity.User;
import com.company.onlinestore.security.CustomerRole;
import com.company.onlinestore.security.StoreStaffRole;
import io.jmix.core.EntityStates;
import io.jmix.core.Messages;
import io.jmix.core.Metadata;
import io.jmix.core.security.event.SingleUserPasswordChangeEvent;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.StoreStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@UiController("StoreStaff.edit")
@UiDescriptor("store-staff-edit.xml")
@EditedEntityContainer("storeStaffDc")
public class StoreStaffEdit extends StandardEditor<StoreStaff> {
    protected static final int NOTIFICATION_HIDE_DELAY = 3000;

    @Autowired
    private Notifications notifications;

    @Autowired
    private Messages messages;

    @Autowired
    private EntityStates entityStates;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordField passwordField;

    @Autowired
    private TextField<String> usernameField;

    @Autowired
    private PasswordField confirmPasswordField;

    @Autowired
    private MessageBundle messageBundle;

    @Autowired
    private Metadata metadata;

    @Subscribe
    public void onInitEntity(InitEntityEvent<User> event) {
        usernameField.setEditable(true);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            usernameField.focus();
        }
    }

    @Subscribe
    protected void onBeforeCommit(BeforeCommitChangesEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            if (!Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messageBundle.getMessage("passwordsDoNotMatch"))
                        .show();
                event.preventCommit();
            }

            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));
        }
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostCommit(DataContext.PostCommitEvent event) {
        getApplicationContext().publishEvent(
                new SingleUserPasswordChangeEvent(
                        getEditedEntity().getUsername(), passwordField.getValue()
                )
        );
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        RoleAssignmentEntity roleAssignmentEntity = metadata.create(RoleAssignmentEntity.class);
        roleAssignmentEntity.setRoleCode(StoreStaffRole.CODE);
        roleAssignmentEntity.setUsername(getEditedEntity().getUsername());
        roleAssignmentEntity.setRoleType(RoleAssignmentRoleType.RESOURCE);

        getScreenData().getDataContext().merge(roleAssignmentEntity);
    }

    @Subscribe
    public void onAfterCommitChanges(AfterCommitChangesEvent event) {
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withPosition(Notifications.Position.TOP_CENTER)
                .withCaption(messages.getMessage(getClass(), "register.success"))
                .withHideDelayMs(NOTIFICATION_HIDE_DELAY)
                .show();
    }
}