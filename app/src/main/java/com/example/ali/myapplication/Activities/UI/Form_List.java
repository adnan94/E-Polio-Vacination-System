package com.example.ali.myapplication.Activities.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ali.myapplication.Activities.Adoptor.Adopter_Uc_FormList_Screen;
import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Form_List extends Fragment {

    ListView listView;
    ArrayList<BForm> list;
    DatabaseReference ref;
    Adopter_Uc_FormList_Screen adopter;

    public Form_List() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form__list, container, false);
        casts(view);
        initializations();
        return view;
    }

    private void initializations() {
        list = new ArrayList<>();
        ref = FirebaseHandler.getDataBaseReference();
        adopter = new Adopter_Uc_FormList_Screen(getActivity(), list);
        listView.setAdapter(adopter);
        getData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("formData", list.get(position));
                ViewFormForUc viewFormForUc = new ViewFormForUc();
                viewFormForUc.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.maincontainer, viewFormForUc).addToBackStack(null).commit();

            }
        });
    }

    private void getData() {
        ref.child("FormData").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BForm bform = dataSnapshot.getValue(BForm.class);
                list.add(bform);
                adopter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void casts(View view) {
        listView = (ListView) view.findViewById(R.id.listViewFormsUc);
    }


}
