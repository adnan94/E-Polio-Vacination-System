package com.example.ali.myapplication.Activities.Uc_Ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.Activity.UcHome;
import com.example.ali.myapplication.Activities.Adaptor.PolioTeam_list_Adapter;
import com.example.ali.myapplication.Activities.ModelClasses.Polio_Team;
import com.example.ali.myapplication.Activities.ModelClasses.UC_Object;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.SharedPref_UC;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sami Khan on 11/18/2017.
 */

public class Polio_TeamList extends android.support.v4.app.Fragment {

    public ListView polio_teamlist;
    public PolioTeam_list_Adapter polioTeam_list_adapter;
    public ArrayList<Polio_Team> polio_teams;
    public ImageView back_arrow,remove_search;
    public static TextView ActionBartitle;
//    public FloatingActionButton add_teams;
    public ImageView floatingActionButton;
    public ImageView floatingMenuItem1;
    public ImageView floatingMenuItem2;
    public View clist_back_view;
    public UC_Object uc_object;
    public EditText team_list_search;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.polio_teamlist,null);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
      //  back_arrow.setVisibility(View.GONE);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
     //   add_teams = (FloatingActionButton)view.findViewById(R.id.add_teams);
        ActionBartitle.setText("Polio Team List");

        Utils.relwayMedium(getActivity(),ActionBartitle);
        team_list_search = (EditText)view.findViewById(R.id.team_list_search);
        Utils.relwayRegular(getActivity(),team_list_search);

        floatingActionButton = (ImageView) view.findViewById(R.id.customer_list_sort);
        floatingMenuItem1 = (ImageView)view.findViewById(R.id.floatingMenuItem1);
        floatingMenuItem2 = (ImageView)view.findViewById(R.id.floatingMenuItem2);
       // customer_list = (ListView)view.findViewById(R.id.customer_list);
        remove_search = (ImageView)view.findViewById(R.id.remove_search);
        polio_teamlist = (ListView)view.findViewById(R.id.polio_teamlist);
        clist_back_view = (View)view.findViewById(R.id.clist_back_view);
        polio_teams = new ArrayList<>();
       polioTeam_list_adapter = new PolioTeam_list_Adapter(polio_teams,getActivity());
        polio_teamlist.setAdapter(polioTeam_list_adapter);
        uc_object = SharedPref_UC.getCurrentUser(getActivity());
        FirebaseHandler.getInstance().getUc_teams().child(uc_object.getUc_member_uid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                     if(dataSnapshot!=null){
                         if(dataSnapshot.getValue()!=null){
                             for(DataSnapshot data:dataSnapshot.getChildren()){
                                 Polio_Team polio_team = data.getValue(Polio_Team.class);
                                 polio_teams.add(polio_team);
                                 polioTeam_list_adapter.notifyDataSetChanged();
                             }
                         }
                     }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UcHome.class);
                startActivity(intent);
              getActivity().finish();
            }
        });

        team_list_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                 if(team_list_search.getText().toString().length() >0){
                     polioTeam_list_adapter.filterCustomer(team_list_search.getText().toString());
                 }
            }
        });


        polio_teamlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Polio_TeamDetail polio_teamDetail = new Polio_TeamDetail();

                Bundle bundle = new Bundle();
                bundle.putParcelable("obj", polio_teams.get(i));

                polio_teamDetail.setArguments(bundle);
               getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.add_member_container, polio_teamDetail)
                        .addToBackStack(null)
                        .commit();
            }
        });



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAnimations();
            }
        });

        floatingMenuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAnimations();
                Add_PolioTeam_Fragment add_polioTeam_fragment = new Add_PolioTeam_Fragment();
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.add_member_container, add_polioTeam_fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }

    public void customAnimations() {
        if (floatingMenuItem1.getVisibility() == View.VISIBLE) {
            implementOnCloseAnimations();
        } else {
            implentOnOpenAnimations();
        }

    }


    private void implentOnOpenAnimations() {
        ObjectAnimator slideInAnimation = ObjectAnimator.ofFloat(floatingMenuItem1, "translationY", 200, 0);
        slideInAnimation.setDuration(200);
        ObjectAnimator fadeInAnimatin = ObjectAnimator.ofFloat(floatingMenuItem1, "alpha", 0, 1);
        fadeInAnimatin.setDuration(200);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleX", 1f);
        scaleAnimationX.setDuration(200);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleY", 1f);
        scaleAnimationY.setDuration(200);

        ObjectAnimator slideInAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "translationY", 200, 0);
        slideInAnimation2.setDuration(200);
        ObjectAnimator fadeInAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "alpha", 0, 1);
        fadeInAnimation2.setDuration(200);
        ObjectAnimator scaleAnimation2X = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleX", 1f);
        scaleAnimation2X.setDuration(200);
        ObjectAnimator scaleAnimation2Y = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleY", 1f);
        scaleAnimation2Y.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideInAnimation).with(fadeInAnimatin).with(scaleAnimationX).with(scaleAnimationY).with(slideInAnimation2).with(fadeInAnimation2).with(scaleAnimation2X).with(scaleAnimation2Y);


        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                floatingMenuItem1.setVisibility(View.VISIBLE);
                floatingMenuItem2.setVisibility(View.VISIBLE);
                clist_back_view.setVisibility(View.VISIBLE);
                floatingActionButton.setBackgroundResource(R.mipmap.closed);

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }

    private void implementOnCloseAnimations() {
        ObjectAnimator slideOutAnimation = ObjectAnimator.ofFloat(floatingMenuItem1, "translationY", 0, 200);
        slideOutAnimation.setDuration(200);
        ObjectAnimator fadeOutAnimation = ObjectAnimator.ofFloat(floatingMenuItem1, "alpha", 1, 0);
        fadeOutAnimation.setDuration(200);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleX", 0.5f);
        scaleAnimationX.setDuration(200);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleY", 0.5f);
        scaleAnimationY.setDuration(200);

        ObjectAnimator slideOutAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "translationY", 0, 200);
        slideOutAnimation2.setDuration(200);
        ObjectAnimator fadeOutAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "alpha", 1, 0);
        fadeOutAnimation2.setDuration(200);
        ObjectAnimator scaleAnimation2X = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleX", 0.5f);
        scaleAnimation2X.setDuration(200);
        ObjectAnimator scaleAnimation2Y = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleY", 0.5f);
        scaleAnimation2Y.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideOutAnimation).with(fadeOutAnimation).with(scaleAnimationX).with(scaleAnimationY).with(slideOutAnimation2).with(fadeOutAnimation2).with(scaleAnimation2X).with(scaleAnimation2Y);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                floatingMenuItem1.setVisibility(View.GONE);
                floatingMenuItem2.setVisibility(View.GONE);
                clist_back_view.setVisibility(View.GONE);
                floatingActionButton.setBackgroundResource(R.mipmap.sort_btn);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }
}
