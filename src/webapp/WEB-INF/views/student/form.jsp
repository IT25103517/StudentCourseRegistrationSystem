<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? 'Edit' : 'Register'} Student — UniManage</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <%@ include file="../layout/sidebar.jsp" %>
</head>
<body>
<div class="main-content">
    <div class="content-area">
        <div class="d-flex align-items-center mb-4">
            <a href="${pageContext.request.contextPath}/students"
               class="btn btn-outline-secondary me-3">
                <i class="fas fa-arrow-left"></i>
            </a>
            <div>
                <h4 class="fw-bold mb-0" style="color:#1e293b">
                    ${isEdit ? '✏️ Edit Student' : '🎓 Register New Student'}
                </h4>
                <p class="text-muted mb-0" style="font-size:13px">Fill in all required fields</p>
            </div>
        </div>

        <div class="card" style="max-width:720px">
            <div class="card-body p-4">
                <form:form action="${pageContext.request.contextPath}/students/${isEdit ? 'edit/' += student.id : 'add'}"
                           method="POST" modelAttribute="student">

                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">First Name <span class="text-danger">*</span></label>
                            <form:input path="firstName" cssClass="form-control" placeholder="First name"/>
                            <form:errors path="firstName" cssClass="text-danger" style="font-size:12px"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Last Name <span class="text-danger">*</span></label>
                            <form:input path="lastName" cssClass="form-control" placeholder="Last name"/>
                            <form:errors path="lastName" cssClass="text-danger" style="font-size:12px"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Student ID</label>
                            <form:input path="studentId" cssClass="form-control"
                                        placeholder="Auto-generated if left empty"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Email <span class="text-danger">*</span></label>
                            <form:input path="email" type="email" cssClass="form-control"
                                        placeholder="student@university.edu"/>
                            <form:errors path="email" cssClass="text-danger" style="font-size:12px"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Phone</label>
                            <form:input path="phone" cssClass="form-control" placeholder="+94 71 xxx xxxx"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Date of Birth</label>
                            <form:input path="dateOfBirth" type="date" cssClass="form-control"/>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Address</label>
                            <form:textarea path="address" cssClass="form-control" rows="2"
                                           placeholder="Residential address"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Password</label>
                            <form:input path="password" type="password" cssClass="form-control"
                                        placeholder="Student login password"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Status</label>
                            <form:select path="status" cssClass="form-select">
                                <c:forEach var="s" items="${statuses}">
                                    <form:option value="${s}" label="${s.displayName}"/>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>

                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>
                            ${isEdit ? 'Update Student' : 'Register Student'}
                        </button>
                        <a href="${pageContext.request.contextPath}/students"
                           class="btn btn-outline-secondary">Cancel</a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>