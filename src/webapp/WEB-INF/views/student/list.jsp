<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Students — UniManage</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
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
                <h4 class="fw-bold mb-1" style="color:#1e293b">🎓 Students</h4>
                <p class="text-muted mb-0" style="font-size:13px">
                    Manage student records — Navodya
                </p>
            </div>
            <a href="${pageContext.request.contextPath}/students/add"
               class="btn btn-primary">
                <i class="fas fa-user-plus me-2"></i>Register Student
            </a>
        </div>

        <div class="card mb-4">
            <div class="card-body py-3">
                <form action="${pageContext.request.contextPath}/students" method="GET">
                    <div class="row g-2">
                        <div class="col-md-8">
                            <div class="search-box">
                                <i class="fas fa-search"></i>
                                <input type="text" name="search" class="form-control"
                                       placeholder="Search by name, student ID, or email..."
                                       value="${searchTerm}">
                            </div>
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary w-100">Search</button>
                        </div>
                        <div class="col-md-2">
                            <a href="${pageContext.request.contextPath}/students"
                               class="btn btn-outline-secondary w-100">Clear</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h6 class="mb-0 fw-bold">
                    All Students
                    <span class="badge bg-primary ms-2">${students.size()}</span>
                </h6>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Student ID</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Status</th>
                                <th>Registered</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="student" items="${students}" varStatus="loop">
                                <tr>
                                    <td class="text-muted">${loop.index + 1}</td>
                                    <td><code class="text-primary">${student.studentId}</code></td>
                                    <td>
                                        <div class="d-flex align-items-center gap-2">
                                            <div style="width:34px;height:34px;border-radius:50%;
                                                        background:linear-gradient(135deg,#667eea,#764ba2);
                                                        display:flex;align-items:center;justify-content:center;
                                                        color:white;font-weight:700;font-size:13px">
                                                ${student.firstName.substring(0,1)}${student.lastName.substring(0,1)}
                                            </div>
                                            <div>
                                                <div class="fw-semibold">${student.displayName}</div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>${student.email}</td>
                                    <td>${not empty student.phone ? student.phone : '—'}</td>
                                    <td>
                                        <span class="badge bg-${student.status.badgeClass}">
                                            ${student.status.displayName}
                                        </span>
                                    </td>
                                    <td class="text-muted" style="font-size:12px">
                                        ${student.createdAt != null ?
                                            student.createdAt.toString().substring(0,10) : '—'}
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/students/edit/${student.id}"
                                           class="action-btn btn-edit me-1">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/students/delete/${student.id}"
                                           class="action-btn btn-del"
                                           onclick="return confirm('Delete student ${student.displayName}?')">
                                            <i class="fas fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty students}">
                                <tr>
                                    <td colspan="8" class="text-center py-5 text-muted">
                                        <div style="font-size:48px;margin-bottom:12px">🎓</div>
                                        <div class="fw-semibold">No students registered yet</div>
                                        <div class="mt-2">
                                            <a href="${pageContext.request.contextPath}/students/add"
                                               class="btn btn-primary btn-sm">Register First Student</a>
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
<script>document.getElementById('pageTitle').innerText = 'Students';</script>
</body>
</html>