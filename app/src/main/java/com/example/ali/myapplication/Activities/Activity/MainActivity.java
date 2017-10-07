package com.example.ali.myapplication.Activities.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button form_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        form_btn = (Button)findViewById(R.id.form_btn);

        form_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.container,new Form_Detail()).addToBackStack(null).commit();
                form_btn.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        form_btn.setVisibility(View.VISIBLE);
    }
}


