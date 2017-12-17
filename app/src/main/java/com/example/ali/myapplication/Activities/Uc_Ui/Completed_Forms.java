package com.example.ali.myapplication.Activities.Uc_Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ali.myapplication.Activities.Adaptor.Adapter_Uc_FormList_Screen;
import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.Uc_Ui.ViewFormForUc;
import com.example.ali.myapplication.Activities.User_Ui.ViewUserForm;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sami Khan on 10/28/2017.
 */

public class Completed_Forms extends android.support.v4.app.Fragment {

    public ListView listViewFormsUc_app;

    ArrayList<BForm> list;
    Query ref;
    Adapter_Uc_FormList_Screen adopter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.complete_forms,null);

        casts(view);
        initializations();
        return view;
    }

    private void initializations() {
        list = new ArrayList<>();
        ref = FirebaseHandler.getDataBaseReference();
        adopter = new Adapter_Uc_FormList_Screen(getActivity(), list);
        listViewFormsUc_app.setAdapter(adopter);
        getData();
        listViewFormsUc_app.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("formData", list.get(position));
                ViewFormForUc viewFormForUc = new ViewFormForUc();
                ViewUserForm viewUserForm = new ViewUserForm();
                viewFormForUc.setArguments(bundle);
                viewUserForm.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_uc, viewFormForUc).addToBackStack(null).commit();

            }
        });
    }

    private void getData() {
        FirebaseHandler.getInstance().getAdd_forms().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if(dataSnapshot.getValue()!=null) {
                        list.clear();
                        for(DataSnapshot data:dataSnapshot.getChildren()) {
                            BForm bform = data.getValue(BForm.class);
                            if (bform.getForm_status().equals("Completed")) {
                                list.add(bform);
                                adopter.notifyDataSetChanged();
                            }
                        }
                    }else{
                        adopter.notifyDataSetChanged();
                    }
                }
                    adopter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void casts(View view) {
        listViewFormsUc_app = (ListView) view.findViewById(R.id.listViewFormsUc_compl);
    }
}
