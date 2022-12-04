package com.company.onlinestore.screen.product;

import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
public class ProductEdit extends StandardEditor<Product> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (dataManager.load(Product.class)
                .all()
                .list()
                .stream()
                .anyMatch(this::check)) {
            showNotification();
            event.preventCommit();
        }
    }

    protected void showNotification() {
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withPosition(Notifications.Position.TOP_CENTER)
                .withCaption(messages.getMessage(getClass(), "sameProduct"))
                .show();
    }

    protected boolean check(Product product) {
        return getEditedEntity().getName().equals(product.getName()) &&
                getEditedEntity().getDescription().equals(product.getDescription());
    }
}