package com.example.ali.myapplication.Activities.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;

public class MainActivity extends AppCompatActivity {
    EditText name, cnic, childName, relation, religion, fatherName, fatherCnic, motherName, motherCnic, areaOfBirth, dateOfBirth, disability, address, district;
    Button submit;
    CheckBox yes, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cast();
        clickListeners();
    }

    private void clickListeners() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void cast() {
        name = (EditText) findViewById(R.id.editTextApplicantName);
        cnic = (EditText) findViewById(R.id.editTextApplicantCnic);
        childName = (EditText) findViewById(R.id.editTextChildName);
        relation = (EditText) findViewById(R.id.editTextRelation);
        religion = (EditText) findViewById(R.id.editTextReligion);
        fatherName = (EditText) findViewById(R.id.editTextFatherName);
        fatherCnic = (EditText) findViewById(R.id.editTextFatherCnic);
        motherName = (EditText) findViewById(R.id.editTextMotherName);
        motherCnic = (EditText) findViewById(R.id.editTextMotherCnic);
        areaOfBirth = (EditText) findViewById(R.id.editTextAreaOfBirth);
        dateOfBirth = (EditText) findViewById(R.id.editTextDateOfBirth);
        disability = (EditText) findViewById(R.id.editTextDisability);
        address = (EditText) findViewById(R.id.editTextAddress);
        district = (EditText) findViewById(R.id.editTextDistrict);
        submit = (Button) findViewById(R.id.submitForm);
        yes = (CheckBox) findViewById(R.id.checkBoxYes);
        no = (CheckBox) findViewById(R.id.checkBoxNo);
    }
}
