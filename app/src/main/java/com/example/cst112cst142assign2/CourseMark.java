package com.example.cst112cst142assign2;

public class CourseMark {

    public double weight, mark;
    public int nPos;

    public String CourseCode, evaluation;
    public long id;


    public CourseMark(String CourseCode, String evaluation, double weight, double mark)
    {
        this.id = -1;
        this.evaluation = evaluation;
        this.CourseCode = CourseCode;
        this.weight = weight;
        this.mark = mark;
    }

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
