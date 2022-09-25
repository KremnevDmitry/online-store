package com.company.onlinestore.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "CustomerRole", code = "customer-role")
public interface CustomerRole {
}