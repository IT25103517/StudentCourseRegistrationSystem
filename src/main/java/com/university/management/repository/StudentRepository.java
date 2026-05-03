package com.university.management.repository;

import com.university.management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentId(String studentId);

    Optional<Student> findByEmail(String email);

    List<Student> findByStatus(Student.Status status);

    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(s.studentId) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Student> searchStudents(@Param("term") String term);

    boolean existsByStudentId(String studentId);

    boolean existsByEmail(String email);

    // Login: find student by email and password
    Optional<Student> findByEmailAndPassword(String email, String password);
}