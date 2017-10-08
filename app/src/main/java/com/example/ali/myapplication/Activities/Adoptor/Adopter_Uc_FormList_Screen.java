package com.example.ali.myapplication.Activities.Adoptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.R;

import java.util.ArrayList;

/**
 * Created by AdnanAhmed on 10/7/2017.
 */

public class Adopter_Uc_FormList_Screen extends BaseAdapter {
    Context context;
    ArrayList<BForm> list;
    LayoutInflater inflater;

    public Adopter_Uc_FormList_Screen(Context context, ArrayList<BForm> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.item_form_uc_screen, viewGroup, false);
        TextView name = (TextView) v.findViewById(R.id.textNameUcFormListScreen);
        TextView cnic = (TextView) v.findViewById(R.id.textCnicUcFormListScreen);
        name.setText("Name : " + list.get(i).applicantName);
        cnic.setText("Cnic : " + list.get(i).applicantCnic);

        return v;
    }
}
