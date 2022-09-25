package com.company.onlinestore.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "Administrator", code = "administrator")
public interface AdministratorRole {
}