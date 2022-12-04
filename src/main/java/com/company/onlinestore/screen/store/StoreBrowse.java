package com.company.onlinestore.screen.store;

import com.company.onlinestore.entity.*;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UiController("Store.browse")
@UiDescriptor("store-browse.xml")
@LookupComponent("storesTable")
public class StoreBrowse extends StandardLookup<Store> {

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private CollectionLoader<Store> storesDl;
    @Autowired
    private DataManager dataManager;

    protected boolean load = false;

    @Subscribe
    public void onInit(InitEvent event) {
        UUID id = ((User) currentAuthentication.getUser()).getId();

        dataManager.load(StoreStaff.class)
                .id(id)
                .optional()
                .ifPresent(this::openWithStoreStaff);

        if (!load) {
            storesDl.setLoadDelegate(purchaseLoadContext -> dataManager.load(Store.class).all().list());
        }
    }

    protected void openWithStoreStaff(StoreStaff storeStaff) {
        load = true;
        storesDl.setLoadDelegate(purchaseLoadContext -> {
            List<Store> loadedStores = dataManager.load(Store.class).all().list();

            return loadedStores.stream()
                    .filter(store -> store.getStoreStaffs().contains(storeStaff))
                    .collect(Collectors.toList());
        });
    }
}