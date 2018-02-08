package com.example.ali.myapplication.Activities.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.ModelClasses.Visit_DateObject;
import com.example.ali.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Sami Khan on 12/31/2017.
 */

public class User_VisitingDateAdapter extends BaseAdapter {

    public ArrayList<Visit_DateObject> visit_dateObjects;
    public Context context;
    public TextView start_time,end_time,month,date_visit;

    public User_VisitingDateAdapter(ArrayList<Visit_DateObject> visit_dateObjects, Context context) {
        this.visit_dateObjects = visit_dateObjects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return visit_dateObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return visit_dateObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View v = layoutInflater.inflate(R.layout.visit_date_view,null);

        start_time = (TextView)v.findViewById(R.id.start_time);
        end_time = (TextView)v.findViewById(R.id.end_time);
        month = (TextView)v.findViewById(R.id.month);
        date_visit = (TextView)v.findViewById(R.id.date_visit);

        start_time.setText(visit_dateObjects.get(i).getVisit_for());
        end_time.setText(visit_dateObjects.get(i).getVisit_to());

        String datee[] = visit_dateObjects.get(i).getVisit_date().split("/");

        if(datee[1].equals("01")){
            month.setText("Jan");
        }else if(datee[1].equals("02")){
            month.setText("Feb");
        }else if(datee[1].equals("03")){
            month.setText("Mar");
        }else if(datee[1].equals("04")){
            month.setText("Apr");
        }else if(datee[1].equals("05")){
            month.setText("May");
        }else if(datee[1].equals("06")){
            month.setText("Jun");
        }else if(datee[1].equals("07")){
            month.setText("Jul");
        }else if(datee[1].equals("08")){
            month.setText("Aug");
        }else if(datee[1].equals("09")){
            month.setText("Sep");
        }else if(datee[1].equals("10")){
            month.setText("Oct");
        }else if(datee[1].equals("11")){
            month.setText("Nov");
        }else if(datee[1].equals("12")){
            month.setText("Dec");
        }

        date_visit.setText(datee[0]);


        return v;
    }
}
