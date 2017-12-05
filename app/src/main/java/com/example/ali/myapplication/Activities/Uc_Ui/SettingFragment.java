package com.example.ali.myapplication.Activities.Uc_Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ali.myapplication.R;

/**
 * Created by Sami Khan on 12/5/2017.
 */

public class SettingFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.setting_frag,null);


        return view;
    }
}
