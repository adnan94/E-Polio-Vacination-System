package com.example.ali.myapplication.Activities.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFormForUc extends Fragment {

    public TextView name, cnic, childName, relation, religion, fatherName, fatherCnic, motherName, motherCnic, areaOfBirth, dateOfBirth, disability, address, district, gender, vacinated;
    Button edit;
    BForm bform;

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
        return view;
    }

    private void init() {
        bform = getArguments().getParcelable("formData");
        if(bform!=null){
            name.setText("Applicant Name : "+bform.applicantName);
            cnic.setText("Applicant Cnic : "+bform.applicantCnic);
            childName.setText("Child Name : "+bform.childName);
            relation.setText("Relation : "+bform.relation);
            gender.setText("Gender : "+bform.getGender());
            religion.setText("Relation : "+bform.religion);
            fatherName.setText("Father Name : "+bform.fatherName);
            fatherCnic.setText("Father Cnic : "+bform.fatherCnic);
            motherName.setText("Mother Name : "+bform.motherName);
            motherCnic.setText("Mother Cnic : "+bform.getMotherCnic());
            areaOfBirth.setText("Area Of Birth : "+bform.getAreaOfBirth());
            dateOfBirth.setText("Date Of Birth : "+bform.dateOfBirth);
            vacinated.setText("Vaccinated : "+bform.isVacinated());
            disability.setText("Disability : "+bform.disablity);
            address.setText("Address : "+bform.getAddress());
            district.setText("District : "+bform.getDistrict());

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
        edit=(Button)view.findViewById(R.id.edit);
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
