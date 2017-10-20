package com.example.ali.myapplication.Activities.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ali.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfUc extends android.support.v4.app.Fragment {


    public ListOfUc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_uc, container, false);
    }

}
