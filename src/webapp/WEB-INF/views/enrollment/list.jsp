<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Enrollments — UniManage</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <%@ include file="../layout/sidebar.jsp" %>
</head>
<body>
<div class="main-content">
    <div class="content-area">

        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible mb-4">
                <i class="fas fa-check-circle me-2"></i>${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible mb-4">
                <i class="fas fa-exclamation-triangle me-2"></i>${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h4 class="fw-bold mb-1" style="color:#1e293b">📋 Enrollments</h4>
                <p class="text-muted mb-0" style="font-size:13px">
                    Manage student course enrollments — De Silva
                </p>
            </div>
            <a href="${pageContext.request.contextPath}/enrollments/add"
               class="btn btn-primary">
                <i class="fas fa-plus me-2"></i>Enroll Student
            </a>
        </div>

        <div class="card">
            <div class="card-header">
                <h6 class="mb-0 fw-bold">
                    All Enrollments
                    <span class="badge bg-primary ms-2">${enrollments.size()}</span>
                </h6>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Student</th>
                                <th>Course</th>
                                <th>Enrolled On</th>
                                <th>Status</th>
                                <th>Grade</th>
                                <th>Marks</th>
                                <th>Calculated Grade</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="enroll" items="${enrollments}" varStatus="loop">
                                <tr>
                                    <td class="text-muted">${loop.index + 1}</td>
                                    <td>
                                        <div class="fw-semibold">${enroll.student.displayName}</div>
                                        <div class="text-muted" style="font-size:11px">${enroll.student.studentId}</div>
                                    </td>
                                    <td>
                                        <div class="fw-semibold">${enroll.course.name}</div>
                                        <span class="badge bg-secondary" style="font-size:10px">
                                            ${enroll.course.courseCode}
                                        </span>
                                    </td>
                                    <td class="text-muted" style="font-size:12px">
                                        ${enroll.enrollmentDate}
                                    </td>
                                    <td>
                                        <span class="badge bg-${enroll.status.badgeClass}">
                                            ${enroll.status.displayName}
                                        </span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty enroll.grade}">
                                                <span class="badge bg-info">${enroll.grade}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">—</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${not empty enroll.marks ? enroll.marks : '—'}</td>
                                    <td>
                                        <span class="badge bg-secondary">${enroll.calculatedGrade}</span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/enrollments/edit/${enroll.id}"
                                           class="action-btn btn-edit me-1">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/enrollments/delete/${enroll.id}"
                                           class="action-btn btn-del"
                                           onclick="return confirm('Remove this enrollment?')">
                                            <i class="fas fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty enrollments}">
                                <tr>
                                    <td colspan="9" class="text-center py-5 text-muted">
                                        <div style="font-size:48px;margin-bottom:12px">📋</div>
                                        <div class="fw-semibold">No enrollments yet</div>
                                        <div class="mt-2">
                                            <a href="${pageContext.request.contextPath}/enrollments/add"
                                               class="btn btn-primary btn-sm">
                                                Enroll First Student
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>document.getElementById('pageTitle').innerText = 'Enrollments';</script>
</body>
</html>