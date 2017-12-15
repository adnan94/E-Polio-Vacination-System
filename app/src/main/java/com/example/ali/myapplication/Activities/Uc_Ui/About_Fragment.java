package com.example.ali.myapplication.Activities.Uc_Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.Activity.UcHome;
import com.example.ali.myapplication.R;

/**
 * Created by Sami Khan on 12/2/2017.
 */

public class About_Fragment extends android.support.v4.app.Fragment {

    public Button terms_and_cond_btn;
    public ImageView back_image;
    public TextView ActionBartitle;
    public ImageView back_arrow;
    public Toolbar toolbar_outside;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about_frag_view,null);
        toolbar_outside = (Toolbar)view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_outside);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar_outside.findViewById(R.id.back_image);
        back_arrow.setVisibility(View.GONE);
        ActionBartitle = (TextView) toolbar_outside.findViewById(R.id.main_appbar_textView);
        //   add_teams = (FloatingActionButton)view.findViewById(R.id.add_teams);
        ActionBartitle.setText("Terms & Conditions");
        back_image = (ImageView)view.findViewById(R.id.back_image);
        terms_and_cond_btn = (Button)view.findViewById(R.id.terms_and_cond_btn);

        terms_and_cond_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
                Intent intent = new Intent(getActivity(), UcHome.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
