package com.university.management.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * =====================================================
 * ENROLLMENT ENTITY
 * =====================================================
 * Managed by: De Silva
 *
 * This is a JOIN TABLE entity connecting Students and Courses.
 * A student ENROLLS in a course → creates an Enrollment record.
 *
 * OOP CONCEPTS:
 * - INHERITANCE: Extends BaseEntity
 * - ENCAPSULATION: Private fields with getters/setters
 * - POLYMORPHISM: EnrollmentStatus enum with different grade displays
 * - ABSTRACTION: Status and Grade handling abstracted into enums/methods
 *
 * DATABASE TABLE: enrollments
 * =====================================================
 */
@Entity
@Table(name = "enrollments",
        // Unique constraint: A student can only enroll in the same course ONCE
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"})
)
public class Enrollment extends BaseEntity {

    /**
     * MANY Enrollments belong to ONE Student
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /**
     * MANY Enrollments belong to ONE Course
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate = LocalDate.now();  // Default = today

    /**
     * Enrollment status.
     * POLYMORPHISM: Each status has different display name and color.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    /**
     * Letter grade: A, B, C, D, F (or null if not graded yet)
     */
    @Column(name = "grade", length = 5)
    private String grade;

    /**
     * Score out of 100
     */
    @Column(name = "marks")
    private Double marks;

    // ---- Enum for Status ----

    /**
     * POLYMORPHISM: Each enum constant has its own behavior.
     * Different statuses display differently in the UI.
     */
    public enum EnrollmentStatus {
        ACTIVE("Active", "success"),
        COMPLETED("Completed", "primary"),
        DROPPED("Dropped", "danger"),
        PENDING("Pending", "warning");

        private final String displayName;
        private final String badgeClass;

        EnrollmentStatus(String displayName, String badgeClass) {
            this.displayName = displayName;
            this.badgeClass = badgeClass;
        }

        public String getDisplayName() { return displayName; }
        public String getBadgeClass() { return badgeClass; }
    }

    // ---- Constructors ----
    public Enrollment() {}

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
        this.status = EnrollmentStatus.ACTIVE;
    }

    // ---- Getters and Setters ----

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public Double getMarks() { return marks; }
    public void setMarks(Double marks) { this.marks = marks; }

    /**
     * ABSTRACTION: Calculate grade from marks automatically.
     * This hides the grading logic from outside — caller just uses getCalculatedGrade().
     */
    public String getCalculatedGrade() {
        if (marks == null) return "Not Graded";
        if (marks >= 75) return "A";
        if (marks >= 65) return "B";
        if (marks >= 55) return "C";
        if (marks >= 45) return "D";
        return "F";
    }
}