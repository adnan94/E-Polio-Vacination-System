package com.example.ali.myapplication.Activities.User_Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.ali.myapplication.Activities.Adaptor.User_VisitingDateAdapter;
import com.example.ali.myapplication.Activities.ModelClasses.Polio_Schedule;
import com.example.ali.myapplication.Activities.ModelClasses.Visit_DateObject;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sami Khan on 12/31/2017.
 */

public class View_VisitingDates extends android.support.v4.app.Fragment {

    public GridView gridView;
    public ArrayList<Visit_DateObject> visit_dateObjects;
    public Polio_Schedule polio_schedule;
    public User_VisitingDateAdapter user_visitingDateAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.visit_date,null);

        gridView  = (GridView)view.findViewById(R.id.gridview);
        visit_dateObjects = new ArrayList<>();
        user_visitingDateAdapter = new User_VisitingDateAdapter(visit_dateObjects,getActivity());
        gridView.setAdapter(user_visitingDateAdapter);


        if(getArguments()!=null){
            polio_schedule = getArguments().getParcelable("obj");
        }

        FirebaseHandler.getInstance()
                .getPolio_subSchedule()
                .child(polio_schedule.getSchedule_id())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null){
                            if(dataSnapshot.getValue()!=null){
                                for(DataSnapshot data:dataSnapshot.getChildren()){
                                    Visit_DateObject visit_dateObject = data.getValue(Visit_DateObject.class);
                                    visit_dateObjects.add(visit_dateObject);
                                    user_visitingDateAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        return view;
    }
}
