package com.company.onlinestore.security;

import com.company.onlinestore.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "StoreStaff", code = "store-staff")
public interface StoreStaffRole {

    public static String CODE = "store-staff";
    @MenuPolicy(menuIds = {"RetailNetwork.browse", "Store.browse", "StoreStaff.browse", "Purchase.browse", "myTasks"})
    @ScreenPolicy(screenIds = {"RetailNetwork.browse", "Store.browse", "StoreStaff.browse", "LoginScreen", "MainScreen", "Product.edit", "RetailNetwork.edit", "Store.edit", "StoreStaff.edit", "Purchase.browse", "Purchase.edit", "PurchaseProduct.edit", "myTasks", "bpm_MyTasks.browse", "bpm_DynamicTaskProcessForm"})
    void screens();

    @EntityAttributePolicy(entityClass = Address.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Address.class, actions = EntityPolicyAction.ALL)
    void address();

    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.READ)
    void customer();

    @EntityAttributePolicy(entityClass = Product.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Product.class, actions = EntityPolicyAction.ALL)
    void product();

    @EntityAttributePolicy(entityClass = RetailNetwork.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = RetailNetwork.class, actions = EntityPolicyAction.READ)
    void retailNetwork();

    @EntityAttributePolicy(entityClass = Store.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Store.class, actions = EntityPolicyAction.ALL)
    void store();

    @EntityAttributePolicy(entityClass = StoreStaff.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = StoreStaff.class, actions = EntityPolicyAction.READ)
    void storeStaff();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @SpecificPolicy(resources = "ui.loginToUi")
    void specific();

    @EntityAttributePolicy(entityClass = Purchase.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Purchase.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.DELETE})
    void purchase();

    @EntityAttributePolicy(entityClass = PurchaseProduct.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = PurchaseProduct.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.DELETE})
    void purchaseProduct();
}