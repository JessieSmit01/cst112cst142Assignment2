package com.example.cst112cst142assign2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MarkActivity extends AppCompatActivity {

    TextView tvWeight;
    TextView tvMark;
    TextView tvEval;
    private ListView lv;
    ArrayList<CourseMark> courseMarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        courseMarks = new ArrayList<CourseMark>();
        getMarks();
        adapter arrayAdapter = new adapter(this, R.layout.listview_row, courseMarks);
        lv = (ListView)findViewById(R.id.listView);


        tvWeight = findViewById(R.id.etWeight);
        tvMark = findViewById(R.id.etMark);
        tvEval = findViewById(R.id.etEval);

        lv.setAdapter(arrayAdapter);


    }

    public void getMarks()
    {
        CourseMark newCourse = new CourseMark("Simon", "Simon",2.2,2.2);
        courseMarks.add(newCourse);
    }
}
