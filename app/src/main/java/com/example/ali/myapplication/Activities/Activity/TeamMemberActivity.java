package com.example.ali.myapplication.Activities.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ali.myapplication.Activities.UI.TeamMemberHomeFragment;
import com.example.ali.myapplication.R;

public class TeamMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member);
        getSupportFragmentManager().beginTransaction().add(R.id.maincontainer, new TeamMemberHomeFragment()).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
