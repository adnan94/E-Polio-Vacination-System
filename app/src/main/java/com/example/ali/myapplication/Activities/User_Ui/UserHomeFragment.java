package com.example.ali.myapplication.Activities.User_Ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.Activity.AdminHome;
import com.example.ali.myapplication.Activities.Activity.UserHome;
import com.example.ali.myapplication.Activities.Adaptor.Slider_Pager_Adaptor;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.Activities.Utils.ViewPagerCustomDuration;
import com.example.ali.myapplication.R;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzagy.runOnUiThread;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserHomeFragment extends Fragment {

    //    Button form_btn_details, form_btn_list;
    ViewPagerCustomDuration viewPager;
    Slider_Pager_Adaptor slider_pager_adaptor;
    LinearLayout addForm, viewForm, viewShedule, viewToken;
    public TextView view_schedule, add_forms, view_forms, view_tokens;

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
        addForm = (LinearLayout) view.findViewById(R.id.addForm);
        viewForm = (LinearLayout) view.findViewById(R.id.viewForm);
        viewShedule = (LinearLayout) view.findViewById(R.id.viewShedule);
        viewToken = (LinearLayout) view.findViewById(R.id.viewToken);
        view_schedule = (TextView) view.findViewById(R.id.view_schedule);
        view_forms = (TextView) view.findViewById(R.id.view_forms);
        view_tokens = (TextView) view.findViewById(R.id.view_tokens);
        add_forms = (TextView) view.findViewById(R.id.add_forms);

        Utils.relwayMedium(getActivity(), view_schedule);
        Utils.relwayMedium(getActivity(), view_forms);
        Utils.relwayMedium(getActivity(), view_tokens);
        Utils.relwayMedium(getActivity(), add_forms);


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

//        addForm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//                View vieww = layoutInflater.inflate(R.layout.alert_confirm_home, null);
//                Button button = (Button) vieww.findViewById(R.id.alertSubmit);
//                Button cancel = (Button) vieww.findViewById(R.id.alertCencel);
//
//                TextView title = (TextView) vieww.findViewById(R.id.alertTitleText);
//                TextView message = (TextView) vieww.findViewById(R.id.alertMessageText);
//                final EditText emailText = (EditText) vieww.findViewById(R.id.alertEditText);
//                button.setText("Confirm");
//                emailText.setVisibility(View.GONE);
//                title.setText("Information");
//                message.setText("Are you at your home ?");
//                alert.setView(vieww);
//
//                final AlertDialog alertDialog = alert.create();
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new Add_form()).addToBackStack(null).commit();
//                        alertDialog.dismiss();
//                    }
//
//                });
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                    }
//
//                });
//                alertDialog.show();
//
//            }
//        });
//        viewForm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new UserFormListScreen()).addToBackStack(null).commit();
//            }
//        });
//        viewToken.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        viewShedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        form_btn_details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//        form_btn_list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            //    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new UserFormListScreen()).addToBackStack(null).commit();
//
//            }
//        });

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
