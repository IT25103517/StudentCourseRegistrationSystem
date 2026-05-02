package com.university.management.service;

import java.util.List;
import java.util.Optional;

/**
 * =====================================================
 * MANAGEMENT SERVICE INTERFACE — Generic CRUD
 * =====================================================
 *
 * OOP CONCEPTS:
 * -------------------------------------------------
 * 1. ABSTRACTION:
 *    - This interface defines WHAT operations exist.
 *    - It does NOT say HOW they are implemented.
 *    - This is pure abstraction — a contract.
 *
 * 2. POLYMORPHISM:
 *    - 6 different service classes implement this interface.
 *    - They all have the same method names (save, findById, etc.)
 *    - But each implements them differently for their own entity.
 *    - This is "interface-based polymorphism" (runtime polymorphism).
 *
 * 3. GENERICS (<T>):
 *    - T is a "type parameter" — like a placeholder.
 *    - DepartmentService<Department> means T = Department.
 *    - This makes the interface reusable for ANY entity type.
 * =====================================================
 *
 * @param <T> The entity type (Department, Lecturer, Student, etc.)
 */
public interface ManagementService<T> {

    /**
     * Save (Create or Update) an entity.
     * Called for both CREATE and UPDATE operations.
     */
    T save(T entity);

    /**
     * Find one entity by its database ID.
     * Returns Optional<T> because the entity might not exist.
     */
    Optional<T> findById(Long id);

    /**
     * Get ALL entities of this type from the database.
     */
    List<T> findAll();

    /**
     * Delete an entity by its ID.
     */
    void deleteById(Long id);

    /**
     * Search entities by a keyword/term.
     * Each implementation searches different fields.
     */
    List<T> search(String searchTerm);

    /**
     * Count total number of entities.
     * Used for dashboard statistics.
     */
    long count();
}