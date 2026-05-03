<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.university.management.model.Course" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="/index.jsp">&#128218; SCRS</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navLinks">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navLinks">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/student?action=list">Students</a></li>
                <li class="nav-item">
                    <a class="nav-link active" href="/course?action=list">Courses</a></li>
                <li class="nav-item">
                    <a class="nav-link" href="/enrollment?action=list">Enrollments</a></li>
                <li class="nav-item">
                    <a class="nav-link" href="/department?action=list">Departments</a></li>
                <li class="nav-item">
                    <a class="nav-link" href="/lecturer?action=list">Lecturers</a></li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin?action=dashboard">Admin</a></li>
            </ul>
        </div>
    </div>
</nav>

<%
    Course c       = (Course) request.getAttribute("course");
    boolean isEdit = (c != null);

    String formAction  = isEdit ? "update" : "create";
    String titleValue  = isEdit ? c.getTitle() : "";
    int creditsValue   = isEdit ? c.getCredits() : 3;
    int maxStuValue    = isEdit ? c.getMaxStudents() : 40;
    String deptValue   = isEdit ? c.getDeptId() : "";
    String lecValue    = isEdit ? c.getLecId() : "";
%>

<div class="container mt-4" style="max-width: 640px">
    <div class="card shadow">
        <div class="card-header bg-info text-white">
            <h4><%= isEdit ? "Edit Course" : "Add New Course" %></h4>
        </div>
        <div class="card-body">
            <form action="course" method="post">

                <input type="hidden" name="action" value="<%= formAction %>">

                <% if (isEdit) { %>
                <input type="hidden" name="id" value="<%= c.getId() %>">
                <% } else { %>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Course Code</label>
                    <input type="text" class="form-control" name="code"
                           placeholder="e.g. CS301" required>
                    <div class="form-text">Must be unique — e.g. CS101, BM201</div>
                </div>
                <% } %>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Course Title</label>
                    <input type="text" class="form-control" name="title"
                           required value="<%= titleValue %>">
                </div>

                <div class="row">
                    <div class="col mb-3">
                        <label class="form-label fw-semibold">Credits</label>
                        <input type="number" class="form-control" name="credits"
                               min="1" max="6" value="<%= creditsValue %>">
                    </div>
                    <div class="col mb-3">
                        <label class="form-label fw-semibold">Max Students</label>
                        <input type="number" class="form-control" name="maxStudents"
                               min="1" value="<%= maxStuValue %>">
                    </div>
                </div>

                <%-- Department ID typed manually until teammate creates Department class --%>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Department ID</label>
                    <input type="text" class="form-control" name="deptId"
                           placeholder="e.g. D001" required
                           value="<%= deptValue %>">
                    <div class="form-text">
                        Enter the Department ID (e.g. D001, D002)
                    </div>
                </div>

                <%-- Lecturer ID typed manually until teammate creates Lecturer class --%>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Lecturer ID</label>
                    <input type="text" class="form-control" name="lecId"
                           placeholder="e.g. L001" required
                           value="<%= lecValue %>">
                    <div class="form-text">
                        Enter the Lecturer ID (e.g. L001, L002)
                    </div>
                </div>

                <div class="d-flex gap-2 mt-3">
                    <button type="submit" class="btn btn-info text-white flex-fill">
                        <%= isEdit ? "Save Changes" : "Add Course" %>
                    </button>
                    <a href="course?action=list"
                       class="btn btn-secondary flex-fill">Cancel</a>
                </div>

            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>