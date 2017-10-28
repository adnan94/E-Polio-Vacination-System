package com.example.ali.myapplication.Activities.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ali.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserHomeFragment extends Fragment {

    Button form_btn_details, form_btn_list;

    public UserHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        castAndClicks(view);
        return view;
    }

    private void castAndClicks(View view) {
        form_btn_details = (Button) view.findViewById(R.id.form_btn);
        form_btn_list = (Button) view.findViewById(R.id.form_btn_list);

        form_btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new Add_form()).addToBackStack(null).commit();
            }
        });
        form_btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new UserFormListScreen()).addToBackStack(null).commit();

            }
        });

    }

}
