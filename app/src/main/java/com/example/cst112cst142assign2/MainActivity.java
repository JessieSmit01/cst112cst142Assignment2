package com.example.cst112cst142assign2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText etCourseCode, etName;
    private Spinner spinner;
    private Cursor cursor;
    private CourseDBHelper db;
    private CourseMarksHelper dbMarks;
    private Button btnEdit, btnSave, btnNew, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        etCourseCode = findViewById(R.id.etCode);
        etName =findViewById(R.id.etName);
        spinner.setOnItemSelectedListener(this);

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        Course obCcourse = new Course("CDBM190", "Database management", 1);
        db = new CourseDBHelper(this);
        dbMarks = new CourseMarksHelper(this);
        refreshData();


 //      db.open();
//        db.createCourse(obCcourse);
//
//        db.close();

 //       CourseMarksHelper obMarks = new CourseMarksHelper(this);
//        obMarks.open();
//        obMarks.createCourseMarks(new CourseMark("CDBM190", "Final", 40, 90));
//
//        obMarks.close();

        //ArrayList<CourseMarks> arrayOfCourseMarks = new ArrayList<CourseMarks>();

        //RowCursorAdapter adapter = new RowCursorAdapter(this, arrayOfCourseMarks);



    }

    private void refreshData()
    {
        // ensure the spinner has all of the rows from the table
        db.open();
        cursor = db.getAllFuelPurchases(); // fill cursor

        // the cursor adapter will be the link between the cursor and the spinner
        String [] cols = new String[] {db.COURSECODE}; // this is a list of columns to show on the view (spinner)
        int [] views = new int [] {android.R.id.text1}; // list of views to place the data

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, cols, views);

        // assign the adapter to the spinner
        spinner.setAdapter(adapter);

        db.close();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // get the data from the cursor which has been moved by the adapter to point to the selected item
        etCourseCode.setText(cursor.getString(1));
        etName.setText(cursor.getString(2));


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void createMarksForCourse(String CourseCode)
    {
        dbMarks.open();
        dbMarks.createCourseMarks(new CourseMark(CourseCode, "Final", 0, 0));
        dbMarks.createCourseMarks(new CourseMark(CourseCode, "Midterm", 0, 0));
        dbMarks.createCourseMarks(new CourseMark(CourseCode, "A1", 0, 0));
        dbMarks.createCourseMarks(new CourseMark(CourseCode, "A2", 0, 0));
        dbMarks.createCourseMarks(new CourseMark(CourseCode, "A3", 0, 0));
        dbMarks.createCourseMarks(new CourseMark(CourseCode, "A4", 0, 0));
        dbMarks.close();
    }

    @Override
    public void onClick(View view) {


        switch(view.getId())
        {
            case R.id.btnEdit:
                etCourseCode.setFocusableInTouchMode(true);
                etName.setFocusableInTouchMode(true);
                break;
            case R.id.btnNew:
                etCourseCode.setText("");
                etName.setText("");
                etCourseCode.setFocusableInTouchMode(true);
                etName.setFocusableInTouchMode(true);
                break;

        }
    }
}
