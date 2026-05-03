package com.university.management.service;

import com.university.management.model.Lecturer;
import java.util.List;

public interface LecturerService extends ManagementService<Lecturer> {
    List<Lecturer> findByDepartmentId(Long departmentId);
    boolean existsByEmployeeId(String employeeId);
}