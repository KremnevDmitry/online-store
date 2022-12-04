package com.company.onlinestore.screen.purchaseproduct;

import com.company.onlinestore.entity.Product;
import com.company.onlinestore.entity.Purchase;
import com.company.onlinestore.entity.Store;
import com.company.onlinestore.screen.product.ProductBrowse;
import io.jmix.core.EntityStates;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.PurchaseProduct;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UiController("PurchaseProduct.edit")
@UiDescriptor("purchase-product-edit.xml")
@EditedEntityContainer("purchaseProductDc")
public class PurchaseProductEdit extends StandardEditor<PurchaseProduct> {


    @Autowired
    private TextField<BigDecimal> priceField;
    @Autowired
    private EntityStates entityStates;
    @Autowired
    private TextField<Integer> amountField;
    @Autowired
    private EntityPicker<Product> productField;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            priceField.setValue(BigDecimal.ZERO);
        }

        amountField.addValueChangeListener(this::amountListener);
        productField.addValueChangeListener(this::productListener);
    }

    @Install(to = "productField.entityLookup", subject = "screenConfigurer")
    private void productFieldEntityLookupScreenConfigurer(Screen screen) {
        Purchase purchase = getEditedEntity().getPurchase();
        Store store = purchase.getStore();

        List<PurchaseProduct> purchasePrd = purchase.getPurchaseProducts();
        if (purchasePrd != null) {
            List<Product> purchaseProducts = purchasePrd
                    .stream()
                    .map(PurchaseProduct::getProduct)
                    .collect(Collectors.toList());
            ((ProductBrowse) screen).setExcludedProducts(purchaseProducts);
        } else {
            ((ProductBrowse) screen).setExcludedProducts(Collections.emptyList());
        }

        ((ProductBrowse) screen).setStore(store);
    }

    protected void amountListener(HasValue.ValueChangeEvent<Integer> integerValueChangeEvent) {
        Integer amount = integerValueChangeEvent.getValue();
        if (productField.getValue() != null && amount != null && amount > 0) {
            BigDecimal sum = productField.getValue().getPrice().multiply(BigDecimal.valueOf(amount.longValue()));
            priceField.setValue(sum);
        }
    }

    protected void productListener(HasValue.ValueChangeEvent<Product> productValueChangeEvent) {
        Integer amount = amountField.getValue();
        Product product = productValueChangeEvent.getValue();
        if (amount != null && amount > 0 && product != null) {
            BigDecimal sum = product.getPrice().multiply(BigDecimal.valueOf(amount.longValue()));
            priceField.setValue(sum);
        }
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        Integer amount = amountField.getValue();
        if (amount != null && getEditedEntity().getProduct().getQuantity() < amount) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption(messages.getMessage(getClass(), "wrongAmount"))
                    .withPosition(Notifications.Position.TOP_CENTER)
                    .show();
            event.preventCommit();
        }
    }
}