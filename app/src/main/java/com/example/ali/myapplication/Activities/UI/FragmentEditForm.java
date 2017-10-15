package com.example.ali.myapplication.Activities.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEditForm extends Fragment {

    public EditText name, cnic, childName, relation, religion, fatherName, fatherCnic, motherName, motherCnic, areaOfBirth, dateOfBirth, disability, address, district;
    public CheckBox yes, no, male, female;
    public Button submit;
    BForm bForm;
    public String randomNumber;


    public FragmentEditForm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_edit_form, container, false);
        bForm = getArguments().getParcelable("formData");
        cast(view);
        setFormData();
        clickListeners();
        return view;
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
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    public void setFormData() {
        if (bForm != null) {
            name.setText(bForm.applicantName);
            cnic.setText(bForm.applicantCnic);
            childName.setText(bForm.childName);
            relation.setText(bForm.relation);
            if (bForm.getGender().equalsIgnoreCase("Male")) {
                male.setChecked(true);
            } else if (female.isChecked()) {
                female.setChecked(true);
            }
            religion.setText(bForm.religion);
            fatherName.setText(bForm.fatherName);
            fatherCnic.setText(bForm.fatherCnic);
            motherName.setText(bForm.motherName);
            motherCnic.setText(bForm.motherCnic);
            areaOfBirth.setText(bForm.areaOfBirth);
            dateOfBirth.setText(bForm.dateOfBirth);
            if (bForm.isVacinated()) {
                yes.setChecked(true);
            } else {
                no.setChecked(true);
            }
            disability.setText(bForm.getDisablity());
            address.setText(bForm.getAddress());
            district.setText(bForm.getDistrict());
        }
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
}
