package com.example.ali.myapplication.Activities.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.ModelClasses.Polio_Schedule;
import com.example.ali.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Sami Khan on 12/10/2017.
 */

public class SheduleListAdapter extends BaseAdapter {

    public ArrayList<Polio_Schedule> polio_schedules;
    public Context context;

    public SheduleListAdapter(ArrayList<Polio_Schedule> polio_schedules, Context context) {
        this.polio_schedules = polio_schedules;
        this.context = context;
    }

    @Override
    public int getCount() {
        return polio_schedules.size();
    }

    @Override
    public Object getItem(int i) {
        return polio_schedules.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.schedule_view, null);
        TextView schedule_title = (TextView)v.findViewById(R.id.schedule_title);
        TextView schedule_des = (TextView)v.findViewById(R.id.schedule_des);
        TextView datee = (TextView)v.findViewById(R.id.datee);

        schedule_title.setText(polio_schedules.get(i).getSchedule_titile());
        schedule_des.setText(polio_schedules.get(i).getSchedule_des());
        datee.setText(polio_schedules.get(i).getSchedule_from()+" - "+polio_schedules.get(i).getSchedule_to());



        return v;
    }
}
