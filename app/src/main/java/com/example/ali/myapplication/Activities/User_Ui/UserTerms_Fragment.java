package com.example.ali.myapplication.Activities.User_Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.Activity.UcHome;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;

/**
 * Created by Sami Khan on 12/2/2017.
 */

public class UserTerms_Fragment extends android.support.v4.app.Fragment {

    public Button terms_and_cond_btn;
    public ImageView back_image;
    public TextView terms_heading;
    public TextView para_one;
    public TextView heading_two;
    public TextView para_two;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_terms,null);

     ///   back_image = (ImageView)view.findViewById(R.id.back_image);
        terms_and_cond_btn = (Button)view.findViewById(R.id.terms_and_cond_btn);
        terms_heading = (TextView)view.findViewById(R.id.terms_heading);
        para_one = (TextView)view.findViewById(R.id.para_one);
        heading_two = (TextView)view.findViewById(R.id.heading_two);
        para_two = (TextView)view.findViewById(R.id.para_two);

        Utils.relwayBold(getActivity(),terms_heading);
        Utils.relwayLight(getActivity(),para_one);
        Utils.relwayLight(getActivity(),para_two);
        Utils.relwaySemiBold(getActivity(),heading_two);
        Utils.relwayRegular(getActivity(),terms_and_cond_btn);


        terms_and_cond_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

//        back_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().popBackStack();
//                Intent intent = new Intent(getActivity(), UcHome.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });

        return view;
    }
}
