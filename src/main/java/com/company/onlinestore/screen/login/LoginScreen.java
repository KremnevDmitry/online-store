package com.company.onlinestore.screen.login;

import com.company.onlinestore.entity.Customer;
import com.company.onlinestore.screen.register.RegisterForm;
import io.jmix.core.MessageTools;
import io.jmix.core.Messages;
import io.jmix.securityui.authentication.AuthDetails;
import io.jmix.securityui.authentication.LoginScreenSupport;
import io.jmix.ui.JmixApp;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.builder.AfterScreenCloseEvent;
import io.jmix.ui.component.*;
import io.jmix.ui.component.impl.FileResourceImpl;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.ui.security.UiLoginProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import java.io.File;
import java.util.Locale;

@UiController("LoginScreen")
@UiDescriptor("login-screen.xml")
@Route(path = "login", root = true)
public class LoginScreen extends Screen {

    @Autowired
    private TextField<String> usernameField;

    @Autowired
    private PasswordField passwordField;

    @Autowired
    private CheckBox rememberMeCheckBox;

    @Autowired
    private ComboBox<Locale> localesField;

    @Autowired
    private Notifications notifications;

    @Autowired
    private Messages messages;

    @Autowired
    private MessageTools messageTools;

    @Autowired
    private LoginScreenSupport loginScreenSupport;

    @Autowired
    private JmixApp app;

    private final Logger log = LoggerFactory.getLogger(LoginScreen.class);
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe
    private void onInit(InitEvent event) {
        usernameField.focus();
        initLocalesField();
        initDefaultCredentials();
    }

    private void initLocalesField() {
        localesField.setOptionsMap(messageTools.getAvailableLocalesMap());
        localesField.setValue(app.getLocale());
        localesField.addValueChangeListener(this::onLocalesFieldValueChangeEvent);
    }

    private void onLocalesFieldValueChangeEvent(HasValue.ValueChangeEvent<Locale> event) {
        //noinspection ConstantConditions
        app.setLocale(event.getValue());
        UiControllerUtils.getScreenContext(this).getScreens()
                .create(this.getClass(), OpenMode.ROOT)
                .show();
    }

    private void initDefaultCredentials() {
            usernameField.setValue("");
            passwordField.setValue("");
    }

    @Subscribe("submit")
    private void onSubmitActionPerformed(Action.ActionPerformedEvent event) {
        login();
    }

    private void login() {
        String username = usernameField.getValue();
        String password = passwordField.getValue();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage(getClass(), "emptyUsernameOrPassword"))
                    .show();
            return;
        }

        try {
            loginScreenSupport.authenticate(
                    AuthDetails.of(username, password)
                            .withLocale(localesField.getValue())
                            .withRememberMe(rememberMeCheckBox.isChecked()), this);
        } catch (BadCredentialsException | DisabledException | LockedException e) {
            log.warn("Login failed for user '{}': {}", username, e.toString());
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption(messages.getMessage(getClass(), "loginFailed"))
                    .withDescription(messages.getMessage(getClass(), "badCredentials"))
                    .show();
        }
    }

    @Subscribe("registerButton")
    public void onRegisterButtonClick(Button.ClickEvent event) {
        screenBuilders.editor(Customer.class, this)
                .withScreenClass(RegisterForm.class)
                .newEntity()
                .show();
    }
}
