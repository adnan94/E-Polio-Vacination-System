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
    EditText name, cnic, childName, relation, religion, fatherName, fatherCnic, motherName, motherCnic, areaOfBirth, dateOfBirth, disability, address, district;
    Button submit;
    CheckBox yes, no, male, female;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        cast();
        clickListeners();
    }

    private void init() {
        ref = FirebaseHandler.getInstance().getDataBaseReference().child("FormData");
    }

    private void clickListeners() {
        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    no.setChecked(false);
                } else {
                    no.setChecked(true);
                }
            }
        });
        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    yes.setChecked(false);
                } else {
                    yes.setChecked(true);
                }
            }
        });
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    female.setChecked(false);
                } else {
                    female.setChecked(true);
                }
            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    male.setChecked(false);
                } else {
                    male.setChecked(true);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                final int key = random.nextInt() + 200;
                Utils.toast(MainActivity.this, key + "");
                BForm bForm = getFormData();
                ref.child(String.valueOf(key)).setValue(bForm, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setTitle("Your Token Id Is : " + key);
                        dialog.setCancelable(true);
                        dialog.show();
                        Utils.toast(MainActivity.this, "Your Token Id Is : " + key);
                        Utils.toast(MainActivity.this, "Sucessfull");
                        name.setText("");
                        cnic.setText("");
                        childName.setText("");
                        relation.setText("");
                        religion.setText("");
                        fatherName.setText("");
                        fatherCnic.setText("");
                        motherName.setText("");
                        motherCnic.setText("");
                        areaOfBirth.setText("");
                        dateOfBirth.setText("");
                        disability.setText("");
                        address.setText("");
                        district.setText("");

                    }
                });
            }
        });
    }

    //
    public BForm getFormData() {
        BForm bForm = new BForm();
        bForm.setApplicantName(name.getText().toString());
        bForm.setApplicantCnic(cnic.getText().toString());
        bForm.setChildName(childName.getText().toString());
        bForm.setRelation(relation.getText().toString());
        if (male.isChecked()) {
            bForm.setGender("Male");
        } else if (female.isChecked()) {
            bForm.setGender("Female");
        }
        bForm.setReligion(religion.getText().toString());
        bForm.setFatherName(fatherName.getText().toString());
        bForm.setFatherCnic(fatherCnic.getText().toString());
        bForm.setMotherName(motherName.getText().toString());
        bForm.setMotherCnic(motherCnic.getText().toString());
        bForm.setAreaOfBirth(areaOfBirth.getText().toString());
        bForm.setDateOfBirth(dateOfBirth.getText().toString());
        if (yes.isChecked()) {
            bForm.setVacinated(true);
        } else {
            bForm.setVacinated(false);
        }
        bForm.setDisablity(disability.getText().toString());
        bForm.setAddress(address.getText().toString());
        bForm.setDistrict(district.getText().toString());
        return bForm;
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
        male = (CheckBox) findViewById(R.id.checkBoxMale);
        female = (CheckBox) findViewById(R.id.checkBoxFemale);

    }
}


