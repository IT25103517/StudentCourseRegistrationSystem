package com.university.management.service;

import com.university.management.model.Admin;
import java.util.Optional;

public interface AdminService extends ManagementService<Admin> {
    Optional<Admin> authenticate(String username, String password);
    Optional<Admin> findByUsername(String username);
}