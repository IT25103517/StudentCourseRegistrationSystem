package com.university.management.util;

import com.university.management.model.*;
import java.sql.*;
import java.util.*;

public class DBHandler {

    // ════════════════════════════════════════════════════════
    // COURSE CRUD
    // ════════════════════════════════════════════════════════

    public static List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY code";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapCourse(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public static Course getCourseById(String id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT * FROM courses WHERE id=?")) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapCourse(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static boolean insertCourse(Course c) {
        String sql = "INSERT INTO courses " +
                "(id, code, title, credits, dept_id, lec_id, max_students)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getId());
            ps.setString(2, c.getCode());
            ps.setString(3, c.getTitle());
            ps.setInt   (4, c.getCredits());
            ps.setString(5, c.getDeptId());
            ps.setString(6, c.getLecId());
            ps.setInt   (7, c.getMaxStudents());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean updateCourse(Course c) {
        String sql = "UPDATE courses " +
                "SET title=?, credits=?, dept_id=?, lec_id=?, max_students=? " +
                "WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getTitle());
            ps.setInt   (2, c.getCredits());
            ps.setString(3, c.getDeptId());
            ps.setString(4, c.getLecId());
            ps.setInt   (5, c.getMaxStudents());
            ps.setString(6, c.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean deleteCourse(String id) {
        return deleteById("courses", id);
    }

    // ════════════════════════════════════════════════════════
    // DEPARTMENT — needed for the course form dropdown
    // ════════════════════════════════════════════════════════

    public static List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT * FROM departments ORDER BY code");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapDept(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ════════════════════════════════════════════════════════
    // LECTURER — needed for the course form dropdown
    // ════════════════════════════════════════════════════════

    public static List<Lecturer> getAllLecturers() {
        List<Lecturer> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT * FROM lecturers ORDER BY name");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapLecturer(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ════════════════════════════════════════════════════════
    // ACTIVITY LOG — called after every create/update/delete
    // ════════════════════════════════════════════════════════

    public static void writeLog(String adminUser, String action) {
        String sql = "INSERT INTO activity_logs (admin_user, action) VALUES (?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, adminUser);
            ps.setString(2, action);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // ════════════════════════════════════════════════════════
    // AUTO ID GENERATOR
    // ════════════════════════════════════════════════════════

    public static String nextId(String tableName, String prefix) {
        String sql = "SELECT MAX(id) FROM " + tableName;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getString(1) != null) {
                String maxId = rs.getString(1);
                int num = Integer.parseInt(
                        maxId.substring(prefix.length())) + 1;
                return String.format("%s%03d", prefix, num);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return prefix + "001";
    }

    // ════════════════════════════════════════════════════════
    // PRIVATE HELPERS
    // ════════════════════════════════════════════════════════

    private static boolean deleteById(String table, String id) {
        String sql = "DELETE FROM " + table + " WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private static Course mapCourse(ResultSet rs) throws SQLException {
        return new Course(
                rs.getString("id"),
                rs.getString("code"),
                rs.getString("title"),
                rs.getInt   ("credits"),
                rs.getString("dept_id"),
                rs.getString("lec_id"),
                rs.getInt   ("max_students")
        );
    }

    private static Department mapDept(ResultSet rs) throws SQLException {
        return new Department(
                rs.getString("id"),
                rs.getString("code"),
                rs.getString("name"),
                rs.getString("hod"),
                rs.getString("phone"),
                rs.getString("email")
        );
    }

    private static Lecturer mapLecturer(ResultSet rs) throws SQLException {
        return new Lecturer(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("dept_id"),
                rs.getString("specialization"),
                rs.getString("type")
        );
    }
}