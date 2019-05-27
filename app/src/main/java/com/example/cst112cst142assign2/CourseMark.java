package com.example.cst112cst142assign2;

public class CourseMark {

    public double weight, mark;
    public int nPos;

    public String CourseCode, evaluation;
    public long id;


    /**
     * Constructor used when creating a new CourseMark
     * @param CourseCode
     * @param evaluation
     * @param weight
     * @param mark
     */
    public CourseMark(String CourseCode, String evaluation, double weight, double mark)
    {
        this.id = -1; //use -1 to check if this coursemark is new
        this.evaluation = evaluation;
        this.CourseCode = CourseCode;
        this.weight = weight;
        this.mark = mark;
    }

    /**
     * Constructor used to create a courseMark that already exists in the courseMarks table
     * @param id
     * @param CourseCode
     * @param evaluation
     * @param weight
     * @param mark
     */
    public CourseMark(long id, String CourseCode,  String evaluation, double weight, double mark)
    {
        this.id = id;
        this.evaluation = evaluation;
        this.CourseCode = CourseCode;
        this.weight = weight;
        this.mark = mark;
    }

    @Override
    public String toString()
    {
            return evaluation + weight + mark;
    }




}
