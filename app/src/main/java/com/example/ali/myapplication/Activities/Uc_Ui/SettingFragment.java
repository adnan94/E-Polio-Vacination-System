package com.example.ali.myapplication.Activities.Uc_Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ali.myapplication.R;

/**
 * Created by Sami Khan on 12/5/2017.
 */

public class SettingFragment extends android.support.v4.app.Fragment {

    public Toolbar tool_bar;
    public ImageView back_arrow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.setting_frag,null);
//        tool_bar = (Toolbar)view.findViewById(R.id.tool_bar);
//        back_arrow = (ImageView)tool_bar.findViewById(R.id.back_image);
////
////        back_arrow.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                getActivity().getSupportFragmentManager().popBackStack();
////            }
////        });


        return view;
    }
}
