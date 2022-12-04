package com.company.onlinestore.security;

import com.company.onlinestore.entity.Address;
import com.company.onlinestore.entity.Customer;
import com.company.onlinestore.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "AnonymousRole", code = "anonymous-role")
public interface AnonymousRole {

    String CODE = "anonymous-role";

    @ScreenPolicy(screenIds = "Register.form")
    void screens();

    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ})
    void customer();

    @EntityAttributePolicy(entityClass = Address.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Address.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ})
    void address();

    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.CREATE)
    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void user();

    @EntityAttributePolicy(entityClass = RoleAssignmentEntity.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = RoleAssignmentEntity.class, actions = EntityPolicyAction.CREATE)
    void roleAssignmentEntity();
}