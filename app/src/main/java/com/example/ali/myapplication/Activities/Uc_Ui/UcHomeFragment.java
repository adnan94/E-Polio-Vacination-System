package com.example.ali.myapplication.Activities.Uc_Ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ali.myapplication.Activities.Uc_Ui.Form_List_Uc;
import com.example.ali.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UcHomeFragment extends Fragment {
    Button listUc;
    public Button Add_teams;

    public UcHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_uc_home, container, false);
        listUc = (Button) view.findViewById(R.id.ucList);
        Add_teams = (Button)view.findViewById(R.id.Add_teams);
        listUc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //     listUc.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_uc, new Form_List_Uc()).addToBackStack(null).commit();
            }
        });

        return view;
    }

}
