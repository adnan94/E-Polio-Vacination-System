package com.example.ali.myapplication.Activities.Uc_Ui;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.ModelClasses.Form_Token;
import com.example.ali.myapplication.Activities.UI.FragmentEditForm;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFormForUc extends Fragment {

    public TextView name, cnic, childName, relation, religion, fatherName, fatherCnic, motherName, motherCnic, areaOfBirth, dateOfBirth, disability, address, district, gender, vacinated;
    Button edit;
    BForm bform;
    public Button change_status;
    public Dialog filter_dialog;
    public Spinner status_userform;
    public String status[]={"Applied","In-Progress","Completed"};
    public ArrayAdapter<String> statusAdapter;
    public Button form_status_apply,form_status_cancle;

    public ViewFormForUc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_form_for_ui, container, false);
        cast(view);
        init();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("formData",bform);
                FragmentEditForm fragmentEditForm = new FragmentEditForm();
                fragmentEditForm.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_uc,fragmentEditForm).addToBackStack(null).commit();

            }
        });

        change_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_dialog.show();
            }
        });

        form_status_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_dialog.dismiss();
            }
        });

        form_status_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(bform!=null){
                   if(status_userform.getSelectedItem().toString().equals("In-Progress")) {
                       FirebaseHandler.getInstance().getAdd_forms().child(bform.getFormID()).child("form_status").setValue(status_userform.getSelectedItem().toString(), new DatabaseReference.CompletionListener() {
                           @Override
                           public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                               filter_dialog.dismiss();
                               DatabaseReference key = FirebaseHandler.getInstance().getForm_token().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(bform.getFormID()).push();
                               key.setValue(new Form_Token(key.getKey(), bform.getFormID(),
                                       bform.getApplicantName(), bform.getApplicantCnic(),
                                       bform.getTimestamp(), bform.getTimestamp()));

                           }
                       });
                   }else{
                       filter_dialog.dismiss();
                   }
               }
            }
        });




        return view;
    }

    private void init() {
        bform = getArguments().getParcelable("formData");
        if(bform!=null){
            name.setText("Applicant Name : "+bform.getApplicantName());
            cnic.setText("Applicant Cnic : "+bform.getApplicantCnic());
            childName.setText("Child Name : "+bform.getChildName());
            relation.setText("Relation : "+bform.getRelation());
            gender.setText("Gender : "+bform.getGender());
            religion.setText("Relation : "+bform.getReligion());
            fatherName.setText("Father Name : "+bform.getFatherName());
            fatherCnic.setText("Father Cnic : "+bform.getFatherCnic());
            motherName.setText("Mother Name : "+bform.getMotherName());
            motherCnic.setText("Mother Cnic : "+bform.getMotherCnic());
            areaOfBirth.setText("Area Of Birth : "+bform.getAreaOfBirth());
            dateOfBirth.setText("Date Of Birth : "+bform.getDateOfBirth());
            vacinated.setText("Vaccinated : "+bform.isVacinated());
            disability.setText("Disability : "+bform.getDisablity());
            address.setText("Address : "+bform.getAddress());
            district.setText("District : "+bform.getDistrict());

        }
    }

    private void cast(View view) {
        View completeView = getActivity().getLayoutInflater().inflate(R.layout.filter_dialog, null);
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
        edit=(Button)view.findViewById(R.id.edit);

        change_status = (Button)view.findViewById(R.id.change_status);


        filter_dialog = new Dialog(getActivity());
        filter_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        filter_dialog.setContentView(completeView);
        form_status_cancle = (Button)completeView.findViewById(R.id.form_status_cancle);
        form_status_apply= (Button)completeView.findViewById(R.id.form_status_apply);
        status_userform= (Spinner)completeView.findViewById(R.id.status_userform);
        statusAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,status);
        status_userform.setAdapter(statusAdapter);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("formData",bform);
                FragmentEditForm fragmentEditForm = new FragmentEditForm();
                fragmentEditForm.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_uc,fragmentEditForm).addToBackStack(null).commit();


    }
    });
    }

}
