package com.university.management.service;

import com.university.management.model.Department;
import com.university.management.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * =====================================================
 * DEPARTMENT SERVICE IMPLEMENTATION
 * =====================================================
 *
 * OOP CONCEPTS:
 * - POLYMORPHISM: Implements DepartmentService (which extends ManagementService<Department>).
 *   This class provides the CONCRETE behavior for Department CRUD operations.
 *   When someone calls service.save(), THIS code runs.
 *
 * - ENCAPSULATION: The repository is private. Business logic is hidden from controllers.
 *   Controllers don't know HOW data is saved — they just call service.save().
 *
 * @Service = Spring manages this class (creates one instance for the whole app)
 * @Transactional = wraps each method in a database transaction
 * =====================================================
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    /**
     * The repository handles actual database operations.
     * @Autowired = Spring automatically creates and injects the DepartmentRepository.
     */
    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * CREATE or UPDATE a department.
     * If department has no ID → INSERT new record.
     * If department has an ID → UPDATE existing record.
     */
    @Override
    public Department save(Department department) {
        // Validation: Convert code to uppercase before saving
        if (department.getCode() != null) {
            department.setCode(department.getCode().toUpperCase().trim());
        }
        return departmentRepository.save(department);
    }

    /**
     * READ — Find one department by ID.
     */
    @Override
    @Transactional(readOnly = true)  // readOnly=true optimizes database performance for read operations
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    /**
     * READ — Get all departments.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    /**
     * DELETE — Remove a department by ID.
     */
    @Override
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    /**
     * READ — Search departments by name or head.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Department> search(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll();
        }
        return departmentRepository.searchDepartments(searchTerm.trim());
    }

    /**
     * Count total departments (for dashboard).
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return departmentRepository.count();
    }

    @Override
    public boolean existsByCode(String code) {
        return departmentRepository.existsByCode(code.toUpperCase());
    }

    @Override
    public List<Department> findByName(String name) {
        return departmentRepository.findByNameContainingIgnoreCase(name);
    }
}