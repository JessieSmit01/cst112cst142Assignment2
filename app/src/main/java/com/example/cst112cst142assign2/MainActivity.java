package com.example.cst112cst142assign2;


import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;


import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText etCourseCode, etName;
    private Spinner spinner;
    private Cursor cursor;
    private CourseDBHelper db;
    private CourseMarksHelper dbMarks;
    private Button btnEdit, btnSave, btnNew, btnDelete;
    private RadioGroup rdYear;
    private int currentYear;
    private int currentAvg;

    private long id = -1;

    SharedPreferences settings;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = findViewById(R.id.spinner);
        etCourseCode = findViewById(R.id.etCode);
        etName =findViewById(R.id.etName);

        spinner.setOnItemSelectedListener(this);

        //Initiate the shared preferences
        settings = getSharedPreferences("prefYear", Context.MODE_PRIVATE);
        editor = settings.edit();


        rdYear = findViewById(R.id.rdYear);


        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        btnSave = findViewById(R.id.btnSave2);
        btnSave.setOnClickListener(this);


        db = new CourseDBHelper(this);
        dbMarks = new CourseMarksHelper(this);

        refreshData();

        db = new CourseDBHelper(this);
        refreshData();



    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        refreshData();
    }

    private void refreshData()
    {
        // ensure the spinner has all of the rows from the table
        db.open();
        cursor = db.getAllCourses(); // fill cursor
      //  cursor = db.getAllFuelPurchases(); // fill cursor

        // the cursor adapter will be the link between the cursor and the spinner
        String [] cols = new String[] {db.COURSECODE, db.AVERAGE}; // this is a list of columns to show on the view (spinner)
        int [] views = new int [] {R.id.text1, R.id.text2}; // list of views to place the data

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.spinner_layout, cursor, cols, views);

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
        this.currentAvg = cursor.getInt(4);

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

    /**
     * This method takes in a view (Button) clicked with actions based on what button is clicked
     * @param view
     */
    @Override
    public void onClick(View view) {
        Course obCourse;

        switch(view.getId())
        {

            case R.id.btnEdit: //edit button clicked

                if(this.id > -1) {
                    Intent i = new Intent(this, MarkActivity.class);
                    i.putExtra("courseid", this.id);
                    i.putExtra("coursecode", this.etCourseCode.getText().toString());
                    i.putExtra("name", this.etName.getText().toString());
                    i.putExtra("year", this.currentYear);
                    i.putExtra("average", this.currentAvg);
                    MainActivity.this.startActivity(i);
                }
                else
                {
                    Toast.makeText(this, "Please save this course before editing marks", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnNew: //New button clicked
                clearFields();
                break;

            case R.id.btnDelete: //delete button clicked
                db.open();
                dbMarks.open();
                obCourse = new Course(id, etCourseCode.getText().toString(), etName.getText().toString(), this.currentYear, this.currentAvg);
                db.deleteCourse(obCourse);
                dbMarks.deleteCourseMarks(obCourse);
                clearFields();
                refreshData();
                db.close();
                dbMarks.close();

                break;
            case R.id.btnSave2: //Save button clicked
                db.open();
                obCourse = getCourseFromFields();
                if(obCourse != null && this.id == -1)
                {
                    db.open();
                    db.createCourse(obCourse);
                    createMarksForCourse(obCourse.courseCode);
                    db.close();
                    currentYear = ((RadioButton)(findViewById(R.id.rdY1))).isChecked() ? 1 : 2;
                    editor.putInt("prefYear", currentYear);
                    editor.commit();
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
                    currentYear = ((RadioButton)(findViewById(R.id.rdY1))).isChecked() ? 1 : 2;
                    editor.putInt("prefYear", currentYear);
                    editor.commit();
                    refreshData();
                }




        }
    }

    /**
     * This method clears all fields and sets the year radio group to the last selected which is saved in shared preferences
     */
    public void clearFields()
    {
        etCourseCode.setText("");
        etName.setText("");
        etCourseCode.setFocusableInTouchMode(true);
        etName.setFocusableInTouchMode(true);
        if(settings.getInt("prefYear", 1) == 1)
        {
            ((RadioButton) findViewById(R.id.rdY1)).setChecked(true);
        }
        else
        {
            ((RadioButton) findViewById(R.id.rdY2)).setChecked(true);

        }

        this.id = -1;
    }

    /**
     * This method will take in all data from fields and create a course which it will then return
     * @return
     */
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
            if(this.id == -1) //check if this is a new course
            {
                RadioButton obSelected = findViewById(yearSelectedId);
                currentYear = obSelected.equals(findViewById(R.id.rdY1)) ? 1 : 2;
                editor.putInt("prefYear", currentYear);
                editor.commit();
                return new Course(this.etCourseCode.getText().toString(), this.etName.getText().toString(), currentYear);
            }
            else
            {
                RadioButton obSelected = findViewById(yearSelectedId);
                currentYear = obSelected.equals(findViewById(R.id.rdY1)) ? 1 : 2;
                editor.putInt("prefYear", currentYear);
                editor.commit();
                return new Course(this.id, this.etCourseCode.getText().toString(), this.etName.getText().toString(), currentYear, this.currentAvg);
            }

        }

    }
}

