package com.example.ali.myapplication.Activities.Activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.ali.myapplication.Activities.UI.ListOfUc;
import com.example.ali.myapplication.R;

public class AdminHome extends AppCompatActivity {
    Button listUc;
    public DrawerLayout drawer_layout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public String[] menuName = {"Change Password", "Terms & Conditions", "Send Messages", "Add Customer", "Pay Bill Online"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        listUc = (Button) findViewById(R.id.ucList);



        listUc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         //       listUc.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().add(R.id.maincontainer_admin, new ListOfUc()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

      //  getSupportFragmentManager().popBackStack();

    }
}
