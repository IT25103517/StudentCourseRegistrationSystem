package com.university.management.repository;

import com.university.management.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    // Find all lecturers in a specific department
    List<Lecturer> findByDepartmentId(Long departmentId);

    // Search lecturers by name
    @Query("SELECT l FROM Lecturer l WHERE " +
            "LOWER(l.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(l.lastName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(l.employeeId) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Lecturer> searchByName(@Param("name") String name);

    // Check if employee ID is already taken
    boolean existsByEmployeeId(String employeeId);

    // Check if email is already taken
    boolean existsByEmail(String email);

    // Find by employee ID
    java.util.Optional<Lecturer> findByEmployeeId(String employeeId);
}