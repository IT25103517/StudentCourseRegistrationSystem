<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Departments — UniManage</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <%@ include file="../layout/sidebar.jsp" %>
</head>
<body>
<div class="main-content">
    <div class="content-area">

        <!-- Flash Messages -->
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

        <!-- Page Header -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h4 class="fw-bold mb-1" style="color:#1e293b">🏛️ Departments</h4>
                <p class="text-muted mb-0" style="font-size:13px">
                    Manage university departments — Chathuranga
                </p>
            </div>
            <a href="${pageContext.request.contextPath}/departments/add"
               class="btn btn-primary">
                <i class="fas fa-plus me-2"></i>Add Department
            </a>
        </div>

        <!-- Search Bar -->
        <div class="card mb-4">
            <div class="card-body py-3">
                <form action="${pageContext.request.contextPath}/departments" method="GET">
                    <div class="row g-2 align-items-center">
                        <div class="col-md-8">
                            <div class="search-box">
                                <i class="fas fa-search"></i>
                                <input type="text" name="search" class="form-control"
                                       placeholder="Search departments by name or head..."
                                       value="${searchTerm}">
                            </div>
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary w-100">Search</button>
                        </div>
                        <div class="col-md-2">
                            <a href="${pageContext.request.contextPath}/departments"
                               class="btn btn-outline-secondary w-100">Clear</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Departments Table -->
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h6 class="mb-0 fw-bold">
                    All Departments
                    <span class="badge bg-primary ms-2">${departments.size()}</span>
                </h6>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Code</th>
                                <th>Department Name</th>
                                <th>Head of Department</th>
                                <th>Description</th>
                                <th>Created</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dept" items="${departments}" varStatus="loop">
                                <tr>
                                    <td class="text-muted">${loop.index + 1}</td>
                                    <td>
                                        <span class="badge bg-primary bg-opacity-10 text-primary fw-bold"
                                              style="font-size:12px">${dept.code}</span>
                                    </td>
                                    <td class="fw-semibold">${dept.name}</td>
                                    <td>${not empty dept.headOfDepartment ? dept.headOfDepartment : '<span class="text-muted">—</span>'}</td>
                                    <td style="max-width:200px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap">
                                        ${not empty dept.description ? dept.description : '—'}
                                    </td>
                                    <td class="text-muted" style="font-size:12px">
                                        ${dept.createdAt != null ?
                                            dept.createdAt.toString().substring(0,10) : '—'}
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/departments/edit/${dept.id}"
                                           class="action-btn btn-edit me-1" title="Edit">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/departments/delete/${dept.id}"
                                           class="action-btn btn-del"
                                           title="Delete"
                                           onclick="return confirm('Delete this department? This cannot be undone.')">
                                            <i class="fas fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty departments}">
                                <tr>
                                    <td colspan="7" class="text-center py-5 text-muted">
                                        <div style="font-size:48px; margin-bottom:12px">🏛️</div>
                                        <div class="fw-semibold">No departments found</div>
                                        <div class="mt-2">
                                            <a href="${pageContext.request.contextPath}/departments/add"
                                               class="btn btn-primary btn-sm">Add First Department</a>
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
<script>document.getElementById('pageTitle').innerText = 'Departments';</script>
</body>
</html>