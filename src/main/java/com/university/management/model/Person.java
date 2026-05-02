package com.university.management.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * =====================================================
 * PERSON — Abstract Class
 * =====================================================
 *
 * OOP CONCEPTS DEMONSTRATED:
 * -------------------------------------------------
 * INHERITANCE CHAIN:
 *   BaseEntity  ←  Person  ←  Student
 *   BaseEntity  ←  Person  ←  Lecturer
 *   BaseEntity  ←  Person  ←  Admin
 *
 * This class groups all COMMON HUMAN attributes that
 * Lecturer, Student, and Admin all share.
 * Without this, we'd repeat firstName, lastName, email, phone
 * in 3 separate classes — violating the DRY principle.
 *
 * ABSTRACTION:
 * - 'abstract' means you cannot create a Person directly.
 * - It's a concept/template, not a concrete thing.
 * =====================================================
 */
@MappedSuperclass  // JPA: Share these fields with all child entity classes
public abstract class Person extends BaseEntity {

    // ---- Common fields for all people ----

    /**
     * @NotBlank = form validation: field cannot be empty
     * @Column = maps to a column in the MySQL table
     */
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    /**
     * @Email = validates that input looks like an email address
     * unique = true means no two people can have the same email
     */
    @Email(message = "Please enter a valid email address")
    @Column(name = "email", unique = true, length = 150)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    // ---- Getters and Setters (Encapsulation) ----

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Helper method — returns full name by combining first and last name.
     * POLYMORPHISM OPPORTUNITY: Subclasses can override this if needed.
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}