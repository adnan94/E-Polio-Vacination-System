package com.example.ali.myapplication.Activities.Uc_Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.myapplication.R;

/**
 * Created by Sami Khan on 12/16/2017.
 */

public class UcSetting_Fragment extends android.support.v4.app.Fragment {

    public TextView ActionBartitle;
    public ImageView back_arrow;
    public Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.uc_setting,null);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        back_arrow.setVisibility(View.GONE);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        //   add_teams = (FloatingActionButton)view.findViewById(R.id.add_teams);
        ActionBartitle.setText("Settings");

        return view;
    }
}
