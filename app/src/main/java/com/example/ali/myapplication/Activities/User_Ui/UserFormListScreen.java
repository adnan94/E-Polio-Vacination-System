package com.example.ali.myapplication.Activities.User_Ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFormListScreen extends Fragment {
    ListView listView;
    ArrayList<BForm> list;
    Query ref;
    Adapter_Uc_FormList_Screen adopter;


    public UserFormListScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_form_list_screen, container, false);
        casts(view);
        initializations();
        return view;
    }

    private void initializations() {
        list = new ArrayList<>();
        ref = FirebaseHandler.getDataBaseReference();
        adopter = new Adapter_Uc_FormList_Screen(getActivity(), list);
        listView.setAdapter(adopter);
        getData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("formData", list.get(position));
                ViewFormForUc viewFormForUc = new ViewFormForUc();
                ViewUserForm viewUserForm = new ViewUserForm();
                viewFormForUc.setArguments(bundle);
                viewUserForm.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, viewUserForm).addToBackStack(null).commit();

            }
        });
    }

    private void getData() {

            ref = FirebaseHandler.getInstance().getAdd_forms().orderByChild("user_uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addChildEventListener(new ChildEventListener() {
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
