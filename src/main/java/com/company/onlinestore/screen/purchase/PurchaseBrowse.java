package com.company.onlinestore.screen.purchase;

import com.company.onlinestore.entity.*;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UiController("Purchase.browse")
@UiDescriptor("purchase-browse.xml")
@LookupComponent("purchasesTable")
public class PurchaseBrowse extends StandardLookup<Purchase> {

    @Autowired
    private CollectionLoader<Purchase> purchasesDl;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private DataManager dataManager;

    protected boolean load = false;

    @Subscribe
    public void onInit(InitEvent event) {
        UUID id = ((User) currentAuthentication.getUser()).getId();
        dataManager.load(Customer.class)
                .id(id)
                .optional()
                .ifPresent(this::openWithCustomer);

        dataManager.load(StoreStaff.class)
                .id(id)
                .optional()
                .ifPresent(this::openWithStoreStaff);

        if (!load) {
            purchasesDl.setLoadDelegate(purchaseLoadContext -> dataManager.load(Purchase.class).all().list());
        }
    }

    protected void openWithStoreStaff(StoreStaff storeStaff) {
        load = true;
        purchasesDl.setLoadDelegate(purchaseLoadContext -> {
            List<Purchase> purchases = dataManager.load(Purchase.class).all().list();
            List<Store> stores = storeStaff.getStore();

            return purchases.stream()
                    .filter(purchase -> stores.contains(purchase.getStore()))
                    .collect(Collectors.toList());
        });
    }

    protected void openWithCustomer(Customer customer) {
        load = true;
        purchasesDl.setLoadDelegate(purchaseLoadContext -> {
            List<Purchase> purchases = dataManager.load(Purchase.class).all().list();

            return purchases.stream()
                    .filter(purchase -> purchase.getCustomer().equals(customer))
                    .collect(Collectors.toList());
        });
    }
}