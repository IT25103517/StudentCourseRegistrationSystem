package com.university.management.model;

public class Course {

    private String id;
    private String code;
    private String title;
    private int credits;
    private String deptId;
    private String lecId;
    private int maxStudents;

    public Course(String id, String code, String title, int credits,
                  String deptId, String lecId, int maxStudents) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.deptId = deptId;
        this.lecId = lecId;
        this.maxStudents = maxStudents;
    }

    public String getId()          { return id; }
    public String getCode()        { return code; }
    public String getTitle()       { return title; }
    public int    getCredits()     { return credits; }
    public String getDeptId()      { return deptId; }
    public String getLecId()       { return lecId; }
    public int    getMaxStudents() { return maxStudents; }

    public void setTitle(String t)     { this.title = t; }
    public void setCredits(int c)      { this.credits = c; }
    public void setDeptId(String d)    { this.deptId = d; }
    public void setLecId(String l)     { this.lecId = l; }
    public void setMaxStudents(int m)  { this.maxStudents = m; }
}