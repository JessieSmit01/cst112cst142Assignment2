package com.example.cst112cst142assign2;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MarkActivity extends AppCompatActivity {

    EditText tvWeight;
    EditText tvMark;
    TextView tvEval;
    private ListView lv;
    int position;
    ArrayList<CourseMark> courseMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);

    courseMarks = new ArrayList<CourseMark>();


        getMarks();
        final adapter arrayAdapter = new adapter(this, courseMarks, this);
        lv = (ListView)findViewById(R.id.listView);
        tvMark = (EditText) findViewById(R.id.mark);
        tvWeight = (EditText) findViewById(R.id.weight);

        lv.setAdapter(arrayAdapter);

        Intent in = getIntent();
        Bundle b = in.getExtras();

        if(b != null)
        {
            tvMark = (EditText) findViewById(R.id.mark);
            tvWeight = (EditText) findViewById(R.id.weight);
            String mark = (String) b.get("key");
            Toast.makeText(this, mark, Toast.LENGTH_SHORT).show();
            tvMark.setText(mark);
            String weight = (String)b.get("key2");
            tvWeight.setText(weight);

            position = (int)b.get("position");




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




    public void getMarks()
    {
        CourseMark newCourse1 = new CourseMark("CDMB", "Final",3.2,2.2);
        CourseMark newCourse2 = new CourseMark("CDMB", "Midterm",2.2,2.2);
        CourseMark newCourse3 = new CourseMark("CDMB", "A1",2.2,2.2);
        CourseMark newCourse4 = new CourseMark("CDMB", "A2",2.2,2.2);
        CourseMark newCourse5 = new CourseMark("CDMB", "A3",2.2,2.2);
        CourseMark newCourse6 = new CourseMark("CDMB", "A4",2.2,2.2);
        courseMarks.add(newCourse1);
        courseMarks.add(newCourse2);
        courseMarks.add(newCourse3);
        courseMarks.add(newCourse4);
        courseMarks.add(newCourse5);
        courseMarks.add(newCourse6);

    }
}
