package com.example.cst112cst142assign2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Courses.db";
    public static final int DB_VERSION = 1;
    public static final String ID = "_id";
    public static final String TABLE_NAME = "courses";
    public static final String COURSECODE = "courseCode";
    public static final String NAME = "name";
    public static final String YEAR = "year";
    public static final String AVERAGE = "average";

    public SQLiteDatabase sqlDB; // reference to the SQLite database on the file system

    public CourseDBHelper(Context context)
    {
        // context is required ot know where to create the db file
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sCreate = "CREATE TABLE " +
                TABLE_NAME + "(" +
                ID + " integer primary key autoincrement, " +
                COURSECODE + " text not null, " +
                NAME + " text not null, " +
                YEAR + " integer not null, " +
                AVERAGE + " integer not null);";
        db.execSQL(sCreate);


        //Need to create table for courseMarks because courseMarksHelper onCreate will not fire since the db will have already existed after this (CourseDBHelper) calls its onCreate method
        sCreate = "CREATE TABLE IF NOT EXISTS " +
                CourseMarksHelper.TABLE_NAME + "(" +
                CourseMarksHelper.ID + " integer primary key autoincrement, " +
                CourseMarksHelper.COURSE_CODE + " text not null, " +
                CourseMarksHelper. EVALUATION + " text not null, " +
                CourseMarksHelper. WEIGHT + " double, " +
                CourseMarksHelper.MARK + " double);";

        db.execSQL(sCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void open() throws SQLException
    {
        sqlDB = this.getWritableDatabase();
    }

    public void close()
    {
        sqlDB.close();
    }

    public long createCourse(Course course)
    {

        // a container to store each column and value
        ContentValues cvs = new ContentValues();

        // add an item for each column and value
        cvs.put(COURSECODE, course.courseCode);
        cvs.put(NAME, course.sName);
        cvs.put(YEAR, course.sYear);
        cvs.put(AVERAGE, course.nAverage);



        // execute insert, which returns the auto increment value
        long autoid = sqlDB.insert(TABLE_NAME, null, cvs);

        // update the id of the purcahse with the new auto id
        course.id = autoid;
        return autoid;
    }

    public boolean updateCourse(Course course)
    {
        if(course.id < 0) // purchase has never been saved, cannot update
        {
            return false;
        }
        else
        {
            // a container to store each column and value
            ContentValues cvs = new ContentValues();

            // add an item for each column and value
            cvs.put(COURSECODE, course.courseCode);
            cvs.put(NAME, course.sName);
            cvs.put(YEAR, course.sYear);
            cvs.put(AVERAGE, course.nAverage);

            return sqlDB.update(TABLE_NAME, cvs, ID + " = " + course.id, null) > 0;
        }
    }



    public boolean deleteCourse(Course course)
    {
        return sqlDB.delete(TABLE_NAME, COURSECODE + " = " +  "'" + course.courseCode + "'", null) > 0;

    }


    public Cursor getAllCourses()
    {
        // you may want to return a List of FuelPurchase items instead
        // list of columns to select and return
        String[] sFields = new String [] {ID, COURSECODE, NAME, YEAR, AVERAGE};
        return sqlDB.query(TABLE_NAME, sFields, null, null, null, null, null);
    }

//    public Course getCourse(long id)
//    {
//        // list of columns to select and return
//        String[] sFields = new String [] {ID, COURSECODE, NAME, YEAR};
//        Cursor cCursor = sqlDB.query(TABLE_NAME, sFields, ID + " = " + id, null, null, null, null, null);
//        if(cCursor != null) // check for a found result
//        {
//            // move to the first record
//            cCursor.moveToFirst();
//            return new Course(cCursor.getLong(0), cCursor.getString(1), cCursor.getString(2), cCursor.getInt(3));
//        }
//        return null;
//    }






}


