package com.example.ali.myapplication.Activities.Admin_Ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.Adaptor.Uc_ListAdapter;
import com.example.ali.myapplication.Activities.ModelClasses.UC_Object;
import com.example.ali.myapplication.Activities.ModelClasses.UserModel;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfUc extends android.support.v4.app.Fragment {

    public ListView uc_memberlist;
    public ArrayList<UC_Object> userModelArrayList;
    public Uc_ListAdapter uc_listAdapter;
    public ImageView back_arrow;
    public static TextView ActionBartitle;
    public ListOfUc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_list_of_uc,null);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("List Of UC");

        back_arrow.setVisibility(View.INVISIBLE);
        uc_memberlist = (ListView)view.findViewById(R.id.uc_memberlist);
        userModelArrayList = new ArrayList<>();
        uc_listAdapter = new Uc_ListAdapter(userModelArrayList,getActivity());
        uc_memberlist.setAdapter(uc_listAdapter);


        FirebaseHandler.getInstance().getUc_members().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if(dataSnapshot.getValue()!=null){
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            UC_Object userModel = data.getValue(UC_Object.class);

                                userModelArrayList.add(userModel);
                            uc_listAdapter.add(userModel);
                                uc_listAdapter.notifyDataSetChanged();

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
