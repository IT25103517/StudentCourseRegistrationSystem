<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.university.management.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Courses</title>
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

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>&#128218; Course Management</h3>
        <a href="course?action=add" class="btn btn-info text-white">+ Add Course</a>
    </div>

    <input type="text" id="filterBox" class="form-control mb-3"
           placeholder="Filter by title, code, or department...">

    <table class="table table-bordered table-hover bg-white" id="courseTable">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Code</th>
            <th>Title</th>
            <th>Credits</th>
            <th>Dept</th>
            <th>Lecturer</th>
            <th>Max Students</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Course> courses = (List<Course>) request.getAttribute("courses");
            if (courses != null) {
                for (Course c : courses) {
        %>
        <tr>
            <td><%= c.getId() %></td>
            <td><strong><%= c.getCode() %></strong></td>
            <td><%= c.getTitle() %></td>
            <td><span class="badge bg-primary"><%= c.getCredits() %> cr</span></td>
            <td><%= c.getDeptId() %></td>
            <td><%= c.getLecId() %></td>
            <td><%= c.getMaxStudents() %></td>
            <td>
                <a href="course?action=edit&id=<%= c.getId() %>"
                   class="btn btn-warning btn-sm">Edit</a>
                <a href="course?action=delete&id=<%= c.getId() %>"
                   class="btn btn-danger btn-sm"
                   onclick="return confirm('Delete this course?')">Delete</a>
            </td>
        </tr>
        <%  }
        } %>
        </tbody>
    </table>
</div>

<script>
    document.getElementById('filterBox').addEventListener('keyup', function () {
        const filter = this.value.toLowerCase();
        document.querySelectorAll('#courseTable tbody tr').forEach(row => {
            row.style.display =
                row.textContent.toLowerCase().includes(filter) ? '' : 'none';
        });
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>