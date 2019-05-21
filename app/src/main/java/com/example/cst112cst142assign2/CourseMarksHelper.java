package com.example.cst112cst142assign2;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseMarksHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Courses.db";
    public static final String TABLE_NAME = "CourseMarks";
    public static final int DB_VERSION = 1;
    public static final String ID = "_id";
    public static final String FINAL_MARK = "finalMark";
    public static final String FINAL_WEIGHT = "finalWeight";
    public static final String MIDTERM_MARK = "midtermMark";
    public static final String MIDTERM_WEIGHT = "midtermWeight";
    public static final String A1_MARK = "a1Mark";
    public static final String A1_WEIGHT = "a1Weight";
    public static final String A2_MARK = "a2Mark";
    public static final String A2_WEIGHT = "a2Weight";
    public static final String A3_MARK = "a3Mark";
    public static final String A3_WEIGHT = "a3Weight";
    public static final String A4_MARK = "a4Mark";
    public static final String A4_WEIGHT = "a4Weight";
    public static final String COURSEMARK = "coursemark";
    public static final String COURSECODE = "coursecode";

    public SQLiteDatabase sqlDataBase;

    public CourseMarksHelper(Context obContext)
    {
        super(obContext, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sCreate = "CREATE TABLE " +
                TABLE_NAME + "(" +
                ID + "integer primary key autoincrement, " +
                COURSECODE + "String not null," +
                COURSEMARK + "double, " +
                FINAL_MARK + "double, " +
                FINAL_WEIGHT + "double, " +
                MIDTERM_MARK + "double, " +
                MIDTERM_WEIGHT + "double, " +
                A1_MARK + "double, " +
                A1_WEIGHT + "double, " +
                A2_MARK + "double, " +
                A2_WEIGHT + "double, " +
                A3_MARK + "double, " +
                A3_WEIGHT + "double, " +
                A4_MARK + "double, " +
                A4_WEIGHT + "double);";

    sqLiteDatabase.execSQL(sCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqlDataBase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void onOpen() throws SQLException
    {
        sqlDataBase = this.getWritableDatabase();
    }

    public void close() {
        sqlDataBase.close();
    }

    public long createCourseMarks(CourseMarks obCourseMarks)
    {
        ContentValues cvs = new ContentValues();

        cvs.put(COURSECODE, obCourseMarks.courseCode);
        cvs.put(COURSEMARK, obCourseMarks.courseMark);
        cvs.put(FINAL_MARK, obCourseMarks.finalMark);
        cvs.put(FINAL_WEIGHT, obCourseMarks.finalWeight);
        cvs.put(MIDTERM_MARK, obCourseMarks.midtermMark);
        cvs.put(MIDTERM_WEIGHT, obCourseMarks.midtermWeight);
        cvs.put(A1_MARK, obCourseMarks.a1Mark);
        cvs.put(A1_WEIGHT, obCourseMarks.a1Weight);
        cvs.put(A2_MARK, obCourseMarks.a2Mark);
        cvs.put(A2_WEIGHT, obCourseMarks.a2Weight);
        cvs.put(A3_MARK, obCourseMarks.a3Mark);
        cvs.put(A3_WEIGHT, obCourseMarks.a3Weight);
        cvs.put(A4_MARK, obCourseMarks.a4Mark);
        cvs.put(A4_WEIGHT, obCourseMarks.a4Weight);

        long autoid = sqlDataBase.insert(TABLE_NAME, null, cvs);
        return autoid;
    }

}
