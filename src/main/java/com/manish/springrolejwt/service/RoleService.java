package com.manish.springrolejwt.service;

import com.manish.springrolejwt.model.Role;

public interface RoleService {
    Role findByName(String name);
}
