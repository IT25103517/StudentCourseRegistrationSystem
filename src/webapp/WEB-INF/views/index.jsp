
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard — UniManage</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <%@ include file="layout/sidebar.jsp" %>
</head>
<body>
<div class="main-content">
    <div class="content-area">

        <!-- Welcome Banner -->
        <div style="background:linear-gradient(135deg, #4f46e5, #7c3aed); border-radius:20px;
                    padding:32px; color:white; margin-bottom:28px;">
            <h2 style="font-family:'Playfair Display',serif; font-size:26px; margin-bottom:6px;">
                Welcome back, ${sessionScope.adminUsername}! 👋
            </h2>
            <p style="opacity:0.8; margin:0; font-size:14px;">
                Here's what's happening in your university today.
            </p>
        </div>

        <!-- Stats Cards Row -->
        <div class="row g-4 mb-4">
            <div class="col-xl-2 col-md-4 col-6">
                <div class="stat-card">
                    <div class="stat-icon" style="background:#eff6ff;">🏛️</div>
                    <div class="stat-number">${departmentCount}</div>
                    <div class="stat-label">Departments</div>
                </div>
            </div>
            <div class="col-xl-2 col-md-4 col-6">
                <div class="stat-card">
                    <div class="stat-icon" style="background:#f0fdf4;">👨‍🏫</div>
                    <div class="stat-number">${lecturerCount}</div>
                    <div class="stat-label">Lecturers</div>
                </div>
            </div>
            <div class="col-xl-2 col-md-4 col-6">
                <div class="stat-card">
                    <div class="stat-icon" style="background:#fdf4ff;">🎓</div>
                    <div class="stat-number">${studentCount}</div>
                    <div class="stat-label">Students</div>
                </div>
            </div>
            <div class="col-xl-2 col-md-4 col-6">
                <div class="stat-card">
                    <div class="stat-icon" style="background:#fff7ed;">📚</div>
                    <div class="stat-number">${courseCount}</div>
                    <div class="stat-label">Courses</div>
                </div>
            </div>
            <div class="col-xl-2 col-md-4 col-6">
                <div class="stat-card">
                    <div class="stat-icon" style="background:#fef2f2;">📋</div>
                    <div class="stat-number">${enrollmentCount}</div>
                    <div class="stat-label">Enrollments</div>
                </div>
            </div>
        </div>

        <!-- Quick Actions -->
        <div class="card mb-4">
            <div class="card-header">
                <h6 class="mb-0 fw-bold">⚡ Quick Actions</h6>
            </div>
            <div class="card-body">
                <div class="d-flex flex-wrap gap-2">
                    <a href="${pageContext.request.contextPath}/students/add" class="btn btn-primary btn-sm">
                        <i class="fas fa-user-plus me-1"></i> Add Student
                    </a>
                    <a href="${pageContext.request.contextPath}/lecturers/add" class="btn btn-success btn-sm">
                        <i class="fas fa-chalkboard-teacher me-1"></i> Add Lecturer
                    </a>
                    <a href="${pageContext.request.contextPath}/courses/add" class="btn btn-warning btn-sm text-dark">
                        <i class="fas fa-book me-1"></i> Add Course
                    </a>
                    <a href="${pageContext.request.contextPath}/enrollments/add" class="btn btn-info btn-sm text-white">
                        <i class="fas fa-clipboard-list me-1"></i> Enroll Student
                    </a>
                    <a href="${pageContext.request.contextPath}/departments/add" class="btn btn-secondary btn-sm">
                        <i class="fas fa-building me-1"></i> Add Department
                    </a>
                </div>
            </div>
        </div>

        <!-- Recent Students -->
        <div class="row g-4">
            <div class="col-lg-6">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h6 class="mb-0 fw-bold">🎓 Recent Students</h6>
                        <a href="${pageContext.request.contextPath}/students"
                           class="btn btn-sm btn-outline-primary">View All</a>
                    </div>
                    <div class="card-body p-0">
                        <table class="table table-hover mb-0">
                            <thead>
                                <tr>
                                    <th>Student ID</th>
                                    <th>Name</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="student" items="${recentStudents}">
                                    <tr>
                                        <td><code>${student.studentId}</code></td>
                                        <td>${student.displayName}</td>
                                        <td>
                                            <span class="badge bg-${student.status.badgeClass}">
                                                ${student.status.displayName}
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty recentStudents}">
                                    <tr><td colspan="3" class="text-center text-muted py-3">No students yet</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h6 class="mb-0 fw-bold">📚 Recent Courses</h6>
                        <a href="${pageContext.request.contextPath}/courses"
                           class="btn btn-sm btn-outline-primary">View All</a>
                    </div>
                    <div class="card-body p-0">
                        <table class="table table-hover mb-0">
                            <thead>
                                <tr>
                                    <th>Code</th>
                                    <th>Course Name</th>
                                    <th>Credits</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="course" items="${recentCourses}">
                                    <tr>
                                        <td><span class="badge bg-secondary">${course.courseCode}</span></td>
                                        <td>${course.name}</td>
                                        <td><span class="badge bg-primary">${course.credits} cr</span></td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty recentCourses}">
                                    <tr><td colspan="3" class="text-center text-muted py-3">No courses yet</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>document.getElementById('pageTitle').innerText = 'Dashboard';</script>
</body>
</html>

