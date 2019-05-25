package com.example.cst112cst142assign2;

public class Course {
    public long id;
    public String courseCode;
    public String sName;
    public int sYear;
    public int nAverage;

    public Course(String courseCode, String sName, int sYear)
    {
        this.id = -1;
        this.nAverage = 0;
        this.courseCode = courseCode;
        this.sName = sName;
        this.sYear = sYear;
    }
    public Course(Long id, String courseCode, String sName, int sYear, int nAverage)
    {
        this.id = id;
        this.courseCode = courseCode;
        this.sName = sName;
        this.sYear = sYear;
        this.nAverage = nAverage;
    }
}
