package com.example.ali.myapplication.Activities.Activity;

import android.content.Intent;
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
import com.example.ali.myapplication.Activities.Admin_Ui.ListOfUc;
import com.example.ali.myapplication.Activities.Uc_Ui.Add_Polio_TeamActivity;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity {
    Button listUc;
    public DrawerLayout drawer_layout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public String[] menuName = {"List Of Uc", "Polio Forms", "Complaint List", "Setting", "Logout"};
    public ImageView back_arrow;
    public static TextView ActionBartitle;
    public RelativeLayout maincontainer_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        listUc = (Button) findViewById(R.id.ucList);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_outside);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        //  back_arrow.setVisibility(View.INVISIBLE);
        back_arrow.setImageResource(R.mipmap.menu);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Admin Home");

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        maincontainer_admin = (RelativeLayout)findViewById(R.id.maincontainer_admin);
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
                getSupportFragmentManager().beginTransaction().add(R.id.maincontainer_admin, new ListOfUc()).addToBackStack(null).commit();
        //    }
       // });

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                 //   Intent intent = new Intent(AdminHome.this, UcHome.class);
                 //   startActivity(intent);
                 //   finish();
                 //   drawer_layout.closeDrawer(mDrawerList);
                } else if(i==2){
                  //  Intent intent = new Intent(AdminHome.this, Add_Polio_TeamActivity.class);
                  //  startActivity(intent);
                  //  finish();
                   // drawer_layout.closeDrawer(mDrawerList);
                }else if(i==5){
                    FirebaseAuth.getInstance().signOut();
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
