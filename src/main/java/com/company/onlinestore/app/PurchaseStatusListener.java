package com.company.onlinestore.app;

import com.company.onlinestore.entity.Purchase;
import com.company.onlinestore.entity.PurchaseStatus;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component(PurchaseStatusListener.NAME)
public class PurchaseStatusListener implements ExecutionListener {

    public static final String NAME = "onlinestore_PurchaseStatusListener";

    @Override
    public void notify(DelegateExecution execution) {
        Purchase purchase = (Purchase) execution.getVariable("purchase");
        purchase.setStatus(PurchaseStatus.fromId(execution.getCurrentFlowElement().getName()));
        execution.setVariable("purchase", purchase);
    }
}