package com.example.ali.myapplication.Activities.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ali.myapplication.Activities.UI.Form_List_Uc;
import com.example.ali.myapplication.R;

public class UcHome extends AppCompatActivity {
    Button listUc;
    public Button uc_form_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uc_home);
        listUc = (Button) findViewById(R.id.ucList);
        uc_form_list = (Button)findViewById(R.id.uc_form_list);
        listUc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     listUc.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_uc, new Form_List_Uc()).addToBackStack(null).commit();
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
