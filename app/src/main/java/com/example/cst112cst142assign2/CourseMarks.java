package com.example.cst112cst142assign2;

public class CourseMarks {

    public  long id;
    public  double finalMark = 0.0;
    public  double finalWeight = 0.0;
    public  double midtermMark = 0.0;
    public  double midtermWeight = 0.0;
    public  double a1Mark = 0.0;
    public double a1Weight = 0.0;
    public  double a2Mark = 0.0;
    public  double a2Weight = 0.0;
    public  double a3Mark = 0.0;
    public  double a3Weight = 0.0;
    public  double a4Mark = 0.0;
    public double a4Weight = 0.0;
    public double courseMark = 0.0;
    public String courseCode = "";




    public CourseMarks( String CourseCode, double finalWeight, double finalMark, double midtermMark, double midtermWeight
    , double a1Mark, double a1Weight, double a2Mark, double a2Weight, double a3Mark, double a3Weight, double a4Mark, double a4Weight)
    {
        this.id = -1;
        this.courseCode = CourseCode;
        this.finalWeight = finalWeight;
        this.finalMark = finalMark;
        this.midtermMark = midtermMark;
        this.midtermWeight = midtermWeight;
        this.a1Mark = a1Mark;
        this.a1Weight = a1Weight;
        this.a2Mark = a2Mark;
        this.a2Weight = a2Weight;
        this.a3Mark = a3Mark;
        this.a3Weight = a3Weight;
        this.a4Mark = a4Mark;
        this.a4Weight = a4Weight;
    }

    public CourseMarks( long id, String CourseCode, double finalWeight, double finalMark, double midtermMark, double midtermWeight
            , double a1Mark, double a1Weight, double a2Mark, double a2Weight, double a3Mark, double a3Weight, double a4Mark, double a4Weight)
    {
       this(CourseCode, finalWeight, finalMark, midtermMark, midtermWeight, a1Mark, a1Weight, a2Mark, a2Weight, a3Mark,a3Weight, a4Mark,a4Weight);
       this.id = id;
    }
}
