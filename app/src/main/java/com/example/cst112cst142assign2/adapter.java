package com.example.cst112cst142assign2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class adapter extends ArrayAdapter<CourseMark> {
    TextView tvMark;
    TextView tvWeight;
    TextView tvEval;


    public adapter(Context context, int listview_row, List<CourseMark> objects) {
        super(context, listview_row, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CourseMark courseMark = this.getItem(position);
        View locationItemView = convertView;
        if(convertView == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            locationItemView = layoutInflater.inflate(R.layout.listview_row, null);
            locationItemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                }
            });

            tvMark = locationItemView.findViewById(R.id.etMark);
            tvWeight = locationItemView.findViewById(R.id.etWeight);
            tvEval = locationItemView.findViewById(R.id.etEval);
            tvMark.setText(6.6 + "");
            tvWeight.setText(6.6 + "");
            tvEval.setText("Simon");
        }
        return locationItemView;
    }
}
