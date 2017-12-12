package com.example.ali.myapplication.Activities.User_Ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ali.myapplication.Activities.Adaptor.Slider_Pager_Adaptor;
import com.example.ali.myapplication.Activities.Utils.ViewPagerCustomDuration;
import com.example.ali.myapplication.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzagy.runOnUiThread;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserHomeFragment extends Fragment {

    Button form_btn_details, form_btn_list;
    ViewPagerCustomDuration viewPager;
    Slider_Pager_Adaptor slider_pager_adaptor;

    public UserHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        castAndClicks(view);
        return view;
    }

    private void castAndClicks(View view) {
        form_btn_details = (Button) view.findViewById(R.id.form_btn);
        form_btn_list = (Button) view.findViewById(R.id.form_btn_list);
        viewPager = (ViewPagerCustomDuration) view.findViewById(R.id.viewPager);
        viewPager.setScrollDuration(2000);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.mipmap.one);
        list.add(R.mipmap.six);
        list.add(R.mipmap.two);
        list.add(R.mipmap.five);
        slider_pager_adaptor = new Slider_Pager_Adaptor(getActivity(), list);
        viewPager.setAdapter(slider_pager_adaptor);
        viewPager.setOffscreenPageLimit(2);
        pageSwitcher(5, list.size());

        form_btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new Add_form()).addToBackStack(null).commit();
            }
        });
        form_btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new UserFormListScreen()).addToBackStack(null).commit();

            }
        });

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
