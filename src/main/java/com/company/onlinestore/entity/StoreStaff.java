package com.company.onlinestore.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@JmixEntity
@Entity
public class StoreStaff extends User {
    @JoinTable(name = "STORE_STAFF_STORE_LINK",
            joinColumns = @JoinColumn(name = "STORE_STAFF_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "STORE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Store> store;

    public List<Store> getStore() {
        return store;
    }

    public void setStore(List<Store> store) {
        this.store = store;
    }

    @InstanceName
    @DependsOnProperties({"firstName", "lastName"})
    public String getInstanceName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }
}