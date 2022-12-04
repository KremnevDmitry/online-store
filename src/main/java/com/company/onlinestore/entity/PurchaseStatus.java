package com.company.onlinestore.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum PurchaseStatus implements EnumClass<String> {

    NEW("New"),
    ACCEPTED("Accepted"),
    CONFIRMED("Confirmed"),
    PAYMENT_PENDING("Payment pending"),
    PAID("Paid"),
    AWAITING_SHIPMENT("Awaiting shipment"),
    SENTED("Sented"),
    DELIVERED("Delivered"),
    CANCELED("Candeled"),
    COMPLETED("Completed");

    private final String id;

    PurchaseStatus(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nullable
    public static PurchaseStatus fromId(String id) {
        for (PurchaseStatus at : PurchaseStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}