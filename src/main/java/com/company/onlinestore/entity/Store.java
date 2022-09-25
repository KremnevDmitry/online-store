package com.company.onlinestore.entity;

import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "STORE", indexes = {
        @Index(name = "IDX_STORE_RETAIL_NETWORK", columnList = "RETAIL_NETWORK_ID")
})
@Entity
public class Store {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "NUMBER_", nullable = false)
    @NotNull
    private String number;

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "ADDRESS_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "ADDRESS_STREET")),
            @AttributeOverride(name = "building", column = @Column(name = "ADDRESS_BUILDING"))
    })
    private Address address;

    @Column(name = "STORE_TYPE", nullable = false)
    @NotNull
    private String storeType;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @JoinColumn(name = "RETAIL_NETWORK_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RetailNetwork retailNetwork;

    @Composition
    @OneToMany(mappedBy = "store")
    private List<Product> product;

    @JoinTable(name = "STORE_STAFF_STORE_LINK",
            joinColumns = @JoinColumn(name = "STORE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "STORE_STAFF_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<StoreStaff> storeStaffs;

    public List<StoreStaff> getStoreStaffs() {
        return storeStaffs;
    }

    public void setStoreStaffs(List<StoreStaff> storeStaffs) {
        this.storeStaffs = storeStaffs;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public RetailNetwork getRetailNetwork() {
        return retailNetwork;
    }

    public void setRetailNetwork(RetailNetwork retailNetwork) {
        this.retailNetwork = retailNetwork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StoreType getStoreType() {
        return storeType == null ? null : StoreType.fromId(storeType);
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType == null ? null : storeType.getId();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}