package com.example.ali.myapplication.Activities.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.Adaptor.Navigations_ItemsAdapter;
import com.example.ali.myapplication.Activities.Admin_Ui.Add_UC_Activity;
import com.example.ali.myapplication.Activities.Admin_Ui.ListOfUc;
import com.example.ali.myapplication.Activities.Admin_Ui.Monitoring_Fragment;
import com.example.ali.myapplication.Activities.Admin_Ui.Polio_FormList;
import com.example.ali.myapplication.Activities.Uc_Ui.Add_Polio_TeamActivity;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity {
    //  Button listUc;
    public DrawerLayout drawer_layout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public String[] menuName = {"Home", "List Of Uc", "Polio Forms", "Add UC Member", "Monitor Polio Team", "Logout"};
    public ImageView back_arrow;
    public static TextView ActionBartitle;
    public RelativeLayout maincontainer_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        //    listUc = (Button) findViewById(R.id.ucList);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_outside);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        //  back_arrow.setVisibility(View.INVISIBLE);
        back_arrow.setImageResource(R.mipmap.menu);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Admin Home");

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        maincontainer_admin = (RelativeLayout) findViewById(R.id.maincontainer_admin);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        View viewinflate = getLayoutInflater().inflate(R.layout.nav_header_main, null);
        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(AdminHome.this, menuName, null);
        mDrawerList.setAdapter(navigations_itemsAdapter);
        mDrawerList.addHeaderView(viewinflate);

        mDrawerToggle = new ActionBarDrawerToggle(AdminHome.this, drawer_layout, null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                maincontainer_admin.setTranslationX(slideOffset * drawerView.getWidth());
                drawer_layout.bringChildToFront(drawerView);
                drawer_layout.requestLayout();
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);

        //  listUc.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View view) {
        //       listUc.setVisibility(View.GONE);
        //    }
        // });


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 1) {
                    getSupportFragmentManager().popBackStack(1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 2) {

                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_admin, new ListOfUc()).addToBackStack(null).commit();
                    // finish();
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 3) {
//                    Intent intent = new Intent(AdminHome.this, Add_Polio_TeamActivity.class);
//                    startActivity(intent);
//                    finish();
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_admin, new Polio_FormList()).addToBackStack(null).commit();
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 6) {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 4) {
                    Intent intent = new Intent(AdminHome.this, Add_UC_Activity.class);
                    startActivity(intent);
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 5) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_admin, new Monitoring_Fragment()).addToBackStack(null).commit();
                    drawer_layout.closeDrawer(mDrawerList);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //  getSupportFragmentManager().popBackStack();

    }
}
