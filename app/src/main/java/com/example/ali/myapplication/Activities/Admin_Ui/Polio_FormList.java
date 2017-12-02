package com.example.ali.myapplication.Activities.Admin_Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.Adaptor.Adapter_Uc_FormList_Screen;
import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sami Khan on 12/2/2017.
 */

public class Polio_FormList extends android.support.v4.app.Fragment {

    public ListView polio_forms;
    public ArrayList<BForm> bFormArrayList;
    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Adapter_Uc_FormList_Screen adapter_uc_formList_screen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.polio_list_admin,null);

        bFormArrayList = new ArrayList<>();
        adapter_uc_formList_screen  = new Adapter_Uc_FormList_Screen(getActivity(),bFormArrayList);
        polio_forms = (ListView)view.findViewById(R.id.polio_forms);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("List Of Polio Forms");


        FirebaseHandler.getInstance().getAdd_forms()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null){
                            if(dataSnapshot.getValue()!=null){
                                for(DataSnapshot data:dataSnapshot.getChildren()){
                                    BForm bForm = data.getValue(BForm.class);
                                    bFormArrayList.add(bForm);
                                    adapter_uc_formList_screen.notifyDataSetChanged();
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
