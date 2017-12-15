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
import com.example.ali.myapplication.Activities.Adaptor.Slider_Pager_Adaptor;
import com.example.ali.myapplication.Activities.Admin_Ui.Add_UC_Activity;
import com.example.ali.myapplication.Activities.Admin_Ui.ListOfUc;
import com.example.ali.myapplication.Activities.Admin_Ui.Monitoring_Fragment;
import com.example.ali.myapplication.Activities.Admin_Ui.PolioSchedule;
import com.example.ali.myapplication.Activities.Admin_Ui.Polio_FormList;
import com.example.ali.myapplication.Activities.Uc_Ui.Add_Polio_TeamActivity;
import com.example.ali.myapplication.Activities.User_Ui.UserHomeFragment;
import com.example.ali.myapplication.Activities.Utils.ViewPagerCustomDuration;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzagy.runOnUiThread;

public class AdminHome extends AppCompatActivity {
    //  Button listUc;
    public DrawerLayout drawer_layout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public String[] menuName = {"Home", "List Of Uc", "Polio Forms", "Add UC Member", "Add Polio Schedule", "Monitor Polio Team", "Logout"};
    public int a[]={R.drawable.home,R.drawable.list,R.drawable.view_token,R.drawable.businessman,R.drawable.calendar,
            R.drawable.group,R.drawable.logout};
    public ImageView back_arrow;
    ViewPagerCustomDuration viewPager;
    Slider_Pager_Adaptor slider_pager_adaptor;
    public static TextView ActionBartitle;
    public RelativeLayout maincontainer_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        //    listUc = (Button) findViewById(R.id.ucList);
        slider();
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
        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(AdminHome.this, menuName, a);
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
                } else if (i == 7) {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 4) {
                    Intent intent = new Intent(AdminHome.this, Add_UC_Activity.class);
                    startActivity(intent);
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 6) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_admin, new Monitoring_Fragment()).addToBackStack(null).commit();
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 5) {

                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_admin, new PolioSchedule()).addToBackStack(null).commit();
                    // finish();
                    drawer_layout.closeDrawer(mDrawerList);
                }
            }
        });
    }

    private void slider() {

        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.mipmap.one);
        list.add(R.mipmap.six);
        list.add(R.mipmap.two);
        list.add(R.mipmap.five);
        viewPager = (ViewPagerCustomDuration) findViewById(R.id.viewPager);
        viewPager.setScrollDuration(2000);
        slider_pager_adaptor = new Slider_Pager_Adaptor(AdminHome.this, list);
        viewPager.setAdapter(slider_pager_adaptor);
        viewPager.setOffscreenPageLimit(2);
        pageSwitcher(5, list.size());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // getSupportFragmentManager().popBackStack();

    }

    Timer timer;
    int page = 0;

    public void pageSwitcher(int seconds, int pages) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(pages), 0, seconds * 1000); // delay

    }

    class RemindTask extends TimerTask {
        int pages;
        ArrayList list;

        public RemindTask(int pages) {
            this.pages = pages;
            this.list = list;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (page > pages) {
                        page = 0;
                    }
                    viewPager.setCurrentItem(page++);

                }
            });

        }
    }
}
