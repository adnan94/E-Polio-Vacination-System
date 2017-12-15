package com.example.ali.myapplication.Activities.Uc_Ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.ali.myapplication.Activities.Activity.LoginActivity;
import com.example.ali.myapplication.Activities.Activity.UcHome;
import com.example.ali.myapplication.Activities.Adaptor.Navigations_ItemsAdapter;
import com.example.ali.myapplication.Activities.ModelClasses.UC_Object;
import com.example.ali.myapplication.Activities.Utils.SharedPref_UC;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class Add_Polio_TeamActivity extends AppCompatActivity {
 //   public DrawerLayout drawer_layout;
 //   public ListView mDrawerList;
 //   private ActionBarDrawerToggle mDrawerToggle;
 //   public LinearLayout add_teamcontainer_uc;
  //  public String[] menuName = {"Home", "Polio Teams", "About", "Setting", "Log Out"};
  //  public int a[]={R.drawable.home,R.drawable.terms,R.drawable.view_token
  //          ,R.drawable.settingss,R.drawable.logout};
    public Spinner spinner_area;
    public LinearLayout add_members;
    public String[] areaName = {"Nazimabad", "Johar", "Gulshan", "Landi", "Malir"};
    public ArrayAdapter arrayAdapter;
    public RelativeLayout team_container;
    public ImageView back_arrow;
    public static TextView ActionBartitle;
    public static Add_Polio_TeamActivity add_polio_teamActivity;

    public static Add_Polio_TeamActivity getInstance(){
        return add_polio_teamActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__polio__team);
        add_polio_teamActivity = this;
        team_container = (RelativeLayout) findViewById(R.id.team_container);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_outside);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
     //   back_arrow.setVisibility(View.VISIBLE);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Add Polio Team");
   //     drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
      //  team_container = (RelativeLayout) findViewById(R.id.team_container);
     //   mDrawerList = (ListView) findViewById(R.id.left_drawer);
     //   View viewinflate = Add_Polio_TeamActivity.this.getLayoutInflater().inflate(R.layout.nav_header_main, null);
    //    Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(Add_Polio_TeamActivity.this, menuName, a);
   //     mDrawerList.setAdapter(navigations_itemsAdapter);
    //    mDrawerList.addHeaderView(viewinflate);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        mDrawerToggle = new ActionBarDrawerToggle(Add_Polio_TeamActivity.this, drawer_layout, null, R.string.app_name, R.string.app_name) {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                super.onDrawerSlide(drawerView, slideOffset);
//                team_container.setTranslationX(slideOffset * drawerView.getWidth());
//                drawer_layout.bringChildToFront(drawerView);
//                drawer_layout.requestLayout();
//            }
//        };
//        drawer_layout.setDrawerListener(mDrawerToggle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.add_member_container, new Polio_TeamList())
              //  .addToBackStack(null)
                .commit();



//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 1) {
//                    Intent intent = new Intent(Add_Polio_TeamActivity.this, UcHome.class);
//                    startActivity(intent);
//                    finish();
//                } else if (i == 2) {
//                    Intent intent = new Intent(Add_Polio_TeamActivity.this, Add_Polio_TeamActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else if (i == 3) {
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .add(R.id.add_member_container, new About_Fragment())
//                            //  .addToBackStack(null)
//                            .commit();
//                }
//                else if (i ==5) {
//                    UC_Object uc_object = new UC_Object("","","","","","","");
//                    SharedPref_UC.setCurrentUser(Add_Polio_TeamActivity.this,uc_object);
//                    Intent intent = new Intent(Add_Polio_TeamActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });

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
