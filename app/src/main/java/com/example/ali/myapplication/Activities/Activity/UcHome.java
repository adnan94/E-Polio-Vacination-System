package com.example.ali.myapplication.Activities.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ali.myapplication.Activities.UI.Form_List_Uc;
import com.example.ali.myapplication.Activities.UI.UcHomeFragment;
import com.example.ali.myapplication.Activities.UI.UserHomeFragment;
import com.example.ali.myapplication.R;

public class UcHome extends AppCompatActivity {
    Button listUc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uc_home);
        getSupportFragmentManager().beginTransaction().add(R.id.maincontainer_uc, new UcHomeFragment()).commit();



    }

    @Override
    protected void onRestart() {
     //   listUc.setVisibility(View.VISIBLE);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
     //   listUc.setVisibility(View.VISIBLE);

    }
}
