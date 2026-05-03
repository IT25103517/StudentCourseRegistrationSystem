package com.university.management.service;

import com.university.management.model.Enrollment;
import java.util.List;

public interface EnrollmentService extends ManagementService<Enrollment> {
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByCourseId(Long courseId);
    boolean isStudentEnrolled(Long studentId, Long courseId);
}