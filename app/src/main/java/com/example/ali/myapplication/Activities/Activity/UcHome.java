package com.example.ali.myapplication.Activities.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;


import com.example.ali.myapplication.Activities.Adaptor.Form_PagerAdapter;
import com.example.ali.myapplication.Activities.Adaptor.Navigations_ItemsAdapter;
import com.example.ali.myapplication.Activities.UI.Applied_Forms;
import com.example.ali.myapplication.Activities.UI.Completed_Forms;
import com.example.ali.myapplication.Activities.UI.In_Progress_Forms;
import com.example.ali.myapplication.R;

import java.util.ArrayList;

public class UcHome extends AppCompatActivity {
    Button listUc;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<Fragment> fragments;
    Button uc_form_list;
    public Applied_Forms applied_forms;
    public In_Progress_Forms in_progress_forms;
    public Completed_Forms completed_forms;
    Button form_btn_list, form_btn_details;
    public DrawerLayout drawer_layout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public FrameLayout maincontainer_uc;
    public String[] menuName = {"Change Password", "Terms & Conditions", "Send Messages", "Add Polio Teams","Log Out"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uc_home);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        maincontainer_uc = (FrameLayout)findViewById(R.id.maincontainer_uc);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        View viewinflate = UcHome.this.getLayoutInflater().inflate(R.layout.nav_header_main, null);
        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(UcHome.this, menuName, null);
        mDrawerList.setAdapter(navigations_itemsAdapter);
        mDrawerList.addHeaderView(viewinflate);

        mDrawerToggle = new ActionBarDrawerToggle(UcHome.this, drawer_layout, null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                maincontainer_uc.setTranslationX(slideOffset * drawerView.getWidth());
                drawer_layout.bringChildToFront(drawerView);
                drawer_layout.requestLayout();
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        fragments = new ArrayList<>();
        applied_forms = new Applied_Forms();
        in_progress_forms = new In_Progress_Forms();
        completed_forms = new Completed_Forms();

        fragments.add(applied_forms);
        fragments.add(in_progress_forms);
        fragments.add(completed_forms);


        tabLayout.addTab(tabLayout.newTab().setText("Applied"));
        tabLayout.addTab(tabLayout.newTab().setText("In-Progress"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));


         Form_PagerAdapter adapter = new Form_PagerAdapter(getSupportFragmentManager(), fragments);
        //is line se tablayout k neche jo shade araaha hai woh change hoga pageviewer k mutabik
        viewPager.setAdapter(adapter);
        // viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

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
