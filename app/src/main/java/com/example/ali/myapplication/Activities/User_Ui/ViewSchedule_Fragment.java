package com.example.ali.myapplication.Activities.User_Ui;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.ali.myapplication.Activities.Adaptor.SheduleListAdapter;
import com.example.ali.myapplication.Activities.Admin_Ui.UcMemberDetails;
import com.example.ali.myapplication.Activities.ModelClasses.Polio_Schedule;
import com.example.ali.myapplication.Activities.ModelClasses.Visit_DateObject;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sami Khan on 12/20/2017.
 */

public class ViewSchedule_Fragment extends android.support.v4.app.Fragment {

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    public List<WeekViewEvent> events;
    public ListView shedule_list;
    public ArrayList<Polio_Schedule> polio_scheduleArrayList;
    public SheduleListAdapter sheduleListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.view_schedule, null);

        shedule_list = (ListView)view.findViewById(R.id.shedule_list);
        polio_scheduleArrayList = new ArrayList<>();
        sheduleListAdapter = new SheduleListAdapter(polio_scheduleArrayList,getActivity());
        shedule_list.setAdapter(sheduleListAdapter);


        FirebaseHandler.getInstance().getPolio_schedule()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null){
                            if(dataSnapshot.getValue()!=null){
                                for(DataSnapshot data:dataSnapshot.getChildren()) {
                                    Polio_Schedule polio_schedule = data.getValue(Polio_Schedule.class);
                                    polio_scheduleArrayList.add(polio_schedule);
                                    sheduleListAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        shedule_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("obj",polio_scheduleArrayList.get(i));
                View_VisitingDates ucMemberDetails = new View_VisitingDates();
                ucMemberDetails.setArguments(bundle);

                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,ucMemberDetails).addToBackStack(null).commit();

            }
        });


        return view;
    }


}
