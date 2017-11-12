package com.example.ali.myapplication.Activities.Uc_Ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ali.myapplication.R;

/**
 * Created by Sami Khan on 11/12/2017.
 */

public class Add_PolioTeam_Fragment extends android.support.v4.app.Fragment {
    public LinearLayout add_members;
    public String[] menuName = {"Home", "Add Polio Teams", "View Polio Teams", "About", "Setting", "Log Out"};
    public Spinner spinner_area;
    public String[] areaName = {"Nazimabad", "Johar", "Gulshan", "Landi", "Malir"};
    public ArrayAdapter arrayAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_polioteam_fragment, null);
        add_members = (LinearLayout)view.findViewById(R.id.add_members);
        spinner_area = (Spinner) view.findViewById(R.id.spinner_area);
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, areaName);
        spinner_area.setAdapter(arrayAdapter);
        add_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.add_member_container, new Add_Team_View())
                        .addToBackStack(null)
                        .commit();
            }
        });

        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        return view;
    }
}
