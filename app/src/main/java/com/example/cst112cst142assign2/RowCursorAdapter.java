package com.example.cst112cst142assign2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RowCursorAdapter extends ArrayAdapter<CourseMarks> {

    public RowCursorAdapter(Context context, ArrayList<CourseMarks> list)
    {
        super(context,0,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CourseMarks obCourseMark = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_row, parent, false);
        }
        TextView tvMark = (TextView) convertView.findViewById(R.id.etMark);
        TextView tvWeight = (TextView) convertView.findViewById(R.id.etWeight);

        tvWeight.setText( obCourseMark.courseCode);
        tvMark.setText( obCourseMark.courseCode);

        return convertView;
    }



}
