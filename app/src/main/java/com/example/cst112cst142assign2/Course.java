package com.example.cst112cst142assign2;

public class Course {
    public long id;
    public String courseCode;
    public String sName;
    public int sYear;
    public int nAverage;


    /**
     * Constructor used to create a new Course for writing to the database
     * @param courseCode
     * @param sName
     * @param sYear
     */
    public Course(String courseCode, String sName, int sYear)
    {
        this.id = -1; //courses with an id of 1 represent a new course that has not been saved to database
        this.nAverage = 0;
        this.courseCode = courseCode;
        this.sName = sName;
        this.sYear = sYear;
    }

    /**
     * Constructor used for creating an exising course that was read in from the database
     * @param id
     * @param courseCode
     * @param sName
     * @param sYear
     * @param nAverage
     */
    public Course(Long id, String courseCode, String sName, int sYear, int nAverage)
    {
        this.id = id;
        this.courseCode = courseCode;
        this.sName = sName;
        this.sYear = sYear;
        this.nAverage = nAverage;
    }
}
