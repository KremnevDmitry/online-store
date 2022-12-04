package com.company.onlinestore.screen.purchase;

import com.company.onlinestore.entity.*;
import com.google.common.collect.ImmutableMap;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.Messages;
import io.jmix.core.SaveContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.RandomUtils;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@UiController("Purchase.edit")
@UiDescriptor("purchase-edit.xml")
@EditedEntityContainer("purchaseDc")
public class PurchaseEdit extends StandardEditor<Purchase> {

    protected static final int NOTIFICATION_HIDE_DELAY = 3000;

    @Autowired
    protected ComboBox<PurchaseStatus> statusField;
    @Autowired
    protected TextField<BigDecimal> priceField;
    @Autowired
    protected EntityPicker<Customer> customerField;

    @Autowired
    protected EntityStates entityStates;
    @Autowired
    protected CurrentAuthentication currentAuthentication;
    @Autowired
    protected DataManager dataManager;
    @Autowired
    private TextField<Integer> numberField;
    @Autowired
    private Table<PurchaseProduct> purchaseProductsTable;
    @Autowired
    private CollectionPropertyContainer<PurchaseProduct> purchaseProductsDc;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;
    @Autowired
    private RuntimeService runtimeService;

    protected boolean isNew = false;
    @Autowired
    private EntityPicker<Store> storeField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            statusField.setValue(PurchaseStatus.NEW);
            priceField.setValue(BigDecimal.ZERO);
            numberField.setValue(RandomUtils.nextInt());
            purchaseProductsDc.addCollectionChangeListener(e -> recalculatePrice());
        } else {
            storeField.setEditable(false);
        }

        purchaseProductsTable.getActions()
                .forEach(action -> action.setEnabled(false));
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<Purchase> event) {
        isNew = true;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            Customer currentCustomer = findCurrentCustomer();
            if (currentCustomer == null) {
                preventOpen(event);
            } else {
                customerField.setValue(currentCustomer);
            }
        }
    }

    @Subscribe("storeField")
    public void onStoreFieldValueChange(HasValue.ValueChangeEvent<Store> event) {
        if (entityStates.isNew(getEditedEntity())) {
            purchaseProductsDc.getMutableItems().clear();
            purchaseProductsTable.getActions()
                    .forEach(
                            action -> action.setEnabled(event.getValue() != null)
                    );
        }
    }

    protected Customer findCurrentCustomer() {
        UUID id = ((User) currentAuthentication.getUser()).getId();
        return dataManager.load(Customer.class)
                .id(id)
                .one();
    }

    private void preventOpen(AfterShowEvent event) {
        event.getSource().closeWithDefaultAction();
        showNotification();
    }

    protected void showNotification() {
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withPosition(Notifications.Position.TOP_CENTER)
                .withCaption(messages.getMessage(getClass(), "noFoundCustomerCaption"))
                .withDescription(messages.getMessage(getClass(), "noFoundCustomerDescription"))
                .withHideDelayMs(NOTIFICATION_HIDE_DELAY)
                .show();
    }

    protected void recalculatePrice() {
        BigDecimal finalPrice = BigDecimal.ZERO;
        for (PurchaseProduct product : purchaseProductsDc.getMutableItems()) {
            finalPrice = finalPrice.add(product.getPrice());
        }

        priceField.setValue(finalPrice);
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (purchaseProductsDc.getItems().isEmpty()) {
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withPosition(Notifications.Position.MIDDLE_CENTER)
                    .withCaption(messages.getMessage(getClass(), "chooseSomeone"))
                    .withHideDelayMs(NOTIFICATION_HIDE_DELAY)
                    .show();
            event.preventCommit();
        }

        getScreenData().getDataContext().merge(purchaseProductsDc.getItems());
    }

    @Subscribe
    public void onAfterClose(AfterCloseEvent event) {
        if (isNew && event.closedWith(StandardOutcome.COMMIT)) {
            SaveContext saveContext = new SaveContext();
            Map<String, Object> params = ImmutableMap.of(
                    "customer", getEditedEntity().getCustomer(),
                    "purchase", getEditedEntity());

            runtimeService.startProcessInstanceByKey(
                    "purchase-process",
                    "Purchase number:'" + getEditedEntity().getNumber() + "' Customer:" + getEditedEntity().getCustomer().getInstanceName(),

                    params);
            saveContext.saving(getEditedEntity());

            List<Product> products = getEditedEntity().getPurchaseProducts().stream()
                    .map(purchaseProduct -> {
                        Product product = purchaseProduct.getProduct();
                        product.setQuantity(product.getQuantity() - purchaseProduct.getAmount());
                        return product;
                    })
                    .collect(Collectors.toList());

            saveContext.saving(products);

            dataManager.save(saveContext);
        }
    }
}