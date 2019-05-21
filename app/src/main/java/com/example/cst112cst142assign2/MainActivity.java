package com.example.cst112cst142assign2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<CourseMarks> arrayOfCourseMarks = new ArrayList<CourseMarks>();

        RowCursorAdapter adapter = new RowCursorAdapter(this, arrayOfCourseMarks);

        ListView listView = (ListView) findViewById(R.id.listBox);
        listView.setAdapter(adapter);

        CourseMarks obCourse = new CourseMarks("CDMB",2,2,2,2,2,2,2,2,2,2,2,2);
        adapter.add(obCourse);
    }


}
