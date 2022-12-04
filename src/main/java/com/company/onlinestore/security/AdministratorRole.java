package com.company.onlinestore.security;

import com.company.onlinestore.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "Administrator", code = "administrator")
public interface AdministratorRole {
    @MenuPolicy(menuIds = {"User.browse", "RetailNetwork.browse", "Store.browse", "StoreStaff.browse"})
    @ScreenPolicy(screenIds = {"ChangePasswordDialog", "ResetPasswordDialog", "User.browse", "User.edit", "sec_RoleAssignmentFragment", "sec_RoleAssignmentScreen", "sec_RoleFilterFragment", "sec_RowLevelRoleModel.browse", "RetailNetwork.browse", "Store.browse", "StoreStaff.browse", "LoginScreen", "MainScreen", "StoreStaff.edit", "Store.edit", "RetailNetwork.edit", "Product.edit", "Customer.edit", "sec_ResourceRoleModel.browse", "sec_ResourceRoleModel.lookup", "sec_RowLevelRoleModel.lookup"})
    void screens();

    @EntityAttributePolicy(entityClass = Address.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Address.class, actions = EntityPolicyAction.ALL)
    void address();

    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    void customer();

    @EntityAttributePolicy(entityClass = Product.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Product.class, actions = EntityPolicyAction.ALL)
    void product();

    @EntityAttributePolicy(entityClass = Purchase.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Purchase.class, actions = EntityPolicyAction.ALL)
    void purchase();

    @EntityAttributePolicy(entityClass = PurchaseProduct.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = PurchaseProduct.class, actions = EntityPolicyAction.ALL)
    void purchaseProduct();

    @EntityAttributePolicy(entityClass = RetailNetwork.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = RetailNetwork.class, actions = EntityPolicyAction.ALL)
    void retailNetwork();

    @EntityAttributePolicy(entityClass = Store.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Store.class, actions = EntityPolicyAction.ALL)
    void store();

    @EntityAttributePolicy(entityClass = StoreStaff.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = StoreStaff.class, actions = EntityPolicyAction.ALL)
    void storeStaff();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.ALL)
    void user();

    @SpecificPolicy(resources = "ui.loginToUi")
    void specific();

    @EntityPolicy(entityClass = RoleAssignmentEntity.class, actions = EntityPolicyAction.CREATE)
    void roleAssignmentEntity();
}