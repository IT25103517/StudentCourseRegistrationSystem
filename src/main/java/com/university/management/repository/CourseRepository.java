package com.university.management.repository;

import com.university.management.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByDepartmentId(Long departmentId);

    List<Course> findByLecturerId(Long lecturerId);

    @Query("SELECT c FROM Course c WHERE " +
            "LOWER(c.courseCode) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Course> searchCourses(@Param("term") String term);

    boolean existsByCourseCode(String courseCode);

    java.util.Optional<Course> findByCourseCode(String courseCode);
}