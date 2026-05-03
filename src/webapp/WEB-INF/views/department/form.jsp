<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? 'Edit' : 'Add'} Department — UniManage</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <%@ include file="../layout/sidebar.jsp" %>
</head>
<body>
<div class="main-content">
    <div class="content-area">

        <div class="d-flex align-items-center mb-4">
            <a href="${pageContext.request.contextPath}/departments"
               class="btn btn-outline-secondary me-3">
                <i class="fas fa-arrow-left"></i>
            </a>
            <div>
                <h4 class="fw-bold mb-0" style="color:#1e293b">
                    ${isEdit ? '✏️ Edit Department' : '➕ Add New Department'}
                </h4>
                <p class="text-muted mb-0" style="font-size:13px">
                    ${isEdit ? 'Update department information' : 'Register a new department'}
                </p>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger mb-4">
                <i class="fas fa-exclamation-triangle me-2"></i>${error}
            </div>
        </c:if>

        <div class="card" style="max-width:640px">
            <div class="card-body p-4">
                <form:form action="${pageContext.request.contextPath}/departments/${isEdit ? 'edit/' += department.id : 'add'}"
                           method="POST" modelAttribute="department">

                    <div class="row g-3">
                        <div class="col-md-8">
                            <label class="form-label">Department Name <span class="text-danger">*</span></label>
                            <form:input path="name" cssClass="form-control"
                                        placeholder="e.g., Computer Science"/>
                            <form:errors path="name" cssClass="text-danger" style="font-size:12px"/>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Department Code <span class="text-danger">*</span></label>
                            <form:input path="code" cssClass="form-control"
                                        placeholder="e.g., CS"/>
                            <form:errors path="code" cssClass="text-danger" style="font-size:12px"/>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Head of Department</label>
                            <form:input path="headOfDepartment" cssClass="form-control"
                                        placeholder="Full name of department head"/>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Description</label>
                            <form:textarea path="description" cssClass="form-control" rows="3"
                                           placeholder="Brief description of the department..."/>
                        </div>
                    </div>

                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>
                            ${isEdit ? 'Update Department' : 'Save Department'}
                        </button>
                        <a href="${pageContext.request.contextPath}/departments"
                           class="btn btn-outline-secondary">Cancel</a>
                    </div>
                </form:form>
            </div>
        </div>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>document.getElementById('pageTitle').innerText = '${isEdit ? "Edit" : "Add"} Department';</script>
</body>
</html>