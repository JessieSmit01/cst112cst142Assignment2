package com.example.cst112cst142assign2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseMarksHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Courses.db";
    public static final String TABLE_NAME = "courseMarks";
    public static final int DB_VERSION = 1;
    public static final String ID = "_id";
    public static final String COURSE_CODE = "courseCode";
    public static final String EVALUATION = "evaluation";
    public static final String MARK = "mark";
    public static final String WEIGHT = "weight";


    public SQLiteDatabase sqlDB; // reference to the SQLite database on the file system

    public CourseMarksHelper(Context context)
    {
        // context is required to know where to create the db file
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table to hold in course marks for a single course
        String sCreate = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + "(" +
                ID + " integer primary key autoincrement, " +
                COURSE_CODE + " text not null, " +
                EVALUATION + " text not null, " +
                WEIGHT + " double, " +
                MARK + " double);";

        db.execSQL(sCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // called if the version increases
        // drop existing table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // recreate it
        onCreate(db);
    }

    /**
     * This method will open the database so that we can write or read from it
     * @throws SQLException
     */
    public void open() throws SQLException
    {
        sqlDB = this.getWritableDatabase();
    }

    /**
     * This method closes the database so that data cannot be accidentally written or read
     */
    public void close()
    {
        sqlDB.close();
    }

    /**
     * Method will table in a courseMark and write it to a new table row
     * @param mark
     * @return
     */
    public long createCourseMarks(CourseMark mark)
    {
        ContentValues cvs = new ContentValues();
        cvs.put(COURSE_CODE, mark.CourseCode);
        cvs.put(EVALUATION, mark.evaluation);
        cvs.put(WEIGHT, mark.weight);
        cvs.put(MARK, mark.mark);



        // execute insert, which returns the auto increment value
        long autoid = sqlDB.insert(TABLE_NAME, null, cvs);

        // update the id of the purcahse with the new auto id
        mark.id = autoid;
        return autoid;
    }

    /**
     * Method will be used to update aCoursemark with new data
     * @param mark courseMark to be updated
     * @return
     */
    public boolean updateMarks(CourseMark mark) {
        if (mark.id < 0) // marks have never been saved, cannot update
        {
            return false;
        } else {
            // a container to store each column and value
            ContentValues cvs = new ContentValues();

            cvs.put(COURSE_CODE, mark.CourseCode);
            cvs.put(EVALUATION, mark.evaluation);
            cvs.put(WEIGHT, mark.weight);
            cvs.put(MARK, mark.mark);

            // add an item for each column and value


            return sqlDB.update(TABLE_NAME, cvs, ID + " = " + mark.id, null) > 0;
        }
    }


    /**
     * Takes in a course and deletes all courseMarks that math by CourseCodes
     * @param course
     * @return
     */
    public boolean deleteCourseMarks(Course course)
    {
       // return sqlDB.delete(TABLE_NAME, COURSE_CODE + " = " + course.courseCode, null) > 0;
        return sqlDB.delete(TABLE_NAME, COURSE_CODE + " = " + "'" + course.courseCode + "'", null) > 0;
    }


    /**
     * This method will take in a course code and return a Curser of all rows from the courseMarks table
     * that have a course code equal to the course code passed in
     * @param sCourseCode
     * @return
     */
    public Cursor getAllCourseMarksByCourseCode(String sCourseCode)
    {

        // list of columns to select and return
        String[] sFields = new String [] {ID, COURSE_CODE, EVALUATION, WEIGHT, MARK};
        return sqlDB.query(TABLE_NAME, sFields, COURSE_CODE + " = " + "'" + sCourseCode + "'", null, null, null, null);
    }


    /**
     * This method will be used to select one course code from a table
     * @param id
     * @return
     */
    public CourseMark getCourseMark(long id)
    {
        // list of columns to select and return
        String[] sFields = new String [] {ID, COURSE_CODE, EVALUATION, WEIGHT, MARK};
        Cursor mCursor = sqlDB.query(TABLE_NAME, sFields, ID + " = " + id, null, null, null, null, null);
        if(mCursor != null) // check for a found result
        {
            // move to the first record
            mCursor.moveToFirst();
            return new CourseMark(mCursor.getLong(0), mCursor.getString(1), mCursor.getString(2), mCursor.getDouble(3), mCursor.getDouble(4));
        }
        return null;
    }


}
