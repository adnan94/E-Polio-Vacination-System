package com.example.ali.myapplication.Activities.User_Ui;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.ModelClasses.Form_Token;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewUserForm extends Fragment {

    public TextView name, cnic, childName, relation, religion, fatherName, fatherCnic, motherName, motherCnic, areaOfBirth, dateOfBirth, disability, address, district, gender, vacinated;
    BForm bform;
    public Dialog show_token;
    public Button show_token_btn;
    public TextView father_name_alert;
    public TextView father_cnic_alert;
    public TextView mother_name_alert, mother_cnic_alert, dob_alert, form_submit_alert, form_verification_alert, form_status;
    public TextView form_verification_time;
    public TextView user_name;
    public TextView token_id;
    public TextView track_id;

    public ViewUserForm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_user_form, container, false);
        cast(view);
        init();

        show_token_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_token.show();

                FirebaseHandler.getInstance().getForm_token()
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(bform.getFormID())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot!=null){
                                    if(dataSnapshot.getValue()!=null){
                                    for(DataSnapshot data:dataSnapshot.getChildren()) {
                                        Form_Token form_token = data.getValue(Form_Token.class);
                                        String dateString = DateFormat.format("dd/MM/yyyy", new Date(bform.getTimestamp())).toString();


                                        show_token_btn.setVisibility(View.VISIBLE);
                                        father_name_alert.setText(bform.getFatherName());
                                        father_cnic_alert.setText(bform.getFatherCnic());
                                        mother_name_alert.setText(bform.getMotherName());
                                        mother_cnic_alert.setText(bform.getMotherCnic());
                                        dob_alert.setText(bform.getDateOfBirth());
                                        form_submit_alert.setText(dateString);
                                        form_verification_alert.setText(form_token.getAppointment_date());
                                        form_verification_time.setText(form_token.getAppointment_time());
                                        form_status.setText(bform.getForm_status());
                                        user_name.setText(bform.getChildName());
                                        token_id.setText(form_token.getForm_id());
                                        track_id.setText(form_token.getToken_id());

                                    }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



            }
        });


        return view;
    }

    private void init() {
        bform = getArguments().getParcelable("formData");
        if (bform != null) {
            name.setText("Applicant Name : " + bform.getUserName());
            cnic.setText("Applicant Cnic : " + bform.getApplicantCnic());
            childName.setText("Child Name : " + bform.getChildName());
            relation.setText("Relation : " + bform.getRelation());
            gender.setText("Gender : " + bform.getGender());
            religion.setText("Relation : " + bform.getReligion());
            fatherName.setText("Father Name : " + bform.getFatherName());
            fatherCnic.setText("Father Cnic : " + bform.getFatherCnic());
            motherName.setText("Mother Name : " + bform.getMotherName());
            motherCnic.setText("Mother Cnic : " + bform.getMotherCnic());
            areaOfBirth.setText("Area Of Birth : " + bform.getAreaOfBirth());
            dateOfBirth.setText("Date Of Birth : " + bform.getDateOfBirth());
            vacinated.setText("Vaccinated : " + bform.isVacinated());
            disability.setText("Disability : " + bform.getDisablity());
            address.setText("Address : " + bform.getAddress());
            district.setText("District : " + bform.getDistrict());


            if (bform.getForm_status().equals("In-Progress")) {

                FirebaseHandler.getInstance().getForm_token()
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(bform.getFormID())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot!=null){
                                    if(dataSnapshot.getValue()!=null){

                                        Form_Token form_token = dataSnapshot.getValue(Form_Token.class);
                                        String dateString = DateFormat.format("dd/MM/yyyy", new Date( bform.getTimestamp())).toString();


                                        show_token_btn.setVisibility(View.VISIBLE);
                                        father_name_alert.setText(bform.getFatherName());
                                        father_cnic_alert.setText(bform.getFatherCnic());
                                        mother_name_alert.setText(bform.getMotherName());
                                        mother_cnic_alert.setText(bform.getMotherCnic());
                                        dob_alert.setText(bform.getDateOfBirth());
                                        form_submit_alert.setText(dateString);
                                        form_verification_alert.setText(form_token.getAppointment_date());
                                        form_verification_time.setText(form_token.getAppointment_time());
                                        form_status.setText(bform.getForm_status());
                                        user_name.setText(bform.getChildName());
                                        token_id.setText(form_token.getForm_id());
                                        track_id.setText(form_token.getToken_id());

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });





            } else {
                show_token_btn.setVisibility(View.GONE);
            }

        }
    }

    private void cast(View view) {
        name = (TextView) view.findViewById(R.id.applicantName);
        cnic = (TextView) view.findViewById(R.id.applicantCnic);
        childName = (TextView) view.findViewById(R.id.childName);
        relation = (TextView) view.findViewById(R.id.relation);
        gender = (TextView) view.findViewById(R.id.gender);
        religion = (TextView) view.findViewById(R.id.religion);
        fatherName = (TextView) view.findViewById(R.id.fatherName);
        fatherCnic = (TextView) view.findViewById(R.id.fatherCnic);
        motherName = (TextView) view.findViewById(R.id.motherName);
        motherCnic = (TextView) view.findViewById(R.id.motherCnic);
        areaOfBirth = (TextView) view.findViewById(R.id.areaOfBirth);
        dateOfBirth = (TextView) view.findViewById(R.id.dateOfBirth);
        vacinated = (TextView) view.findViewById(R.id.vacinated);
        disability = (TextView) view.findViewById(R.id.disability);
        address = (TextView) view.findViewById(R.id.address);
        district = (TextView) view.findViewById(R.id.district);
        show_token_btn = (Button) view.findViewById(R.id.show_token);

        //alert dialog
        View completeView = getActivity().getLayoutInflater().inflate(R.layout.token_view, null);
        show_token = new Dialog(getActivity());
        show_token.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        show_token.setContentView(completeView);
        father_name_alert = (TextView) completeView.findViewById(R.id.father_name_alert);
        father_cnic_alert = (TextView) completeView.findViewById(R.id.father_cnic_alert);
        mother_name_alert = (TextView) completeView.findViewById(R.id.mother_name_alert);
        mother_cnic_alert = (TextView) completeView.findViewById(R.id.mother_cnic_alert);
        user_name = (TextView)completeView.findViewById(R.id.user_name);
        dob_alert = (TextView) completeView.findViewById(R.id.dob_alert);
        form_submit_alert = (TextView) completeView.findViewById(R.id.form_submit_alert);
        form_verification_alert = (TextView) completeView.findViewById(R.id.form_verification_alert);
        form_status = (TextView) completeView.findViewById(R.id.form_status);
        form_verification_time = (TextView)completeView.findViewById(R.id.form_verification_time);
        token_id = (TextView)completeView.findViewById(R.id.token_id);
        track_id = (TextView)completeView.findViewById(R.id.track_id);

        //end


    }

}
