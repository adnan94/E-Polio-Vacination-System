package com.example.ali.myapplication.Activities.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ali.myapplication.Activities.UI.Form_Detail;
import com.example.ali.myapplication.Activities.UI.Form_List;
import com.example.ali.myapplication.R;

public class MainActivity extends AppCompatActivity {

    Button form_btn_list,form_btn_details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        form_btn_details = (Button)findViewById(R.id.form_btn);
        form_btn_list = (Button)findViewById(R.id.form_btn_list);

        form_btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.container,new Form_Detail()).addToBackStack(null).commit();
                form_btn_details.setVisibility(View.GONE);
            }
        });
        form_btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.container,new Form_List()).addToBackStack(null).commit();
                form_btn_list.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        form_btn_details.setVisibility(View.VISIBLE);
        form_btn_list.setVisibility(View.VISIBLE);

    }
}


