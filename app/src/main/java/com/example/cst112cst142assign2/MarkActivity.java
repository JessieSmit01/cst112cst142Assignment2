package com.example.cst112cst142assign2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MarkActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView tvWeight, tvEvalWeight;
    TextView tvMark, tvEvalMark;
    TextView tvEval;
    TextView tvCourseCode;
    String sCourseCode;

    private ListView lv;
    CourseMarksHelper markHelper = new CourseMarksHelper(this);
    ArrayList<CourseMark> courseMarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);

        this.tvMark = findViewById(R.id.mark);
        this.tvEvalMark = findViewById(R.id.evaluationMark);
        this.tvWeight = findViewById(R.id.weight);
        this.tvEvalWeight = findViewById(R.id.evaluationWeight);

        courseMarks = new ArrayList<CourseMark>();
        this.sCourseCode = getIntent().getStringExtra("coursecode");
        tvCourseCode = findViewById(R.id.txtCourseCode);
        tvCourseCode.setText(this.sCourseCode);

        getMarks();
        adapter arrayAdapter = new adapter(this, courseMarks);
        lv = findViewById(R.id.listView);


        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(this);


    }

    public void getMarks()
    {
        markHelper.open();
        Cursor cursor = markHelper.getAllCourseMarksByCourseCode(this.sCourseCode);
        cursor.moveToFirst();
       for(int j = 0; j < cursor.getCount(); j++)
        {

            this.courseMarks.add(new CourseMark(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getDouble(4)));
            cursor.moveToNext();
        }

        //courseMarks.add(newCourse);

    }



//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CourseMark obCourseMark = courseMarks.get(i);
        tvEvalWeight.setText(obCourseMark.evaluation);
        tvEvalMark.setText(obCourseMark.evaluation);
    }
}
