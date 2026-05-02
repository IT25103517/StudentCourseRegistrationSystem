package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

/**
 * =====================================================
 * COURSE ENTITY
 * =====================================================
 * Managed by: Ashen
 *
 * OOP CONCEPTS:
 * - INHERITANCE: Extends BaseEntity (gets id, timestamps)
 * - ENCAPSULATION: Private fields with public getters/setters
 * - RELATIONSHIPS: ManyToOne with Department and Lecturer
 *
 * DATABASE TABLE: courses
 * =====================================================
 */
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    /**
     * Unique course code like "CS101", "ENG201"
     */
    @NotBlank(message = "Course code is required")
    @Column(name = "course_code", unique = true, nullable = false, length = 20)
    private String courseCode;

    @NotBlank(message = "Course name is required")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Credit hours — must be positive (1, 2, 3, 4 credits)
     */
    @Positive(message = "Credits must be a positive number")
    @Column(name = "credits", nullable = false)
    private Integer credits;

    /**
     * Academic semester: "Semester 1", "Semester 2", "Year 1 Semester 1"
     */
    @Column(name = "semester", length = 50)
    private String semester;

    /**
     * Max number of students that can enroll in this course
     */
    @Column(name = "max_students")
    private Integer maxStudents = 50;

    /**
     * MANY Courses belong to ONE Department
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    /**
     * MANY Courses can be taught by ONE Lecturer (assigned lecturer)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    /**
     * ONE Course has MANY Enrollments
     */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    // ---- Constructors ----
    public Course() {}

    // ---- Getters and Setters ----

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public Integer getMaxStudents() { return maxStudents; }
    public void setMaxStudents(Integer maxStudents) { this.maxStudents = maxStudents; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public Lecturer getLecturer() { return lecturer; }
    public void setLecturer(Lecturer lecturer) { this.lecturer = lecturer; }

    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }

    /**
     * Returns how many students are currently enrolled.
     */
    public int getCurrentEnrollmentCount() {
        return enrollments != null ? (int) enrollments.stream()
                .filter(e -> e.getStatus() == Enrollment.EnrollmentStatus.ACTIVE)
                .count() : 0;
    }

    /**
     * Returns whether there are seats available.
     */
    public boolean hasAvailableSeats() {
        return getCurrentEnrollmentCount() < maxStudents;
    }
}