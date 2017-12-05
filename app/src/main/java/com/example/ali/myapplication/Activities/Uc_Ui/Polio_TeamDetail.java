package com.example.ali.myapplication.Activities.Uc_Ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ali.myapplication.Activities.ModelClasses.Polio_Team;
import com.example.ali.myapplication.Activities.ModelClasses.Team_MemberObject;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Sami Khan on 11/18/2017.
 */

public class Polio_TeamDetail extends android.support.v4.app.Fragment {

    public ImageView floatingActionButton;
    public ImageView floatingMenuItem1;
    public ImageView floatingMenuItem2,back_arrow;
    public View cdetail_back_view;
    public static TextView ActionBartitle;
    public String key;
    public Polio_Team polio_team;
    public View detail_view;
    public LinearLayout members_container;
    public TextView team_id,team_name,team_detail_email;
    public TextView team_area_detail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.politeam_details,null);
        initView(view);

        if(getArguments()!=null) {
            if (getArguments().getParcelable("obj") != null) {
                polio_team = getArguments().getParcelable("obj");
                team_name.setText(polio_team.getTeam_name());
                team_detail_email.setText(polio_team.getTeam_email());
                team_id.setText(String.valueOf(polio_team.getTeam_uid()));
                team_area_detail.setText(polio_team.getTeam_area());

            }
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAnimations();
            }
        });


        FirebaseHandler.getInstance().getPolio_teams()
                .child(polio_team.getTeam_uid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (getActivity() != null) {
                            if (dataSnapshot != null) {
                                if (dataSnapshot.getValue() != null) {
                                    members_container.removeAllViews();
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        Team_MemberObject team_memberObject = data.getValue(Team_MemberObject.class);
                                        addLayout(team_memberObject);
                                    }
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        detail_view = (View)view.findViewById(R.id.detail_view);
        cdetail_back_view = (View)view.findViewById(R.id.cdetail_back_view);
        members_container = (LinearLayout)view.findViewById(R.id.members_container);
        team_id = (TextView)view.findViewById(R.id.team_id);
        team_name = (TextView)view.findViewById(R.id.team_name);
        team_detail_email = (TextView)view.findViewById(R.id.team_detail_email);
        team_area_detail = (TextView)view.findViewById(R.id.team_area_detail);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        back_arrow.setVisibility(View.GONE);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        //   add_teams = (FloatingActionButton)view.findViewById(R.id.add_teams);
        ActionBartitle.setText("Polio Team Detail");
        floatingActionButton = (ImageView) view.findViewById(R.id.customer_team_sort);
        floatingMenuItem1 = (ImageView)view.findViewById(R.id.edit_team_details);
        floatingMenuItem2 = (ImageView)view.findViewById(R.id.delete_team_details);

        floatingMenuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
                Bundle bundle= new Bundle();
                bundle.putParcelable("obj",polio_team);
             //   bundle.putParcelable("member",team_memberObject);
                Add_PolioTeam_Fragment add_team_view = new Add_PolioTeam_Fragment();
                add_team_view.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.add_member_container, add_team_view)
                        .addToBackStack(null)
                        .commit();
            }
        });

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
                detail_view.setVisibility(View.VISIBLE);
                cdetail_back_view.setVisibility(View.VISIBLE);
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
                detail_view.setVisibility(View.GONE);
                cdetail_back_view.setVisibility(View.GONE);
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
    private void addLayout(Team_MemberObject team_memberObject) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(getActivity());

        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.member_container_view, null, false);
        members_container.addView(layout);
        TextView member_name = (TextView) layout.findViewById(R.id.member_name);
        TextView member_email = (TextView) layout.findViewById(R.id.member_email);
        TextView member_nic = (TextView) layout.findViewById(R.id.member_nic);
        TextView member_phone = (TextView) layout.findViewById(R.id.member_phone);
        layout.setTag(team_memberObject);

        String split[] = team_memberObject.getMember_email().split("team");

        member_name.setText(team_memberObject.getMember_name());
        member_email.setText(split[1]);
        member_nic.setText(team_memberObject.getMember_nic_no());
        member_phone.setText(team_memberObject.getMember_phone_no());
        ImageView member_picture = (ImageView)layout.findViewById(R.id.member_picture);


        Glide.with(getActivity())
                .load(team_memberObject.getMember_pic())
                .asBitmap()
                .placeholder(R.drawable.user)
                .into(member_picture);


//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AddSegment appartment_detail = new AddSegment();
//                Bundle bundle = new Bundle();
//                segmentObject = new Apartment(add_apart_name.getText().toString(),
//                        "",
//                        "", key);
//                bundle.putParcelable("object", segmentObject);
//                bundle.putParcelable("segment", (Parcelable) layout.getTag());
//                appartment_detail.setArguments(bundle);
//                //   getActivity().getSupportFragmentManager().popBackStack();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
//                        // .addToBackStack(null)
//                        .replace(R.id.profile_container, appartment_detail).commit();
//            }
//
//        });
//


    }
}
