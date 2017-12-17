package com.example.ali.myapplication.Activities.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEditForm extends Fragment {

    public EditText name, cnic, childName, relation, religion, fatherName, fatherCnic, motherName, motherCnic, areaOfBirth, dateOfBirth, disability, address, district;
    public CheckBox yes, no, male, female;
    public Button submit;
    BForm bForm;
    public String randomNumber;
    public Toolbar toolbar_outside;
    public ImageView back_image;
    public TextView main_appbar_textView;
    public TextView textViewGender;
    public TextView textViewVacinated;
    public String gender = "";
    public boolean vaccinated;

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
                    vaccinated = Boolean.parseBoolean("false");
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
                    vaccinated = Boolean.parseBoolean("true");
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
                    gender = "Female";
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
                    gender = "Male";
                    male.setChecked(true);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BForm bFormm = new BForm(name.getText().toString(), cnic.getText().toString(), childName.getText().toString(), relation.getText().toString()

                        , religion.getText().toString(), fatherName.getText().toString(), fatherCnic.getText().toString(), motherName.getText().toString(), motherCnic.getText().toString(),
                        areaOfBirth.getText().toString(), dateOfBirth.getText().toString(), disability.getText().toString(), address.getText().toString(), district.getText().toString(),
                        gender, vaccinated, bForm.getFormID(), bForm.getUser_uid(), bForm.getForm_status(), bForm.getTimestamp(), bForm.getLat(), bForm.getLng(), bForm.getDrops(),
                        bForm.getVacinationDate(), bForm.getCell());


                FirebaseHandler.getInstance()
                        .getAdd_forms()
                        .child(bForm.getFormID())
                        .setValue(bFormm, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        });


            }
        });
    }

    public void setFormData() {

        FirebaseHandler.getInstance()
                .getAdd_forms()
                .child(bForm.getFormID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                     if(dataSnapshot!=null){
                         if(dataSnapshot.getValue()!=null){
                             BForm bForm = dataSnapshot.getValue(BForm.class);

                             if (bForm != null) {
                                 name.setText(bForm.getUserName());
                                 cnic.setText(bForm.getApplicantCnic());
                                 childName.setText(bForm.getChildName());
                                 relation.setText(bForm.getRelation());
                                 if (bForm.getGender().equalsIgnoreCase("Male")) {
                                     gender = "Male";
                                     male.setChecked(true);
                                 } else if (female.isChecked()) {
                                     gender = "Female";
                                     female.setChecked(true);
                                 }
                                 religion.setText(bForm.getReligion());
                                 fatherName.setText(bForm.getFatherName());
                                 fatherCnic.setText(bForm.getFatherCnic());
                                 motherName.setText(bForm.getMotherName());
                                 motherCnic.setText(bForm.getMotherCnic());
                                 areaOfBirth.setText(bForm.getAreaOfBirth());
                                 dateOfBirth.setText(bForm.getDateOfBirth());
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
                     }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    //
    public BForm getFormData() {
        BForm bForm = new BForm();
        bForm.setUserName(name.getText().toString());
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
        Utils.relwayRegular(getActivity(), name);
        cnic = (EditText) view.findViewById(R.id.editTextApplicantCnic);
        Utils.relwayRegular(getActivity(), cnic);
        childName = (EditText) view.findViewById(R.id.editTextChildName);
        Utils.relwayRegular(getActivity(), childName);
        relation = (EditText) view.findViewById(R.id.editTextRelation);
        Utils.relwayRegular(getActivity(), relation);
        religion = (EditText) view.findViewById(R.id.editTextReligion);
        Utils.relwayRegular(getActivity(), religion);
        fatherName = (EditText) view.findViewById(R.id.editTextFatherName);
        Utils.relwayRegular(getActivity(), fatherName);
        fatherCnic = (EditText) view.findViewById(R.id.editTextFatherCnic);
        Utils.relwayRegular(getActivity(), fatherCnic);
        motherName = (EditText) view.findViewById(R.id.editTextMotherName);
        Utils.relwayRegular(getActivity(), motherName);
        motherCnic = (EditText) view.findViewById(R.id.editTextMotherCnic);
        Utils.relwayRegular(getActivity(), motherCnic);
        areaOfBirth = (EditText) view.findViewById(R.id.editTextAreaOfBirth);
        Utils.relwayRegular(getActivity(), areaOfBirth);
        dateOfBirth = (EditText) view.findViewById(R.id.editTextDateOfBirth);
        Utils.relwayRegular(getActivity(), dateOfBirth);
        disability = (EditText) view.findViewById(R.id.editTextDisability);
        Utils.relwayRegular(getActivity(), disability);
        address = (EditText) view.findViewById(R.id.editTextAddress);
        Utils.relwayRegular(getActivity(), address);
        district = (EditText) view.findViewById(R.id.editTextDistrict);
        Utils.relwayRegular(getActivity(), district);
        submit = (Button) view.findViewById(R.id.submitForm);
        Utils.relwayRegular(getActivity(), name);
        yes = (CheckBox) view.findViewById(R.id.checkBoxYes);
        Utils.relwayRegular(getActivity(), name);
        no = (CheckBox) view.findViewById(R.id.checkBoxNo);
        Utils.relwayRegular(getActivity(), submit);
        male = (CheckBox) view.findViewById(R.id.checkBoxMale);
        Utils.relwayRegular(getActivity(), male);
        female = (CheckBox) view.findViewById(R.id.checkBoxFemale);
        Utils.relwayRegular(getActivity(), female);
        toolbar_outside = (Toolbar) view.findViewById(R.id.toolbar_outside);
        back_image = (ImageView) toolbar_outside.findViewById(R.id.back_image);
        main_appbar_textView = (TextView) view.findViewById(R.id.main_appbar_textView);
        Utils.relwayMedium(getActivity(), main_appbar_textView);
        main_appbar_textView.setText("Edit Form");
        // back_image.setVisibility(View.INVISIBLE);

        textViewGender = (TextView) view.findViewById(R.id.textViewGender);
        Utils.relwaySemiBold(getActivity(), textViewGender);

        textViewVacinated = (TextView) view.findViewById(R.id.textViewVacinated);
        Utils.relwaySemiBold(getActivity(), textViewVacinated);


        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
