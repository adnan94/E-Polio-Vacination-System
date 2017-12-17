package com.example.ali.myapplication.Activities.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.ModelClasses.Visit_DateObject;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Sami Khan on 12/12/2017.
 */

public class Team_visitDate extends BaseAdapter {

    public ArrayList<Visit_DateObject> visit_dateObjects;
    public Context mcontext;

    public Team_visitDate(ArrayList<Visit_DateObject> visit_dateObjects, Context mcontext) {
        this.visit_dateObjects = visit_dateObjects;
        this.mcontext = mcontext;
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

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.visit_dateview, null);

        TextView date_visit = (TextView) v.findViewById(R.id.date_visit);
        Utils.relwayRegular(mcontext,date_visit);
        TextView visit_month = (TextView) v.findViewById(R.id.visit_month);
        Utils.relwayRegular(mcontext,visit_month);
        TextView visit_time = (TextView)v.findViewById(R.id.visit_time);
        Utils.relwayRegular(mcontext,visit_time);
        String s[] = visit_dateObjects.get(i).getVisit_date().split("/");

        date_visit.setText(s[0]);

        if(s[1].equals("1")){
            s[1] = "January";
        }else if(s[1].equals("2")){
            s[1] = "February";
        }else if(s[1].equals("3")){
            s[1] = "March";
        }else if(s[1].equals("4")){
            s[1] = "April";
        }else if(s[1].equals("5")){
            s[1] = "May";
        }else if(s[1].equals("6")){
            s[1] = "June";
        }else if(s[1].equals("7")){
            s[1] = "July";
        }else if(s[1].equals("8")){
            s[1] = "August";
        }else if(s[1].equals("9")){
            s[1] = "September";
        }else if(s[1].equals("10")){
            s[1] = "October";
        }else if(s[1].equals("11")){
            s[1] = "November";
        }else if(s[1].equals("12")){
            s[1] = "December";
        }

        visit_month.setText(s[1]);
        visit_time.setText(visit_dateObjects.get(i).getVisit_for()+" - "+visit_dateObjects.get(i).getVisit_to());

        return v;
    }
}
