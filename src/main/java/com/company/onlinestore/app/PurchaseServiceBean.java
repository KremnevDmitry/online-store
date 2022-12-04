package com.company.onlinestore.app;

import com.company.onlinestore.entity.Purchase;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(PurchaseServiceBean.NAME)
public class PurchaseServiceBean {
    public static final String NAME = "onlinestore_OnlinePurchaseService";

    @Autowired
    private DataManager dataManager;

    public void savePurchaseStatusInDB(Purchase purchase) {
        dataManager.save(purchase);
    }
}