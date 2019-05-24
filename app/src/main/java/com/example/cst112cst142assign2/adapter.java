package com.example.cst112cst142assign2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class adapter extends ArrayAdapter<CourseMark> {
    TextView tvMark;
    TextView tvWeight;
    TextView tvEval;


    public adapter(Context context, List<CourseMark> objects) {
        super(context, R.layout.listview_row, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final CourseMark courseMark = this.getItem(position);
        View locationItemView = convertView;
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            locationItemView = layoutInflater.inflate(R.layout.listview_row, parent,false);
            //locationItemView.setOnClickListener(new View.OnClickListener(){

//                @Override
//                public void onClick(View view) {
//
//                }
            //});

            tvMark = locationItemView.findViewById(R.id.etMark);
            tvWeight = locationItemView.findViewById(R.id.etWeight);
            tvEval = locationItemView.findViewById(R.id.etEval);

            tvMark.setText(String.valueOf(courseMark.mark));
            tvWeight.setText(String.valueOf(courseMark.weight));
            tvEval.setText(courseMark.evaluation);
        }
        return locationItemView;
    }
}
