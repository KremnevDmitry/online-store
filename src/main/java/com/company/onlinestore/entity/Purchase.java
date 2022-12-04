package com.company.onlinestore.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "PURCHASE", indexes = {
        @Index(name = "IDX_PURCHASE_STORE", columnList = "STORE_ID"),
        @Index(name = "IDX_PURCHASE_CUSTOMER", columnList = "CUSTOMER_ID")
})
@Entity
public class Purchase {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "STORE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Store store;

    @NotNull
    @JmixGeneratedValue
    @Column(name = "NUMBER_", nullable = false)
    private Integer number;

    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer customer;

    @Column(name = "PRICE", precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "STATUS")
    private String status;

    @Composition
    @OneToMany(mappedBy = "purchase")
    private List<PurchaseProduct> purchaseProducts;

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public PurchaseStatus getStatus() {
        return status == null ? null : PurchaseStatus.fromId(status);
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<PurchaseProduct> getPurchaseProducts() {
        return purchaseProducts;
    }

    public void setPurchaseProducts(List<PurchaseProduct> purchaseProducts) {
        this.purchaseProducts = purchaseProducts;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"number"})
    public String getInstanceName() {
        return String.format("%s", number);
    }
}