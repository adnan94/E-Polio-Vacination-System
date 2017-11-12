package com.example.ali.myapplication.Activities.Uc_Ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ali.myapplication.R;

/**
 * Created by Sami Khan on 11/12/2017.
 */

public class Add_Team_View extends android.support.v4.app.Fragment {

    public Spinner team_member_type;
    public EditText team_mname;
    public EditText team_mnic_no;
    public EditText team_maddress;
    public EditText team_mphone_no;
    public String [] membership_type= {"Head Member","Member"};
    public ArrayAdapter memberShipAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_team_view,null);
        initializeView(view);






        return view;
    }

    private void initializeView(View view) {
        team_member_type = (Spinner)view.findViewById(R.id.team_member_type);
        team_mname = (EditText)view.findViewById(R.id.team_mname);
        team_mnic_no = (EditText)view.findViewById(R.id.team_mnic_no);
        team_maddress = (EditText)view.findViewById(R.id.team_maddress);
        team_mphone_no = (EditText)view.findViewById(R.id.team_mphone_no);

        memberShipAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, membership_type);
        team_member_type.setAdapter(memberShipAdapter);

        team_member_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    }
}
