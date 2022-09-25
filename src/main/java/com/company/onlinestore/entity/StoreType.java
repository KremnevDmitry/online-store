package com.company.onlinestore.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum StoreType implements EnumClass<String> {

    CONVENIENCE("Convenience"),
    SUPERMARKET("Supermarket"),
    HYPERMARKET("Hypermarket");

    private final String id;

    StoreType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nullable
    public static StoreType fromId(String id) {
        for (StoreType at : StoreType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}