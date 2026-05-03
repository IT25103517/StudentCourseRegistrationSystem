package com.university.management.service;

import com.university.management.model.Course;
import java.util.List;

public interface CourseService extends ManagementService<Course> {
    List<Course> findByDepartmentId(Long departmentId);
    boolean existsByCourseCode(String courseCode);
}