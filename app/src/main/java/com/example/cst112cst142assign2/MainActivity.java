package com.example.cst112cst142assign2;

import android.content.Intent;
import android.database.Cursor;

import android.provider.MediaStore;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText etCourseCode, etName;
    private Spinner spinner;
    private Cursor cursor;
    private CourseDBHelper db;
    private CourseMarksHelper dbMarks;
    private Button btnEdit, btnSave, btnNew, btnDelete;
    private RadioGroup rdYear;
    private int currentYear;

    private long id = -1;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = findViewById(R.id.spinner);
        etCourseCode = findViewById(R.id.etCode);
        etName =findViewById(R.id.etName);
        spinner.setOnItemSelectedListener(this);

        rdYear = findViewById(R.id.rdYear);


        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);


        db = new CourseDBHelper(this);
        dbMarks = new CourseMarksHelper(this);

        refreshData();


//       db.open();


        Course obCcourse = new Course("CDBM190", "Database management", 1);
        db = new CourseDBHelper(this);
        refreshData();


        //      db.open();

//        db.createCourse(obCcourse);
//
//        db.close();

 //       CourseMarksHelper obMarks = new CourseMarksHelper(this);

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
        cursor = db.getAllCourses(); // fill cursor
      //  cursor = db.getAllFuelPurchases(); // fill cursor

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
        this.id = cursor.getLong(0);
        etCourseCode.setText(cursor.getString(1));
        etName.setText(cursor.getString(2));

        this.currentYear = cursor.getInt(3);

        if(this.currentYear == 2)
        {
            ((RadioButton)findViewById(R.id.rdY2)).setChecked(true);
        }
        else
        {
            ((RadioButton)findViewById(R.id.rdY1)).setChecked(true);
        }
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
        Course obCourse;

        switch(view.getId())
        {

            case R.id.btnEdit:
                Intent i = new Intent(this, MarkActivity.class);
                i.putExtra("coursecode", this.etCourseCode.getText().toString());
                MainActivity.this.startActivity(i);

                break;
            case R.id.btnNew:
                clearFields();
                break;
            case R.id.btnDelete:
                db.open();
                dbMarks.open();
                obCourse = new Course(id, etCourseCode.getText().toString(), etName.getText().toString(), this.currentYear);
                db.deleteCourse(obCourse);
                dbMarks.deleteCourseMarks(obCourse);
                clearFields();
                refreshData();
                db.close();
                dbMarks.close();

                break;
            case R.id.btnSave:
                db.open();
                obCourse = getCourseFromFields();
                if(obCourse != null && this.id == -1)
                {
                    db.open();
                    db.createCourse(obCourse);
                    createMarksForCourse(obCourse.courseCode);
                    db.close();
                    refreshData();
                }
                else
                {
                    if(obCourse == null)
                    {
                        Toast.makeText(this, "Please fill in the course information", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    db.open();
                    db.updateCourse(obCourse);
                    db.close();
                    refreshData();
                }




        }
    }

    public void clearFields()
    {
        etCourseCode.setText("");
        etName.setText("");
        etCourseCode.setFocusableInTouchMode(true);
        etName.setFocusableInTouchMode(true);
        rdYear.clearCheck();
        this.id = -1;
    }

    public Course getCourseFromFields()
    {
        int yearSelectedId = rdYear.getCheckedRadioButtonId();
        int currentYear;
        if(yearSelectedId == -1)
        {
            Toast.makeText(this, "Please select a year", Toast.LENGTH_SHORT).show();
            return null;
        }
        else {
            if(this.id == -1)
            {
                RadioButton obSelected = findViewById(yearSelectedId);
                currentYear = obSelected.equals(findViewById(R.id.rdY1)) ? 1 : 2;
                return new Course(this.etCourseCode.getText().toString(), this.etName.getText().toString(), currentYear);
            }
            else
            {
                RadioButton obSelected = findViewById(yearSelectedId);
                currentYear = obSelected.equals(findViewById(R.id.rdY1)) ? 1 : 2;
                return new Course(this.id, this.etCourseCode.getText().toString(), this.etName.getText().toString(), currentYear);
            }

        }

    }
}

