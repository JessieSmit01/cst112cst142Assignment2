package com.example.cst112cst142assign2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText etCourseCode, etName;
    private Spinner spinner;
    private Cursor cursor;
    private CourseDBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        etCourseCode = findViewById(R.id.etCode);
        etName =findViewById(R.id.etName);
        spinner.setOnItemSelectedListener(this);

        Course obCcourse = new Course("CDBM190", "Database management", 1);
        db = new CourseDBHelper(this);
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
}
