package com.example.ali.myapplication.Activities.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ali.myapplication.Activities.UI.Form_Detail;
import com.example.ali.myapplication.Activities.UI.ListOfUc;
import com.example.ali.myapplication.R;

public class AdminHome extends AppCompatActivity {
Button listUc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        listUc=(Button)findViewById(R.id.ucList);
        listUc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new ListOfUc()).addToBackStack(null).commit();
            }
        });
    }
}
