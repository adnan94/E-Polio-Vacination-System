package com.example.ali.myapplication.Activities.Uc_Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;

/**
 * Created by Sami Khan on 12/5/2017.
 */

public class SettingFragment extends android.support.v4.app.Fragment {

    public Toolbar tool_bar;
    public ImageView back_arrow;
    public TextView logout,txt_contactus,contact,txt_sId,support,txt_dName,display,txt_version,version;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.setting_frag,null);

        logout  = (TextView)view.findViewById(R.id.logout);
        txt_contactus = (TextView)view.findViewById(R.id.txt_contactus);
        contact = (TextView)view.findViewById(R.id.contact);
        txt_sId = (TextView)view.findViewById(R.id.txt_sId);
        support = (TextView)view.findViewById(R.id.support);
        txt_dName = (TextView)view.findViewById(R.id.txt_dName);
        display = (TextView)view.findViewById(R.id.display);
        txt_version = (TextView)view.findViewById(R.id.txt_version);
        version = (TextView)view.findViewById(R.id.version);


        Utils.relwayRegular(getActivity(),txt_version);
        Utils.relwayRegular(getActivity(),txt_sId);
        Utils.relwayRegular(getActivity(),txt_dName);
        Utils.relwayRegular(getActivity(),txt_contactus);

        Utils.relwaySemiBold(getActivity(),version);
        Utils.relwaySemiBold(getActivity(),logout);
        Utils.relwaySemiBold(getActivity(),display);
        Utils.relwaySemiBold(getActivity(),support);
        Utils.relwaySemiBold(getActivity(),contact);



       // contact = (TextView)view.findViewById(R.id.contact);

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
