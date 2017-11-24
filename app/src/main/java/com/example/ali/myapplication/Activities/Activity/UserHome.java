package com.example.ali.myapplication.Activities.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.ali.myapplication.Activities.Adaptor.Navigations_ItemsAdapter;
import com.example.ali.myapplication.Activities.User_Ui.UserHomeFragment;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserHome extends AppCompatActivity {

    Button form_btn_list, form_btn_details;
    public DrawerLayout drawer_layout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public LinearLayout customer_container;
    public String[] menuName = {"Change Password", "Terms & Conditions", "Send Complaint", "Add Form","Logout"};
    public ImageView back_arrow;
    public static TextView ActionBartitle;
  //  public LinearLayout e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_outside);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        //  back_arrow.setVisibility(View.INVISIBLE);
        back_arrow.setImageResource(R.mipmap.menu);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("User Dashboard");
        getSupportFragmentManager().beginTransaction().add(R.id.maincontainer, new UserHomeFragment()).commit();

        customer_container = (LinearLayout) findViewById(R.id.maincontainer);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
      //  e = (LinearLayout)findViewById(R.id.e);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        View viewinflate = UserHome.this.getLayoutInflater().inflate(R.layout.nav_header_main, null);
        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(UserHome.this, menuName, null);
        mDrawerList.setAdapter(navigations_itemsAdapter);
        mDrawerList.addHeaderView(viewinflate);

        mDrawerToggle = new ActionBarDrawerToggle(UserHome.this, drawer_layout, null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                customer_container.setTranslationX(slideOffset * drawerView.getWidth());
                drawer_layout.bringChildToFront(drawerView);
                drawer_layout.requestLayout();
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){

                }else if(i==1){

                }
                else if(i==5){
                    FirebaseAuth.getInstance().signOut();
                    drawer_layout.closeDrawer(mDrawerList);
                }
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


