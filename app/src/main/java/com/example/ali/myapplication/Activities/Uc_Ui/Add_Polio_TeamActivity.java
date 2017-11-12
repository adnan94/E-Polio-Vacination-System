package com.example.ali.myapplication.Activities.Uc_Ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.Activity.UcHome;
import com.example.ali.myapplication.Activities.Adaptor.Navigations_ItemsAdapter;
import com.example.ali.myapplication.R;

public class Add_Polio_TeamActivity extends AppCompatActivity {
    public DrawerLayout drawer_layout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public LinearLayout add_teamcontainer_uc;
    public String[] menuName = {"Home", "Add Polio Teams", "View Polio Teams", "About", "Setting", "Log Out"};
    public Spinner spinner_area;
    public LinearLayout add_members;
    public String[] areaName = {"Nazimabad", "Johar", "Gulshan", "Landi", "Malir"};
    public ArrayAdapter arrayAdapter;
    public RelativeLayout team_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__polio__team);
        team_container = (RelativeLayout) findViewById(R.id.team_container);


        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
      //  team_container = (RelativeLayout) findViewById(R.id.team_container);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        View viewinflate = Add_Polio_TeamActivity.this.getLayoutInflater().inflate(R.layout.nav_header_main, null);
        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(Add_Polio_TeamActivity.this, menuName, null);
        mDrawerList.setAdapter(navigations_itemsAdapter);
        mDrawerList.addHeaderView(viewinflate);

        mDrawerToggle = new ActionBarDrawerToggle(Add_Polio_TeamActivity.this, drawer_layout, null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                team_container.setTranslationX(slideOffset * drawerView.getWidth());
                drawer_layout.bringChildToFront(drawerView);
                drawer_layout.requestLayout();
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.add_member_container, new Add_PolioTeam_Fragment())
              //  .addToBackStack(null)
                .commit();



        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    Intent intent = new Intent(Add_Polio_TeamActivity.this, UcHome.class);
                    startActivity(intent);
                    finish();
                } else if (i == 2) {
                    Intent intent = new Intent(Add_Polio_TeamActivity.this, Add_Polio_TeamActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {


        if(getSupportFragmentManager().getBackStackEntryCount()==0){
            Intent intent = new Intent(Add_Polio_TeamActivity.this, UcHome.class);
            startActivity(intent);
            finish();
        }else{
            super.onBackPressed();
        }
    }
}
