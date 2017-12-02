package com.example.ali.myapplication.Activities.Uc_Ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ali.myapplication.Activities.ModelClasses.Polio_Team;
import com.example.ali.myapplication.Activities.ModelClasses.Team_MemberObject;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Sami Khan on 11/12/2017.
 */

public class Add_PolioTeam_Fragment extends android.support.v4.app.Fragment {
    public LinearLayout add_members;
    public String[] menuName = {"Home", "Add Polio Teams", "View Polio Teams", "About", "Setting", "Log Out"};
    public Spinner spinner_area;
    public String[] areaName = {"Nazimabad", "Johar", "Gulshan", "Landi", "Malir"};
    public String[] team_statuss = {"Activated", "Not Activate"};
    public ArrayAdapter arrayAdapter;
    public ArrayAdapter team_adapter;
    public ImageView back_arrow;
    public static TextView ActionBartitle;
    public DatabaseReference reference;
    public DatabaseReference referenceKey;
    public String key;
    public EditText team_name, team_email;
    public Team_MemberObject team_memberObject;
    public Polio_Team polio_team;
    public Button add_polio_team;
    public LinearLayout members_container;
    public Spinner team_status;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_polioteam_fragment, null);
        initViews(view);


        if (getArguments() != null) {
            if (getArguments().getParcelable("member") != null) {
                team_memberObject = getArguments().getParcelable("member");
            }
            if (getArguments().getParcelable("obj") != null) {
                polio_team = getArguments().getParcelable("obj");
                team_name.setText(polio_team.getTeam_name());
                team_email.setText(polio_team.getTeam_email());

                key = polio_team.getTeam_uid();
            }
        } else {
            polio_team = new Polio_Team(key, team_name.getText().toString(), spinner_area.getSelectedItem().toString(),team_email.getText().toString(),"",team_status.getSelectedItem().toString(),0.0,0.0);
        }

        FirebaseHandler.getInstance().getPolio_teams()
                .child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (getActivity() != null) {
                            if (dataSnapshot != null) {
                                if (dataSnapshot.getValue() != null) {
                                    members_container.removeAllViews();
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        Team_MemberObject team_memberObject = data.getValue(Team_MemberObject.class);
                                        addLayout(team_memberObject);
                                    }
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        add_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          //      if(team_name.getText().toString().length()==0 || !team_name.getText().toString().matches("[A-Za-z][^.]*")){

           //         Snackbar.make(view,"Enter Valid team name",Snackbar.LENGTH_SHORT).show();

         //       }else if(team_email.getText().toString().length()==0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(team_email.getText().toString()).matches()){
         //           Snackbar.make(view,"Enter Valid team email",Snackbar.LENGTH_SHORT).show();

           //     }else {

                    getActivity().getSupportFragmentManager().popBackStack();
                    Polio_Team polio_team = new Polio_Team(key, team_name.getText().toString(), spinner_area.getSelectedItem().toString(), team_email.getText().toString(), "",team_status.getSelectedItem().toString(),0.0,0.0);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("obj", polio_team);
                    Add_Team_View add_team_view = new Add_Team_View();
                    add_team_view.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.add_member_container, add_team_view)
                            .addToBackStack(null)
                            .commit();
             //   }
            }
        });

        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        team_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add_polio_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(team_name.getText().toString().equals("") || !team_name.getText().toString().matches("[A-Za-z][^.]*")){
                    team_name.setError("Enter Valid Name");
                }else if(team_email.getText().toString().equals("") && !android.util.Patterns.EMAIL_ADDRESS.matcher(team_email.getText().toString()).matches()){
                    team_email.setError("Enter Valid Email");
                }else {
                    polio_team = new Polio_Team(key, team_name.getText().toString(), spinner_area.getSelectedItem().toString(), team_email.getText().toString(),"",team_status.getSelectedItem().toString(),0.0,0.0);


                    FirebaseHandler.getInstance().getUc_teams().child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(key).setValue(polio_team, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Add_Polio_TeamActivity.getInstance().onBackPressed();
                        }
                    });

                }
            }
        });


        return view;
    }

    private void addLayout(final Team_MemberObject team_memberObject) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(getActivity());

        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.member_container_view, null, false);
        members_container.addView(layout);
        TextView member_name = (TextView) layout.findViewById(R.id.member_name);
        TextView member_email = (TextView) layout.findViewById(R.id.member_email);
        TextView member_nic = (TextView) layout.findViewById(R.id.member_nic);
        TextView member_phone = (TextView) layout.findViewById(R.id.member_phone);
        ImageView member_picture = (ImageView)layout.findViewById(R.id.member_picture);
        layout.setTag(team_memberObject);
        member_name.setText(team_memberObject.getMember_name());
        member_email.setText(team_memberObject.getMember_email());
        member_nic.setText(team_memberObject.getMember_nic_no());
        member_phone.setText(team_memberObject.getMember_phone_no());


        Glide.with(getActivity())
                .load(team_memberObject.getMember_pic())
                .asBitmap()
                .placeholder(R.drawable.user)
                .into(member_picture);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
                Bundle bundle= new Bundle();
                bundle.putParcelable("obj",polio_team);
                bundle.putParcelable("member",team_memberObject);
                Add_Team_View add_team_view = new Add_Team_View();
                add_team_view.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.add_member_container, add_team_view)
                        .addToBackStack(null)
                        .commit();
            }

        });



    }

    private void initViews(View view) {

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Add Team");
        reference = FirebaseDatabase.getInstance().getReference();
        referenceKey = FirebaseHandler.getInstance().getPolio_teams().push();
        key = referenceKey.getKey();
        add_members = (LinearLayout) view.findViewById(R.id.add_members);
        team_name = (EditText) view.findViewById(R.id.team_name);
        add_polio_team = (Button) view.findViewById(R.id.add_polio_team);
        team_email = (EditText) view.findViewById(R.id.team_email);
        members_container = (LinearLayout) view.findViewById(R.id.members_container);
        spinner_area = (Spinner) view.findViewById(R.id.spinner_area);
        team_status = (Spinner)view.findViewById(R.id.team_status);
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, areaName);
        team_adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, team_statuss);
        spinner_area.setAdapter(arrayAdapter);
        team_status.setAdapter(team_adapter);
    }
}
