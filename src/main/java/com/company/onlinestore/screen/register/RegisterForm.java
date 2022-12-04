package com.company.onlinestore.screen.register;

import com.company.onlinestore.entity.User;
import com.company.onlinestore.security.CustomerRole;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.Messages;
import io.jmix.core.Metadata;
import io.jmix.core.security.event.SingleUserPasswordChangeEvent;
import io.jmix.security.authentication.RoleGrantedAuthority;
import io.jmix.security.model.ResourceRole;
import io.jmix.security.role.ResourceRoleRepository;
import io.jmix.security.role.assignment.RoleAssignmentProvider;
import io.jmix.security.role.assignment.RoleAssignmentRepository;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Image;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.impl.FileResourceImpl;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.Customer;
import io.jmix.ui.settings.UiSettingsCache;
import io.jmix.ui.theme.ThemeVariantsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

@UiController("Register.form")
@UiDescriptor("register-form.xml")
@EditedEntityContainer("customerDc")
@Route(path = "register")
public class RegisterForm extends StandardEditor<Customer> {

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
    @Autowired
    private DataManager dataManager;

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
        if (dataManager.load(Customer.class)
                .all()
                .list()
                .stream()
                .anyMatch(customer -> customer.getUsername().equals(usernameField.getValue()))) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withPosition(Notifications.Position.MIDDLE_CENTER)
                    .withCaption(messages.getMessage(getClass(), "usernameConstraint"))
                    .withHideDelayMs(NOTIFICATION_HIDE_DELAY)
                    .show();
            event.preventCommit();
        }

        RoleAssignmentEntity roleAssignmentEntity = metadata.create(RoleAssignmentEntity.class);
        roleAssignmentEntity.setRoleCode(CustomerRole.CODE);
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