package com.university.management.repository;

import com.university.management.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseId(Long courseId);

    List<Enrollment> findByStatus(Enrollment.EnrollmentStatus status);

    // Check if a student is already enrolled in a course
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    // Find a specific enrollment by student and course
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    // Count active enrollments for a course
    long countByCourseIdAndStatus(Long courseId, Enrollment.EnrollmentStatus status);

    // Find all enrollments with student and course info loaded (eager)
    @Query("SELECT e FROM Enrollment e JOIN FETCH e.student JOIN FETCH e.course")
    List<Enrollment> findAllWithDetails();
}