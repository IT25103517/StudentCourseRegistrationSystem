package com.university.management.servlet;

import com.university.management.model.Course;
import com.university.management.util.DBHandler;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "list":
                req.setAttribute("courses", DBHandler.getAllCourses());
                req.getRequestDispatcher("/course/courseList.jsp")
                        .forward(req, resp);
                break;

            case "add":
                req.setAttribute("departments", DBHandler.getAllDepartments());
                req.setAttribute("lecturers",   DBHandler.getAllLecturers());
                req.getRequestDispatcher("/course/courseForm.jsp")
                        .forward(req, resp);
                break;

            case "edit":
                req.setAttribute("course",
                        DBHandler.getCourseById(req.getParameter("id")));
                req.setAttribute("departments", DBHandler.getAllDepartments());
                req.setAttribute("lecturers",   DBHandler.getAllLecturers());
                req.getRequestDispatcher("/course/courseForm.jsp")
                        .forward(req, resp);
                break;

            case "delete":
                String did = req.getParameter("id");
                DBHandler.deleteCourse(did);
                DBHandler.writeLog("admin", "Deleted course: " + did);
                resp.sendRedirect(req.getContextPath() + "/course?action=list");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("create".equals(action)) {
            String newId = DBHandler.nextId("courses", "C");
            Course nc = new Course(
                    newId,
                    req.getParameter("code"),
                    req.getParameter("title"),
                    Integer.parseInt(req.getParameter("credits")),
                    req.getParameter("deptId"),
                    req.getParameter("lecId"),
                    Integer.parseInt(req.getParameter("maxStudents"))
            );
            DBHandler.insertCourse(nc);
            DBHandler.writeLog("admin",
                    "Created course: " + newId + " (" + nc.getCode() + ")");
            resp.sendRedirect(req.getContextPath() + "/course?action=list");

        } else if ("update".equals(action)) {
            Course c = DBHandler.getCourseById(req.getParameter("id"));
            if (c != null) {
                c.setTitle(      req.getParameter("title"));
                c.setCredits(    Integer.parseInt(req.getParameter("credits")));
                c.setDeptId(     req.getParameter("deptId"));
                c.setLecId(      req.getParameter("lecId"));
                c.setMaxStudents(Integer.parseInt(req.getParameter("maxStudents")));
                DBHandler.updateCourse(c);
                DBHandler.writeLog("admin", "Updated course: " + c.getId());
            }
            resp.sendRedirect(req.getContextPath() + "/course?action=list");
        }
    }
}