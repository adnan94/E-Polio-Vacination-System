package com.example.ali.myapplication.Activities.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ali.myapplication.Activities.UI.Form_List;
import com.example.ali.myapplication.R;

public class UcHome extends AppCompatActivity {
    Button listUc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uc_home);
        listUc = (Button) findViewById(R.id.ucList);
        listUc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     listUc.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().add(R.id.maincontainer_uc, new Form_List()).addToBackStack(null).commit();
            }
        });
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
