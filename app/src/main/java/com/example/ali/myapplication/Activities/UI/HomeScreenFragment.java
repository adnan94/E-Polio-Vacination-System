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
public class HomeScreenFragment extends Fragment {

    Button form_btn_details, form_btn_list;

    public HomeScreenFragment() {
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
                //    if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 1) {
                //    }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new Add_form()).addToBackStack(null).commit();
//                form_btn_details.setVisibility(View.GONE);
            }
        });
        form_btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 1) {
//                    getActivity().getSupportFragmentManager().popBackStack();
//                }
////                form_btn_list.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new Form_List()).addToBackStack(null).commit();

            }
        });

    }

}
