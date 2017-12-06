package com.example.ali.myapplication.Activities.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.Adaptor.Navigations_ItemsAdapter;
import com.example.ali.myapplication.Activities.ModelClasses.Team_MemberObject;
import com.example.ali.myapplication.Activities.ModelClasses.UC_Object;
import com.example.ali.myapplication.Activities.UI.Form_List_Team_Member;
import com.example.ali.myapplication.Activities.UI.TeamMemberHomeFragment;
import com.example.ali.myapplication.Activities.Uc_Ui.About_Fragment;
import com.example.ali.myapplication.Activities.Uc_Ui.Add_Polio_TeamActivity;
import com.example.ali.myapplication.Activities.Uc_Ui.Applied_Forms;
import com.example.ali.myapplication.Activities.Uc_Ui.Completed_Forms;
import com.example.ali.myapplication.Activities.Uc_Ui.In_Progress_Forms;
import com.example.ali.myapplication.Activities.Utils.Service;
import com.example.ali.myapplication.Activities.Utils.SharedPref_Team;
import com.example.ali.myapplication.Activities.Utils.SharedPref_UC;
import com.example.ali.myapplication.R;

import java.util.ArrayList;

public class TeamMemberActivity extends AppCompatActivity {
    Button listUc;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<Fragment> fragments;
    Button uc_form_list;
    public Applied_Forms applied_forms;
    public In_Progress_Forms in_progress_forms;
    public Completed_Forms completed_forms;
    Button form_btn_list, form_btn_details;
    public ListView mDrawerList;
    public FrameLayout maincontainer_uc;
    public DrawerLayout drawer_layout;
    public String[] menuName = {"Home", "View Forms", "Terms & Conditions", "Setting", "LogOut"};
    private ActionBarDrawerToggle mDrawerToggle;
    public static TextView ActionBartitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member);
        getSupportFragmentManager().beginTransaction().add(R.id.maincontainer, new TeamMemberHomeFragment()).addToBackStack(null).commit();
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        maincontainer_uc = (FrameLayout) findViewById(R.id.maincontainer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        View viewinflate = TeamMemberActivity.this.getLayoutInflater().inflate(R.layout.nav_header_main, null);
        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(TeamMemberActivity.this, menuName, null);
        mDrawerList.setAdapter(navigations_itemsAdapter);
        mDrawerList.addHeaderView(viewinflate);

        mDrawerToggle = new ActionBarDrawerToggle(TeamMemberActivity.this, drawer_layout, null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                maincontainer_uc.setTranslationX(slideOffset * drawerView.getWidth());
                drawer_layout.bringChildToFront(drawerView);
                drawer_layout.requestLayout();
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                  getSupportFragmentManager().popBackStack(1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 2) {
                   drawer_layout.closeDrawer(Gravity.LEFT);
                    getSupportFragmentManager().beginTransaction().add(R.id.maincontainer, new Form_List_Team_Member()).addToBackStack(null).commit();

                } else if (i == 5) {
                    Team_MemberObject uc_object = new Team_MemberObject("", "", "", "", "", "", "", "");
                    SharedPref_Team.setCurrentUser(TeamMemberActivity.this, uc_object);
                    Intent intent = new Intent(TeamMemberActivity.this, LoginActivity.class);
                    startActivity(intent);
                    stopService(new Intent(TeamMemberActivity.this, Service.class));
                    finish();

                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 4) {
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
