<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Enrollment Form — UniManage</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <%@ include file="../layout/sidebar.jsp" %>
</head>
<body>
<div class="main-content">
    <div class="content-area">

        <c:if test="${not empty error}">
            <div class="alert alert-danger mb-4">
                <i class="fas fa-exclamation-triangle me-2"></i>${error}
            </div>
        </c:if>

        <div class="d-flex align-items-center mb-4">
            <a href="${pageContext.request.contextPath}/enrollments"
               class="btn btn-outline-secondary me-3">
                <i class="fas fa-arrow-left"></i>
            </a>
            <h4 class="fw-bold mb-0" style="color:#1e293b">
                ${isEdit ? '✏️ Update Enrollment' : '📋 Enroll Student in Course'}
            </h4>
        </div>

        <div class="card" style="max-width:600px">
            <div class="card-body p-4">
                <c:choose>
                    <c:when test="${isEdit}">
                        <%-- Edit: Only update status and grade --%>
                        <form action="${pageContext.request.contextPath}/enrollments/edit/${enrollment.id}"
                              method="POST">
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Student</label>
                                <input type="text" class="form-control" disabled
                                       value="${enrollment.student.displayName} (${enrollment.student.studentId})">
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Course</label>
                                <input type="text" class="form-control" disabled
                                       value="${enrollment.course.name} (${enrollment.course.courseCode})">
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Status</label>
                                <select name="status" class="form-select">
                                    <c:forEach var="s" items="${statuses}">
                                        <option value="${s}" ${enrollment.status == s ? 'selected' : ''}>
                                            ${s.displayName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label fw-semibold">Grade</label>
                                    <select name="grade" class="form-select">
                                        <option value="">Not Graded</option>
                                        <option value="A+" ${enrollment.grade == 'A+' ? 'selected' : ''}>A+</option>
                                        <option value="A" ${enrollment.grade == 'A' ? 'selected' : ''}>A</option>
                                        <option value="B" ${enrollment.grade == 'B' ? 'selected' : ''}>B</option>
                                        <option value="C" ${enrollment.grade == 'C' ? 'selected' : ''}>C</option>
                                        <option value="D" ${enrollment.grade == 'D' ? 'selected' : ''}>D</option>
                                        <option value="F" ${enrollment.grade == 'F' ? 'selected' : ''}>F</option>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-semibold">Marks (0-100)</label>
                                    <input type="number" name="marks" class="form-control"
                                           min="0" max="100" step="0.5"
                                           value="${enrollment.marks}">
                                </div>
                            </div>
                            <div class="d-flex gap-2 mt-4">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save me-2"></i>Update Enrollment
                                </button>
                                <a href="${pageContext.request.contextPath}/enrollments"
                                   class="btn btn-outline-secondary">Cancel</a>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <%-- Add: Select student and course --%>
                        <form action="${pageContext.request.contextPath}/enrollments/add" method="POST">
                            <div class="mb-3">
                                <label class="form-label fw-semibold">
                                    Select Student <span class="text-danger">*</span>
                                </label>
                                <select name="studentId" class="form-select" required>
                                    <option value="">-- Select a student --</option>
                                    <c:forEach var="student" items="${students}">
                                        <option value="${student.id}">
                                            ${student.displayName} (${student.studentId})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-semibold">
                                    Select Course <span class="text-danger">*</span>
                                </label>
                                <select name="courseId" class="form-select" required>
                                    <option value="">-- Select a course --</option>
                                    <c:forEach var="course" items="${courses}">
                                        <option value="${course.id}">
                                            ${course.name} (${course.courseCode}) — ${course.credits} credits
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="d-flex gap-2 mt-4">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-clipboard-check me-2"></i>Enroll Student
                                </button>
                                <a href="${pageContext.request.contextPath}/enrollments"
                                   class="btn btn-outline-secondary">Cancel</a>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>