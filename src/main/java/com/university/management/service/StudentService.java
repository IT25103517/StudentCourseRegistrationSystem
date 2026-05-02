package com.university.management.service;

import com.university.management.model.Student;
import java.util.Optional;

public interface StudentService extends ManagementService<Student> {
    Optional<Student> findByStudentId(String studentId);
    boolean existsByStudentId(String studentId);
    Optional<Student> authenticate(String email, String password);
}