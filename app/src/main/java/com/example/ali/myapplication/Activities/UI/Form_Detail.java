package com.example.ali.myapplication.Activities.UI;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Sami Khan on 10/7/2017.
 */

public class Form_Detail extends android.support.v4.app.Fragment {

    public EditText name, cnic, childName, relation, religion, fatherName, fatherCnic, motherName, motherCnic, areaOfBirth, dateOfBirth, disability, address, district;
    public Button submit;
    public CheckBox yes, no, male, female;
    public DatabaseReference ref;
    public String randomNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.polio_form,null);

        init();
        cast(view);
        clickListeners();



        return view;
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
                 randomNumber =generateRandom();
                BForm bForm = getFormData();
                ref.child(randomNumber).setValue(bForm, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                        name.setText("");
//                        cnic.setText("");
//                        childName.setText("");
//                        relation.setText("");
//                        religion.setText("");
//                        fatherName.setText("");
//                        fatherCnic.setText("");
//                        motherName.setText("");
//                        motherCnic.setText("");
//                        areaOfBirth.setText("");
//                        dateOfBirth.setText("");
//                        disability.setText("");
//                        address.setText("");
//                        district.setText("");

                    }
                });
            }
        });
    }

    //
    public BForm getFormData() {
        randomNumber = generateRandom();
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
        bForm.setFormID(randomNumber);
        return bForm;
    }

    private void cast(View view) {
        name = (EditText) view.findViewById(R.id.editTextApplicantName);
        cnic = (EditText) view.findViewById(R.id.editTextApplicantCnic);
        childName = (EditText) view.findViewById(R.id.editTextChildName);
        relation = (EditText) view.findViewById(R.id.editTextRelation);
        religion = (EditText) view.findViewById(R.id.editTextReligion);
        fatherName = (EditText) view.findViewById(R.id.editTextFatherName);
        fatherCnic = (EditText) view.findViewById(R.id.editTextFatherCnic);
        motherName = (EditText) view.findViewById(R.id.editTextMotherName);
        motherCnic = (EditText) view.findViewById(R.id.editTextMotherCnic);
        areaOfBirth = (EditText) view.findViewById(R.id.editTextAreaOfBirth);
        dateOfBirth = (EditText) view.findViewById(R.id.editTextDateOfBirth);
        disability = (EditText) view.findViewById(R.id.editTextDisability);
        address = (EditText) view.findViewById(R.id.editTextAddress);
        district = (EditText) view.findViewById(R.id.editTextDistrict);
        submit = (Button) view.findViewById(R.id.submitForm);
        yes = (CheckBox) view.findViewById(R.id.checkBoxYes);
        no = (CheckBox) view.findViewById(R.id.checkBoxNo);
        male = (CheckBox) view.findViewById(R.id.checkBoxMale);
        female = (CheckBox) view.findViewById(R.id.checkBoxFemale);

    }

    public String generateRandom(){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random rnd = new Random();
        char rndNumber;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++)
           sb.append(chars[rnd.nextInt(chars.length)]);

        return sb.toString();
    }
}
