package com.company.onlinestore.security;

import com.company.onlinestore.entity.*;
import io.jmix.bpm.entity.ProcessDefinitionData;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "CustomerRole", code = "customer-role")
public interface CustomerRole {

    String CODE = "customer-role";

    @EntityAttributePolicy(entityClass = Address.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Address.class, actions = EntityPolicyAction.ALL)
    void address();

    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    void customer();

    @EntityAttributePolicy(entityClass = Purchase.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Purchase.class, actions = EntityPolicyAction.ALL)
    void purchase();

    @EntityAttributePolicy(entityClass = PurchaseProduct.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = PurchaseProduct.class, actions = EntityPolicyAction.ALL)
    void purchaseProduct();

    @EntityAttributePolicy(entityClass = Store.class, attributes = {"number", "address", "storeType", "name", "retailNetwork", "product"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Store.class, actions = EntityPolicyAction.READ)
    void store();

    @MenuPolicy(menuIds = {"Store.browse", "RetailNetwork.browse", "Purchase.browse", "myTasks"})
    @ScreenPolicy(screenIds = {"LoginScreen", "MainScreen", "Store.browse", "Store.edit", "RetailNetwork.browse", "StoreStaff.browse", "RetailNetwork.edit", "Product.edit", "Customer.edit", "StoreStaff.edit", "Purchase.browse", "Purchase.edit", "PurchaseProduct.edit", "Product.browse", "myTasks", "bpm_MyTasks.browse", "bpm_DynamicTaskProcessForm"})
    void screens();

    @EntityAttributePolicy(entityClass = Product.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Product.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void product();

    @EntityAttributePolicy(entityClass = RetailNetwork.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = RetailNetwork.class, actions = EntityPolicyAction.READ)
    void retailNetwork();

    @EntityAttributePolicy(entityClass = StoreStaff.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = StoreStaff.class, actions = EntityPolicyAction.READ)
    void storeStaff();

    @SpecificPolicy(resources = "ui.loginToUi")
    void specific();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @EntityAttributePolicy(entityClass = ProcessDefinitionData.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = ProcessDefinitionData.class, actions = EntityPolicyAction.READ)
    void processDefinitionData();
}