package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * =====================================================
 * ADMIN ENTITY — Extends Person
 * =====================================================
 * Managed by: Ramanan
 *
 * OOP CONCEPTS:
 * - INHERITANCE: BaseEntity → Person → Admin
 *   Inherits: id, timestamps, firstName, lastName, email, phone
 *   Adds:     username, password, role, lastLogin
 *
 * - ENCAPSULATION: Password field protected, access controlled
 * - POLYMORPHISM: Role enum with different permission behaviors
 * - ABSTRACTION: Permission checking abstracted into hasPermission()
 *
 * DATABASE TABLE: admins
 * =====================================================
 */
@Entity
@Table(name = "admins")
public class Admin extends Person {

    @NotBlank(message = "Username is required")
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    /**
     * ENCAPSULATION: Password is private.
     * In production, this should be a hashed value (BCrypt, etc.)
     */
    @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * Admin role — determines what actions they can perform.
     * POLYMORPHISM: Different roles have different capabilities.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20)
    private Role role = Role.ADMIN;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // ---- Role Enum (Polymorphism) ----

    /**
     * POLYMORPHISM: Each role has its own canManageSystem() behavior.
     * This is "enum polymorphism" — same interface, different result.
     */
    public enum Role {
        SUPER_ADMIN("Super Admin", "danger", true),
        ADMIN("Admin", "primary", false),
        MODERATOR("Moderator", "info", false);

        private final String displayName;
        private final String badgeClass;
        private final boolean canManageSystem;  // Only SUPER_ADMIN can delete other admins

        Role(String displayName, String badgeClass, boolean canManageSystem) {
            this.displayName = displayName;
            this.badgeClass = badgeClass;
            this.canManageSystem = canManageSystem;
        }

        public String getDisplayName() { return displayName; }
        public String getBadgeClass() { return badgeClass; }
        public boolean canManageSystem() { return canManageSystem; }
    }

    // ---- Constructors ----
    public Admin() {}

    // ---- Getters and Setters ----

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    /**
     * ABSTRACTION: Hide the permission logic.
     * Callers just ask "can this admin do X?" without knowing the internal rules.
     */
    public boolean hasPermission(String action) {
        if (role == Role.SUPER_ADMIN) return true;  // Super admin can do everything
        if (role == Role.ADMIN) {
            // Regular admins can do most things except system management
            return !action.equals("DELETE_ADMIN") && !action.equals("MANAGE_SYSTEM");
        }
        // Moderators can only view
        return action.equals("VIEW");
    }

    /**
     * Update lastLogin to current time.
     * Called when admin successfully logs in.
     */
    public void recordLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Admin{username=" + username + ", role=" + role + "}";
    }
}