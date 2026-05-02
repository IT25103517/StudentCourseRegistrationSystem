package com.university.management.service;

import com.university.management.model.Course;
import com.university.management.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course save(Course course) {
        if (course.getCourseCode() != null) {
            course.setCourseCode(course.getCourseCode().toUpperCase().trim());
        }
        return courseRepository.save(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> search(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll();
        }
        return courseRepository.searchCourses(searchTerm.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return courseRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findByDepartmentId(Long departmentId) {
        return courseRepository.findByDepartmentId(departmentId);
    }

    @Override
    public boolean existsByCourseCode(String courseCode) {
        return courseRepository.existsByCourseCode(courseCode.toUpperCase());
    }
}