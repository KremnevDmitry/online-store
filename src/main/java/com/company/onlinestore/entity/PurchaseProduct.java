package com.company.onlinestore.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "PURCHASE_PRODUCT", indexes = {
        @Index(name = "IDX_PURCHASE_PRODUCT_PRODUCT", columnList = "PRODUCT_ID"),
        @Index(name = "IDX_PURCHASE_PRODUCT_PURCHASE", columnList = "PURCHASE_ID")
})
@Entity
public class PurchaseProduct {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "PRICE")
    private BigDecimal price;

    @NotNull
    @Positive
    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;

    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "PURCHASE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Purchase purchase;

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"product"})
    public String getInstanceName() {
        return String.format("%s", product);
    }
}