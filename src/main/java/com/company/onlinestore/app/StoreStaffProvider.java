package com.company.onlinestore.app;

import com.company.onlinestore.entity.Purchase;
import com.company.onlinestore.entity.StoreStaff;
import io.jmix.bpm.provider.UserProvider;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;

@UserProvider(value = "onlinestore_StoreStaffProvider", description = "Returns a storeStaff with store")
public class StoreStaffProvider {

    @Autowired
    private DataManager dataManager;

    public String getUserByStore(Purchase purchase) {
        return dataManager.load(StoreStaff.class)
                .all()
                .list()
                .stream()
                .filter(storeStaff -> purchase.getStore().getStoreStaffs().contains(storeStaff))
                .findAny()
                .orElse(null)
                .getUsername();
    }
}