package com.example.cst112cst142assign2;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MarkActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private EditText tvWeight;
    private EditText tvMark;
    private TextView tvEvalWeight, tvEvalMark;
    private TextView tvCourseCode;
    private String sCourseCode, sCourseName;
    private long courseId;
    private int nYear, nAvg;
    private long currentMarkId;
    private CourseDBHelper courseDB;
    private int currentPos;
    Button btnStart;


    private ListView lv;
    CourseMarksHelper markHelper = new CourseMarksHelper(this);
    ArrayList<CourseMark> courseMarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        btnStart = findViewById(R.id.btnSave2);
        btnStart.setOnClickListener(this);


        this.tvMark = findViewById(R.id.mark);
        this.tvEvalMark = findViewById(R.id.evaluationMark);
        this.tvWeight = findViewById(R.id.weight);
        this.tvEvalWeight = findViewById(R.id.evaluationWeight);

        courseMarks = new ArrayList<CourseMark>();

        //grab the values passed through the intent and assign them to variables
        this.sCourseCode = getIntent().getStringExtra("coursecode");
        this.sCourseName = getIntent().getStringExtra("name");
        this.courseId = getIntent().getLongExtra("courseid", -1);
        this.nYear = getIntent().getIntExtra("year", 0);
        this.nAvg = getIntent().getIntExtra("average", 0);
        tvCourseCode = findViewById(R.id.txtCourseCode);
        tvCourseCode.setText(this.sCourseCode);
        courseDB = new CourseDBHelper(this);
        getMarks();
        adapter arrayAdapter = new adapter(this, courseMarks);
        lv = findViewById(R.id.listView);


        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(this);


    }


    /**
     * This method will go through the marks table and select all course marks that match this activites current sCourseCode value
     * For each row retrieved it will create a CourseMark object and add it to the arraylist courseMarks
     */
    public void getMarks()
    {
        markHelper.open();
        Cursor cursor = markHelper.getAllCourseMarksByCourseCode(this.sCourseCode);
        cursor.moveToFirst();
       for(int j = 0; j < cursor.getCount(); j++)
        {
           CourseMark obMark = new CourseMark(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getDouble(4));
           obMark.nPos = j;
            this.courseMarks.add(obMark);
            cursor.moveToNext();
        }



    }



//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }

    /**
     * When a course is selected from the spinner of courses, this method will grab the courses data and fill in the text views and radio buttons based on the courses
     * Saved data
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CourseMark obCourseMark = courseMarks.get(i); //get the corresponding course mark and set the values into the "Edit marks" portion of the page
        tvEvalWeight.setText(obCourseMark.evaluation + " Weight:");
        tvEvalMark.setText(obCourseMark.evaluation + " Mark:");
        tvMark.setText(obCourseMark.mark + "");
        tvWeight.setText(obCourseMark.weight + "");
        this.currentMarkId = obCourseMark.id;
        this.currentPos = obCourseMark.nPos;
    }


    /**
     * This mark will update the current marks in the database to match the user's inputs
     * @param nPos
     * @param nMark
     * @param nWeight
     */
    public void updateMark(int nPos, double nMark, double nWeight)
    {
        CourseMark obMark = courseMarks.get(nPos);
        obMark.mark = nMark;
        obMark.weight = nWeight;
        markHelper.updateMarks(obMark);
        Toast.makeText(this, "Changes Saves", Toast.LENGTH_SHORT).show();

        //Next we need to update the marks in the listview
        //Refresh the listview
        courseMarks = new ArrayList<>();
        getMarks();
        adapter newAdapter = new adapter(this, courseMarks);
        lv.setAdapter(newAdapter);
        adapter arrayAdapter = new adapter(this, courseMarks);

        updateAverage(); //Update the average in the Course table to reflect the newly updates marks
    }

    /**
     * Get the buttons id that was clicked and complete the button's task
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnSave2:
                if(this.tvWeight.getText().toString().equals("") || tvEvalWeight.getText().toString().equals("Weight:"))
                {
                    Toast.makeText(this, "Mark must be selected before saving", Toast.LENGTH_SHORT).show(); //in case a user tries to save without selecting a mark
                }
                else {
                    updateMark(this.currentPos, Double.parseDouble(tvMark.getText().toString()), Double.parseDouble(tvWeight.getText().toString())); //update selected marks
                }
        }
    }

    /**
     * This method will be used to update the average in the Course table
     * It will go through the arraylsit of courseMarks and calculate the average.
     * It will then update the average in Course table
     */
    public void updateAverage()
    {
        Course obCourse = new Course(this.courseId, this.sCourseCode, this.sCourseName, this.nYear, this.nAvg);
        int nNewAvg = 0;

        for(CourseMark obMark : courseMarks)
        {
            nNewAvg += (obMark.weight/100) * obMark.mark;
        }

        obCourse.nAverage = nNewAvg;

        courseDB.open();

        courseDB.updateCourse(obCourse);

        courseDB.close();


    }


}
