<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- This file is INCLUDED into all other pages using <%@ include ... %> --%>

<style>
    /* ===== GLOBAL LAYOUT STYLES ===== */
    :root {
        --primary: #4f46e5;
        --primary-dark: #4338ca;
        --sidebar-width: 260px;
        --header-height: 64px;
        --bg: #f8fafc;
        --sidebar-bg: #1e1b4b;
        --text-sidebar: rgba(255,255,255,0.75);
    }
    body { background: var(--bg); font-family: 'Inter', sans-serif; margin: 0; }

    /* Sidebar */
    .sidebar {
        position: fixed; top: 0; left: 0;
        width: var(--sidebar-width); height: 100vh;
        background: var(--sidebar-bg);
        display: flex; flex-direction: column;
        z-index: 1000; overflow-y: auto;
        box-shadow: 4px 0 24px rgba(0,0,0,0.2);
    }
    .sidebar-logo {
        padding: 24px 20px;
        border-bottom: 1px solid rgba(255,255,255,0.08);
        display: flex; align-items: center; gap: 12px;
    }
    .sidebar-logo .icon {
        width: 40px; height: 40px;
        background: var(--primary);
        border-radius: 10px;
        display: flex; align-items: center; justify-content: center;
        font-size: 20px;
    }
    .sidebar-logo .brand {
        font-family: 'Playfair Display', serif;
        color: white; font-size: 18px; font-weight: 700;
    }
    .sidebar-logo .brand-sub {
        color: rgba(255,255,255,0.4); font-size: 11px; display: block;
    }
    .nav-section {
        padding: 16px 12px 8px;
        font-size: 10px; font-weight: 700; letter-spacing: 1.5px;
        color: rgba(255,255,255,0.3); text-transform: uppercase;
    }
    .sidebar a {
        display: flex; align-items: center; gap: 12px;
        padding: 11px 16px; margin: 2px 8px;
        border-radius: 10px; text-decoration: none;
        color: var(--text-sidebar); font-size: 14px; font-weight: 500;
        transition: all 0.2s;
    }
    .sidebar a:hover, .sidebar a.active {
        background: rgba(79,70,229,0.25);
        color: white;
    }
    .sidebar a.active { background: var(--primary); color: white; }
    .sidebar a .icon { width: 20px; text-align: center; font-size: 15px; }

    /* Top Header */
    .main-header {
        position: fixed; top: 0;
        left: var(--sidebar-width); right: 0;
        height: var(--header-height);
        background: white;
        border-bottom: 1px solid #e2e8f0;
        display: flex; align-items: center;
        justify-content: space-between;
        padding: 0 24px; z-index: 999;
        box-shadow: 0 1px 8px rgba(0,0,0,0.05);
    }
    .page-title {
        font-size: 18px; font-weight: 700; color: #1e293b;
    }
    .admin-badge {
        display: flex; align-items: center; gap: 10px;
    }
    .admin-avatar {
        width: 36px; height: 36px; background: var(--primary);
        border-radius: 50%; display: flex; align-items: center;
        justify-content: center; color: white; font-weight: 700;
        font-size: 14px;
    }

    /* Main Content */
    .main-content {
        margin-left: var(--sidebar-width);
        padding-top: var(--header-height);
        min-height: 100vh;
    }
    .content-area { padding: 28px 32px; }

    /* Cards */
    .card {
        border: none; border-radius: 16px;
        box-shadow: 0 2px 12px rgba(0,0,0,0.06);
    }
    .card-header {
        background: white; border-bottom: 1px solid #f1f5f9;
        border-radius: 16px 16px 0 0 !important;
        padding: 18px 24px;
    }
    .stat-card {
        background: white; border-radius: 16px;
        padding: 24px; box-shadow: 0 2px 12px rgba(0,0,0,0.06);
        transition: transform 0.2s;
    }
    .stat-card:hover { transform: translateY(-3px); }
    .stat-icon {
        width: 52px; height: 52px; border-radius: 14px;
        display: flex; align-items: center; justify-content: center;
        font-size: 24px; margin-bottom: 16px;
    }
    .stat-number { font-size: 32px; font-weight: 800; color: #1e293b; }
    .stat-label { color: #94a3b8; font-size: 13px; font-weight: 500; }

    /* Tables */
    .table th {
        font-weight: 600; font-size: 12px; text-transform: uppercase;
        letter-spacing: 0.5px; color: #64748b; background: #f8fafc;
        border-bottom: 2px solid #e2e8f0;
    }
    .table td { vertical-align: middle; font-size: 14px; }
    .table tbody tr:hover { background: #f8fafc; }

    /* Buttons */
    .btn-primary { background: var(--primary); border-color: var(--primary); }
    .btn-primary:hover { background: var(--primary-dark); }
    .action-btn {
        width: 32px; height: 32px; border-radius: 8px;
        border: none; display: inline-flex;
        align-items: center; justify-content: center;
        font-size: 13px; text-decoration: none; transition: all 0.2s;
    }
    .btn-edit { background: #eff6ff; color: #3b82f6; }
    .btn-edit:hover { background: #3b82f6; color: white; }
    .btn-del { background: #fef2f2; color: #ef4444; }
    .btn-del:hover { background: #ef4444; color: white; }

    /* Alert */
    .alert { border: none; border-radius: 12px; font-size: 14px; }
    .alert-success { background: #f0fdf4; color: #166534; border-left: 4px solid #22c55e; }
    .alert-danger { background: #fef2f2; color: #991b1b; border-left: 4px solid #ef4444; }

    /* Forms */
    .form-control, .form-select {
        border-radius: 10px; border: 1.5px solid #e2e8f0;
        padding: 10px 14px; font-size: 14px; transition: all 0.2s;
    }
    .form-control:focus, .form-select:focus {
        border-color: var(--primary);
        box-shadow: 0 0 0 3px rgba(79,70,229,0.1);
    }
    .form-label { font-weight: 500; font-size: 13px; color: #374151; }
    .search-box { position: relative; }
    .search-box input { padding-left: 38px; }
    .search-box i {
        position: absolute; left: 12px; top: 50%;
        transform: translateY(-50%); color: #94a3b8;
    }
</style>

<!-- SIDEBAR HTML -->
<nav class="sidebar">
    <div class="sidebar-logo">
        <div class="icon">🎓</div>
        <div>
            <div class="brand">UniManage</div>
            <span class="brand-sub">Management System</span>
        </div>
    </div>

    <div class="nav-section">Main Menu</div>
    <a href="${pageContext.request.contextPath}/"
       class="${pageContext.request.servletPath == '/WEB-INF/views/index.jsp' ? 'active' : ''}">
        <span class="icon">🏠</span> Dashboard
    </a>

    <div class="nav-section">Modules</div>
    <a href="${pageContext.request.contextPath}/departments">
        <span class="icon">🏛️</span> Departments
    </a>
    <a href="${pageContext.request.contextPath}/lecturers">
        <span class="icon">👨‍🏫</span> Lecturers
    </a>
    <a href="${pageContext.request.contextPath}/students">
        <span class="icon">🎓</span> Students
    </a>
    <a href="${pageContext.request.contextPath}/courses">
        <span class="icon">📚</span> Courses
    </a>
    <a href="${pageContext.request.contextPath}/enrollments">
        <span class="icon">📋</span> Enrollments
    </a>

    <div class="nav-section">Administration</div>
    <a href="${pageContext.request.contextPath}/admins">
        <span class="icon">🔐</span> Admin Accounts
    </a>

    <div style="margin-top:auto; padding: 16px;">
        <a href="${pageContext.request.contextPath}/logout"
           style="color:rgba(255,100,100,0.7)">
            <span class="icon">🚪</span> Logout
        </a>
    </div>
</nav>

<!-- TOP HEADER -->
<header class="main-header">
    <div class="page-title" id="pageTitle">Dashboard</div>
    <div class="admin-badge">
        <div>
            <div style="font-size:13px; font-weight:600; color:#1e293b">${sessionScope.adminUsername}</div>
            <div style="font-size:11px; color:#94a3b8">Administrator</div>
        </div>
        <div class="admin-avatar">
            ${not empty sessionScope.adminUsername ? sessionScope.adminUsername.substring(0,1).toUpperCase() : 'A'}
        </div>
    </div>
</header>