package com.example.cst112cst142assign2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MarkActivity extends AppCompatActivity implements View.OnClickListener {

    EditText tvWeight;
    EditText tvMark;
    TextView tvEval;
    private ListView lv;
    int position;
    ArrayList<CourseMark> courseMarks;
    CourseMarksHelper dbMarks;
    Button obSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);

        obSave = (Button)findViewById(R.id.btnSaveMark);

    courseMarks = new ArrayList<CourseMark>();

    dbMarks = new CourseMarksHelper(this);
        getMarks("CDBM");

        final adapter arrayAdapter = new adapter(this, courseMarks, this);
        lv = (ListView)findViewById(R.id.listView);
        tvMark = (EditText) findViewById(R.id.mark);
        tvWeight = (EditText) findViewById(R.id.weight);

        lv.setAdapter(arrayAdapter);

        Intent in = getIntent();
        Bundle b = in.getExtras();

        if(b != null)
        {
            dbMarks.open();
            tvMark = (EditText) findViewById(R.id.mark);
            tvWeight = (EditText) findViewById(R.id.weight);
            String mark = (String) b.get("key");
            Toast.makeText(this, mark, Toast.LENGTH_SHORT).show();
            tvMark.setText(mark);
            String weight = (String)b.get("key2");
            tvWeight.setText(weight);

            position = (int)b.get("position");

            Cursor result = dbMarks.getAllCourseMarks();
            if(result != null && result.moveToFirst())
            {
                do {
                    if(result.getCount() == position)
                    {
                        dbMarks.updateMarks(courseMarks.get(position));
                        dbMarks.getCourseMark(position).mark = Double.parseDouble(tvMark.getText().toString());
                        arrayAdapter.getItem(1).mark = dbMarks.getCourseMark(position).mark;
                    }

                }while(result.moveToNext());
            }



            dbMarks.close();
        }

        tvMark.addTextChangedListener(new TextWatcher() {
            CountDownTimer timer = null;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer != null)
                {
                    timer.cancel();
                }
                timer = new CountDownTimer(1500,1000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        CourseMark obAdapter = (CourseMark) lv.getItemAtPosition(position);
                        obAdapter.mark = Double.parseDouble(tvMark.getText().toString());
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("mark", obAdapter.mark);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }
                }.start();


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        tvWeight.addTextChangedListener(new TextWatcher() {
            CountDownTimer timer = null;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer != null)
                {
                    timer.cancel();
                }
                timer = new CountDownTimer(1500,1000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        CourseMark obAdapter = (CourseMark) lv.getItemAtPosition(position);
                        obAdapter.weight = Double.parseDouble(tvWeight.getText().toString());
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("weight", obAdapter.weight);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }
                }.start();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                double result = data.getDoubleExtra("mark",courseMarks.get(position).mark);
                double weight = data.getDoubleExtra("weight", courseMarks.get(position).weight);
                Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
                courseMarks.get(position).mark = result;
                courseMarks.get(position).weight = weight;

                TextView updateMark;
                updateMark = findViewById(R.id.etMark);
                updateMark.setText(String.valueOf(result));

                TextView updateWeight;
                updateMark = findViewById(R.id.etWeight);
                updateMark.setText(String.valueOf(weight));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Toast.makeText(this, "Thing was Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }




    public void getMarks(String CourseCode)
    {      dbMarks.open();

        Cursor result = dbMarks.getAllCourseMarks();



        //dbMarks.getCourseMark(1);
        if(result != null && result.moveToFirst())
        {
            int i = 1;
            do {
                courseMarks.add(dbMarks.getCourseMark(i));
                i++;
            }while (result.moveToNext());
        }

        dbMarks.close();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnSaveMark:

                dbMarks.open();

                Cursor result = dbMarks.getAllCourseMarks();
                if(result != null && result.moveToFirst())
                {
                    int i = 1;
                    do {
                       dbMarks.updateMarks(courseMarks.get(i-1));
                        i++;
                    }while (result.moveToNext());

                }
                dbMarks.close();

                Intent i3 = new Intent(this, MainActivity.class);
                startActivity(i3);


        }
    }
}
