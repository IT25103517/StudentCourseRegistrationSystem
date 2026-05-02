package com.university.management.repository;

import com.university.management.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * =====================================================
 * DEPARTMENT REPOSITORY
 * =====================================================
 * WHAT IS A REPOSITORY?
 * A Repository is an interface that handles all database operations.
 * By extending JpaRepository<Department, Long>, Spring automatically
 * provides these methods WITHOUT us writing any SQL:
 *   - save(department)         → INSERT or UPDATE
 *   - findById(id)             → SELECT WHERE id = ?
 *   - findAll()                → SELECT * FROM departments
 *   - delete(department)       → DELETE WHERE id = ?
 *   - count()                  → SELECT COUNT(*)
 *
 * We only need to DECLARE custom methods — Spring figures out the SQL!
 * =====================================================
 */
@Repository  // Marks this as a Spring-managed repository bean
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    /**
     * Find department by its code (e.g., "CS", "ENG").
     * Spring reads "findByCode" and generates: SELECT * FROM departments WHERE code = ?
     */
    Optional<Department> findByCode(String code);

    /**
     * Find departments whose name contains a search term (case-insensitive).
     * Used for search functionality.
     * Spring generates: SELECT * FROM departments WHERE LOWER(name) LIKE LOWER('%term%')
     */
    List<Department> findByNameContainingIgnoreCase(String name);

    /**
     * Custom JPQL query using @Query.
     * JPQL uses class/field names, NOT table/column names.
     * This searches across both name AND headOfDepartment fields.
     */
    @Query("SELECT d FROM Department d WHERE " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.headOfDepartment) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Department> searchDepartments(@Param("searchTerm") String searchTerm);

    /**
     * Check if a department with a given code already exists.
     * Used to prevent duplicates.
     */
    boolean existsByCode(String code);

    /**
     * Check if a department with a given name already exists.
     */
    boolean existsByName(String name);
}