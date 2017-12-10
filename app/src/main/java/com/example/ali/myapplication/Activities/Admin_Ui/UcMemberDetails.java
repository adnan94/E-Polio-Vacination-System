package com.example.ali.myapplication.Activities.Admin_Ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ali.myapplication.Activities.ModelClasses.Polio_Team;
import com.example.ali.myapplication.Activities.ModelClasses.Team_MemberObject;
import com.example.ali.myapplication.Activities.ModelClasses.UC_Object;
import com.example.ali.myapplication.Activities.Uc_Ui.Add_Team_View;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sami Khan on 12/8/2017.
 */

public class UcMemberDetails extends Fragment {

    public CircleImageView uc_member_image;
    public TextView uc_member_name;
    public TextView uc_member_email;
    public TextView uc_member_nic;
    public TextView uc_member_phone;
    public TextView uc_member_area;
    public ImageButton uc_list_sort;
    public ImageView edit_uc_details;
    public ImageView delete_uc_details;
    public UC_Object uc_object;
    public LinearLayout polio_team_container;
    public ImageView back_arrow;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_uc_member,null);
        initViews(view);

        if(getArguments()!=null){
          uc_object  =   getArguments().getParcelable("obj");
            uc_member_name.setText(uc_object.getUc_membern());
            uc_member_email.setText(uc_object.getUc_member_email());
            uc_member_phone.setText(uc_object.getUc_member_phone());
            uc_member_area.setText(uc_object.getUc_area());
            uc_member_nic.setText(uc_object.getUc_member_cnic());

            Glide.with(getActivity())
                    .load(uc_object.getUc_member_piture())
                    .asBitmap()
                    .placeholder(R.drawable.user)
                    .into(uc_member_image);

        }

        FirebaseHandler.getInstance()
                .getUc_teams()
                .child(uc_object.getUc_member_uid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null){
                            if(dataSnapshot.getValue()!=null){
                                polio_team_container.removeAllViews();
                               for(DataSnapshot data:dataSnapshot.getChildren()){
                                   Polio_Team polio_team = data.getValue(Polio_Team.class);
                                    addLayout(polio_team);
                               }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        edit_uc_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),Add_UC_Activity.class);
                i.putExtra("obj",uc_object);
                startActivity(i);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        return view;
    }

    private void initViews(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        back_arrow.setVisibility(View.INVISIBLE);
        uc_member_image = (CircleImageView) view.findViewById(R.id.uc_member_image);
        uc_member_name = (TextView) view.findViewById(R.id.uc_member_name);
        uc_member_phone = (TextView)view.findViewById(R.id.uc_member_phone);
        uc_member_nic = (TextView)view.findViewById(R.id.uc_member_nic);
        uc_member_email = (TextView)view.findViewById(R.id.uc_member_email);
        uc_member_area = (TextView)view.findViewById(R.id.uc_member_area);
        uc_list_sort = (ImageButton) view.findViewById(R.id.uc_list_sort);
        edit_uc_details = (ImageView)view.findViewById(R.id.edit_uc_details);
        delete_uc_details = (ImageView)view.findViewById(R.id.delete_uc_details);
        polio_team_container= (LinearLayout)view.findViewById(R.id.polio_team_container);

        uc_list_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAnimations();
            }
        });
    }
    public void customAnimations() {
        if (edit_uc_details.getVisibility() == View.VISIBLE) {
            implementOnCloseAnimations();
        } else {
            implentOnOpenAnimations();
        }

    }


    private void implentOnOpenAnimations() {
        ObjectAnimator slideInAnimation = ObjectAnimator.ofFloat(edit_uc_details, "translationY", 200, 0);
        slideInAnimation.setDuration(200);
        ObjectAnimator fadeInAnimatin = ObjectAnimator.ofFloat(edit_uc_details, "alpha", 0, 1);
        fadeInAnimatin.setDuration(200);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(edit_uc_details, "scaleX", 1f);
        scaleAnimationX.setDuration(200);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(edit_uc_details, "scaleY", 1f);
        scaleAnimationY.setDuration(200);

        ObjectAnimator slideInAnimation2 = ObjectAnimator.ofFloat(delete_uc_details, "translationY", 200, 0);
        slideInAnimation2.setDuration(200);
        ObjectAnimator fadeInAnimation2 = ObjectAnimator.ofFloat(delete_uc_details, "alpha", 0, 1);
        fadeInAnimation2.setDuration(200);
        ObjectAnimator scaleAnimation2X = ObjectAnimator.ofFloat(delete_uc_details, "scaleX", 1f);
        scaleAnimation2X.setDuration(200);
        ObjectAnimator scaleAnimation2Y = ObjectAnimator.ofFloat(delete_uc_details, "scaleY", 1f);
        scaleAnimation2Y.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideInAnimation).with(fadeInAnimatin).with(scaleAnimationX).with(scaleAnimationY).with(slideInAnimation2).with(fadeInAnimation2).with(scaleAnimation2X).with(scaleAnimation2Y);


        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                edit_uc_details.setVisibility(View.VISIBLE);
                delete_uc_details.setVisibility(View.VISIBLE);
             //   clist_back_view.setVisibility(View.VISIBLE);
                uc_list_sort.setBackgroundResource(R.mipmap.closed);

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
        ObjectAnimator slideOutAnimation = ObjectAnimator.ofFloat(edit_uc_details, "translationY", 0, 200);
        slideOutAnimation.setDuration(200);
        ObjectAnimator fadeOutAnimation = ObjectAnimator.ofFloat(edit_uc_details, "alpha", 1, 0);
        fadeOutAnimation.setDuration(200);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(edit_uc_details, "scaleX", 0.5f);
        scaleAnimationX.setDuration(200);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(edit_uc_details, "scaleY", 0.5f);
        scaleAnimationY.setDuration(200);

        ObjectAnimator slideOutAnimation2 = ObjectAnimator.ofFloat(delete_uc_details, "translationY", 0, 200);
        slideOutAnimation2.setDuration(200);
        ObjectAnimator fadeOutAnimation2 = ObjectAnimator.ofFloat(delete_uc_details, "alpha", 1, 0);
        fadeOutAnimation2.setDuration(200);
        ObjectAnimator scaleAnimation2X = ObjectAnimator.ofFloat(delete_uc_details, "scaleX", 0.5f);
        scaleAnimation2X.setDuration(200);
        ObjectAnimator scaleAnimation2Y = ObjectAnimator.ofFloat(delete_uc_details, "scaleY", 0.5f);
        scaleAnimation2Y.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideOutAnimation).with(fadeOutAnimation).with(scaleAnimationX).with(scaleAnimationY).with(slideOutAnimation2).with(fadeOutAnimation2).with(scaleAnimation2X).with(scaleAnimation2Y);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                edit_uc_details.setVisibility(View.GONE);
                delete_uc_details.setVisibility(View.GONE);
               // clist_back_view.setVisibility(View.GONE);
                uc_list_sort.setBackgroundResource(R.mipmap.sort_btn);
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

    private void addLayout( Polio_Team team_memberObject) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(getActivity());

        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.polio_team_details, null, false);
        polio_team_container.addView(layout);
        TextView member_name = (TextView) layout.findViewById(R.id.member_name);
        TextView member_email = (TextView) layout.findViewById(R.id.member_email);
        TextView member_nic = (TextView) layout.findViewById(R.id.member_area);
        TextView member_phone = (TextView) layout.findViewById(R.id.team_status);
        ImageView member_picture = (ImageView)layout.findViewById(R.id.member_picture);
        layout.setTag(team_memberObject);

      //  String split[] = team_memberObject.getMember_email().split("team");

        member_name.setText(team_memberObject.getTeam_name());
        member_email.setText(team_memberObject.getTeam_email());
        member_nic.setText(team_memberObject.getTeam_area());
        member_phone.setText(team_memberObject.getTeam_status());


        Glide.with(getActivity())
                .load(team_memberObject.getTeam_image())
                .asBitmap()
                .placeholder(R.mipmap.sehat_logo)
                .into(member_picture);



    }
}
