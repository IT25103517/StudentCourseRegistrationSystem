package com.university.management.service;

import com.university.management.model.Department;
import java.util.List;

/**
 * Department-specific service interface.
 * Extends ManagementService with Department-specific operations.
 */
public interface DepartmentService extends ManagementService<Department> {
    boolean existsByCode(String code);
    List<Department> findByName(String name);
}