package com.example.ali.myapplication.Activities.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.myapplication.Activities.ModelClasses.Polio_Team;
import com.example.ali.myapplication.Activities.ModelClasses.Team_MemberObject;
import com.example.ali.myapplication.Activities.ModelClasses.UC_Object;
import com.example.ali.myapplication.Activities.ModelClasses.UserModel;
import com.example.ali.myapplication.Activities.UI.SignUp_Fragment;
import com.example.ali.myapplication.Activities.Utils.AppLogs;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.Service;
import com.example.ali.myapplication.Activities.Utils.SharedPref;
import com.example.ali.myapplication.Activities.Utils.SharedPref_Team;
import com.example.ali.myapplication.Activities.Utils.SharedPref_UC;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    public TextView signUpText,forgotPassword;
    private Button loginBtn;
    private EditText useremail;
    public TextInputEditText userpass;
    public FragmentManager fragmentManager;
    DatabaseReference firebase;
    private FirebaseAuth mAuth;
    private CheckBox checkBox;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SharedPreferences permissionStatus;
    private boolean remember_flag = false;
    public boolean team_flag = false;
    public boolean uc_flag = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        signUpText = (TextView) findViewById(R.id.signup);
        loginBtn = (Button) findViewById(R.id.user_login);
        useremail = (EditText) findViewById(R.id.editText_Loginemail);
        userpass = (TextInputEditText) findViewById(R.id.editText_loginpass);
        firebase = FirebaseDatabase.getInstance().getReference();
        checkBox = (CheckBox) findViewById(R.id.remember_me);
//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, UserHome.class));
//            finish();
//        }
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
//
                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View view = layoutInflater.inflate(R.layout.alert_layout, null);
                Button button = (Button) view.findViewById(R.id.alertSubmit);
                TextView title = (TextView) view.findViewById(R.id.alertTitleText);
                TextView message = (TextView) view.findViewById(R.id.alertMessageText);
                final EditText emailText = (EditText) view.findViewById(R.id.alertEditText);
                button.setText("Submit");
                emailText.setVisibility(View.VISIBLE);
                title.setText("Forgot your password");
                message.setText("Enter your email here we sent you email for password reset");
                alert.setView(view);

                final AlertDialog alertDialog = alert.create();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (emailText.getText().toString().equals("")) {
                            Snackbar.make(v, "Enter Email First", Snackbar.LENGTH_SHORT).show();
                        } else {
                            mAuth.sendPasswordResetEmail(emailText.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Utils.toast(LoginActivity.this,"Sucessfull");
                                                alertDialog.dismiss();
                                            } else {
                                                task.getException().getMessage();
                                                Snackbar.make(emailText, "" + task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }

                });
                alertDialog.show();


            }
        });
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        fragmentManager = getSupportFragmentManager();
        //   fbSignIn = false;

        if (SharedPref.getCurrentUser(LoginActivity.this) != null) {
            UserModel user = SharedPref.getCurrentUser(LoginActivity.this);
            useremail.setText(user.getEmail());
            userpass.setText(user.getPassword());
        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    remember_flag = true;
                } else {
                    remember_flag = false;
                }
            }
        });


        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction().add(R.id.container, new SignUp_Fragment()).addToBackStack(null).commit();


            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Sign In", "Connecting...", true, false);
                final String emails = useremail.getText().toString();
                final String passo = userpass.getText().toString();
                if (emails.length() == 0 || !useremail.getText().toString().matches(emailPattern)) {
                    useremail.setError("This is Required Field");
                    progressDialog.dismiss();
                } else if (passo.length() == 0 && passo.length() <= 6) {
                    userpass.setError("This is Required Field");
                    progressDialog.dismiss();
                } else {
                    try {

                        FirebaseHandler.getInstance().getPolio_teams()
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot!=null){
                                            if(dataSnapshot.getValue()!=null) {
                                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                    for (DataSnapshot d : data.getChildren()) {
                                                        final Team_MemberObject team_memberObject = d.getValue(Team_MemberObject.class);
                                                        if (team_memberObject.getMember_email().equals("team" + emails)) {
                                                            if (passo.equals("pakistan")) {
                                                                FirebaseHandler.getInstance()
                                                                        .getUc_teams()
                                                                       // .child(team_memberObject.getTeam_uid())
                                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                if (dataSnapshot != null) {
                                                                                    if (dataSnapshot.getValue() != null) {
                                                                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                                                            for (DataSnapshot snap : d.getChildren()) {
                                                                                                Polio_Team polio_team = snap.getValue(Polio_Team.class);
                                                                                                if (polio_team.getTeam_uid().equals(team_memberObject.getTeam_uid())) {
                                                                                                    if (polio_team.getTeam_status().equals("Activated")) {
                                                                                                        progressDialog.dismiss();
                                                                                                        team_flag = true;
                                                                                                        SharedPref_Team.setCurrentUser(LoginActivity.this, team_memberObject);
                                                                                                        startService(new Intent(LoginActivity.this, Service.class));
                                                                                                        startActivity(new Intent(LoginActivity.this, TeamMemberActivity.class));

                                                                                                        break;
                                                                                                    } else {
                                                                                                        progressDialog.dismiss();
                                                                                                        Snackbar.make(view, "ID is not Activated", Snackbar.LENGTH_SHORT).show();
                                                                                                        team_flag = false;
                                                                                                        break;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            @Override
                                                                            public void onCancelled(DatabaseError databaseError) {

                                                                            }
                                                                        });

                                                                break;

                                                            } else {
                                                                team_flag = false;
                                                                progressDialog.dismiss();
                                                                Snackbar.make(view, "Enter Correct Password", Snackbar.LENGTH_SHORT).show();
                                                            }
                                                        }else{

                                                        }
                                                    }

                                                }
                                                if (!team_flag) {
                                                    mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                                                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                                                progressDialog.dismiss();

                                                                FirebaseHandler.getInstance().getUsersRef().child(task.getResult().getUser().getUid()).addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        if (dataSnapshot != null) {
                                                                            if (dataSnapshot.getValue() != null) {
                                                                                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                                                                UserModel userModel1 = new UserModel(userModel.getName(), userModel.getFname(), userModel.getAddress(), userModel.getEmail(), userModel.getPassword(), userModel.getCnic(), userModel.getCellNo(), userModel.getUser_type(),userModel.getPicUrl(),userModel.getUid());
                                                                                UserModel.myObj = userModel1;
//                                                                                    if (userModel.getUser_type() == 3) {
//                                                                                        //User Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UserHome.class));
//                                                                                    } else if (userModel.getUser_type() == 1) {
//                                                                                        //Admin Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, AdminHome.class));
//                                                                                    } else if (userModel.getUser_type() == 2) {
//                                                                                        //Uc Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UcHome.class));
//                                                                                    } else if (userModel.getUser_type() == 4) {
//                                                                                        startActivity(new Intent(LoginActivity.this, TeamMemberActivity.class));
//                                                                                    }
                                                                                finish();
                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                    }
                                                                });
                                                            } else if (!task.isSuccessful()) {
                                                                //   progressDialog.dismiss();
                                                                //    AppLogs.logw("signInWithEmail" + task.getException());
                                                                //     Toast.makeText(LoginActivity.this, "" + task.getException(),
                                                                //            Toast.LENGTH_LONG).show();
                                                                FirebaseHandler.getInstance().getUc_members()
                                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                if(dataSnapshot!=null){
                                                                                    if(dataSnapshot.getValue()!=null){
                                                                                        for(DataSnapshot data:dataSnapshot.getChildren()){
                                                                                            UC_Object uc_object = data.getValue(UC_Object.class);
                                                                                            if(uc_object.getUc_member_email().equals(emails)){
                                                                                                if(passo.equals("pakistan")){

                                                                                                    uc_flag=true;
                                                                                                    Team_MemberObject team_memberObject  = new Team_MemberObject("","","","","","","","");
                                                                                                    SharedPref_Team.setCurrentUser(LoginActivity.this,team_memberObject);
                                                                                                    SharedPref_UC.setCurrentUser(LoginActivity.this,uc_object);
                                                                                                    progressDialog.dismiss();

                                                                                                    startActivity(new Intent(LoginActivity.this, UcHome.class));
                                                                                                    break;
                                                                                                }else{
                                                                                                    uc_flag = false;
                                                                                                    progressDialog.dismiss();
                                                                                                    Snackbar.make(view,"Enter Correct Password",Snackbar.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }else{
                                                                                                uc_flag=false;
                                                                                            }
                                                                                        }
                                                                                        if(!uc_flag){
                                                                                            mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                    if (task.isSuccessful()) {
                                                                                                        AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                                                                                                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                                                                                        progressDialog.dismiss();

                                                                                                        FirebaseHandler.getInstance().getUsersRef().child(task.getResult().getUser().getUid()).addValueEventListener(new ValueEventListener() {
                                                                                                            @Override
                                                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                if (dataSnapshot != null) {
                                                                                                                    if (dataSnapshot.getValue() != null) {
                                                                                                                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                                                                                                        UserModel userModel1 = new UserModel(userModel.getName(), userModel.getFname(), userModel.getAddress(), userModel.getEmail(), userModel.getPassword(), userModel.getCnic(), userModel.getCellNo(), userModel.getUser_type(),userModel.getPicUrl(),userModel.getUid());
                                                                                                                        UserModel.myObj = userModel1;
//                                                                                    if (userModel.getUser_type() == 3) {
//                                                                                        //User Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UserHome.class));
//                                                                                    } else if (userModel.getUser_type() == 1) {
//                                                                                        //Admin Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, AdminHome.class));
//                                                                                    } else if (userModel.getUser_type() == 2) {
//                                                                                        //Uc Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UcHome.class));
//                                                                                    } else if (userModel.getUser_type() == 4) {
//                                                                                        startActivity(new Intent(LoginActivity.this, TeamMemberActivity.class));
//                                                                                    }
                                                                                                                        finish();
                                                                                                                    }
                                                                                                                }
                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onCancelled(DatabaseError databaseError) {

                                                                                                            }
                                                                                                        });
                                                                                                    } else if (!task.isSuccessful()) {
                                                                                                        progressDialog.dismiss();
                                                                                                   //     AppLogs.logw("signInWithEmail" + task.getException());
                                                                                                     //   Toast.makeText(LoginActivity.this, "" + task.getException(),
                                                                                                       //         Toast.LENGTH_LONG).show();
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                }else{
                                                                                    mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                                                                                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                                                                                progressDialog.dismiss();

                                                                                                FirebaseHandler.getInstance().getUsersRef().child(task.getResult().getUser().getUid()).addValueEventListener(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                        if (dataSnapshot != null) {
                                                                                                            if (dataSnapshot.getValue() != null) {
                                                                                                                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                                                                                                UserModel userModel1 = new UserModel(userModel.getName(), userModel.getFname(), userModel.getAddress(), userModel.getEmail(), userModel.getPassword(), userModel.getCnic(), userModel.getCellNo(), userModel.getUser_type(),userModel.getPicUrl(),userModel.getUid());
                                                                                                                UserModel.myObj = userModel1;
//                                                                                    if (userModel.getUser_type() == 3) {
//                                                                                        //User Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UserHome.class));
//                                                                                    } else if (userModel.getUser_type() == 1) {
//                                                                                        //Admin Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, AdminHome.class));
//                                                                                    } else if (userModel.getUser_type() == 2) {
//                                                                                        //Uc Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UcHome.class));
//                                                                                    } else if (userModel.getUser_type() == 4) {
//                                                                                        startActivity(new Intent(LoginActivity.this, TeamMemberActivity.class));
//                                                                                    }
                                                                                                                finish();
                                                                                                            }
                                                                                                        }
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                                                    }
                                                                                                });
                                                                                            } else if (!task.isSuccessful()) {
                                                                                                progressDialog.dismiss();
                                                                                                AppLogs.logw("signInWithEmail" + task.getException());
                                                                                                Toast.makeText(LoginActivity.this, "" + task.getException(),
                                                                                                        Toast.LENGTH_LONG).show();
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(DatabaseError databaseError) {

                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    });
                                                }
                                            }else{
                                                mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                                                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                                            progressDialog.dismiss();

                                                            FirebaseHandler.getInstance().getUsersRef().child(task.getResult().getUser().getUid()).addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    if (dataSnapshot != null) {
                                                                        if (dataSnapshot.getValue() != null) {
                                                                            UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                                                            UserModel userModel1 = new UserModel(userModel.getName(), userModel.getFname(), userModel.getAddress(), userModel.getEmail(), userModel.getPassword(), userModel.getCnic(), userModel.getCellNo(), userModel.getUser_type(),userModel.getPicUrl(),userModel.getUid());
                                                                            UserModel.myObj = userModel1;
//                                                                                    if (userModel.getUser_type() == 3) {
//                                                                                        //User Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UserHome.class));
//                                                                                    } else if (userModel.getUser_type() == 1) {
//                                                                                        //Admin Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, AdminHome.class));
//                                                                                    } else if (userModel.getUser_type() == 2) {
//                                                                                        //Uc Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UcHome.class));
//                                                                                    } else if (userModel.getUser_type() == 4) {
//                                                                                        startActivity(new Intent(LoginActivity.this, TeamMemberActivity.class));
//                                                                                    }
                                                                            finish();
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });
                                                        } else if (!task.isSuccessful()) {
                                                        //    progressDialog.dismiss();
                                                         //   AppLogs.logw("signInWithEmail" + task.getException());
                                                          //  Toast.makeText(LoginActivity.this, "" + task.getException(),
                                                           //         Toast.LENGTH_LONG).show();
                                                            //   progressDialog.dismiss();
                                                            //    AppLogs.logw("signInWithEmail" + task.getException());
                                                            //     Toast.makeText(LoginActivity.this, "" + task.getException(),
                                                            //            Toast.LENGTH_LONG).show();
                                                            FirebaseHandler.getInstance().getUc_members()
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if(dataSnapshot!=null){
                                                                                if(dataSnapshot.getValue()!=null){
                                                                                    for(DataSnapshot data:dataSnapshot.getChildren()){
                                                                                        UC_Object uc_object = data.getValue(UC_Object.class);
                                                                                        if(uc_object.getUc_member_email().equals(emails)){
                                                                                            if(passo.equals("pakistan")){

                                                                                                uc_flag=true;
                                                                                                Team_MemberObject team_memberObject  = new Team_MemberObject("","","","","","","","");
                                                                                                SharedPref_Team.setCurrentUser(LoginActivity.this,team_memberObject);
                                                                                                SharedPref_UC.setCurrentUser(LoginActivity.this,uc_object);
                                                                                                progressDialog.dismiss();

                                                                                                startActivity(new Intent(LoginActivity.this, UcHome.class));
                                                                                                break;
                                                                                            }else{
                                                                                                uc_flag = false;
                                                                                                progressDialog.dismiss();
                                                                                                Snackbar.make(view,"Enter Correct Password",Snackbar.LENGTH_SHORT).show();
                                                                                            }
                                                                                        }else{
                                                                                            uc_flag=false;
                                                                                        }
                                                                                    }
                                                                                    if(!uc_flag){
                                                                                        mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                                                                                                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                                                                                    progressDialog.dismiss();

                                                                                                    FirebaseHandler.getInstance().getUsersRef().child(task.getResult().getUser().getUid()).addValueEventListener(new ValueEventListener() {
                                                                                                        @Override
                                                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                            if (dataSnapshot != null) {
                                                                                                                if (dataSnapshot.getValue() != null) {
                                                                                                                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                                                                                                    UserModel userModel1 = new UserModel(userModel.getName(), userModel.getFname(), userModel.getAddress(), userModel.getEmail(), userModel.getPassword(), userModel.getCnic(), userModel.getCellNo(), userModel.getUser_type(),userModel.getPicUrl(),userModel.getUid());
                                                                                                                    UserModel.myObj = userModel1;
//                                                                                    if (userModel.getUser_type() == 3) {
//                                                                                        //User Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UserHome.class));
//                                                                                    } else if (userModel.getUser_type() == 1) {
//                                                                                        //Admin Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, AdminHome.class));
//                                                                                    } else if (userModel.getUser_type() == 2) {
//                                                                                        //Uc Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UcHome.class));
//                                                                                    } else if (userModel.getUser_type() == 4) {
//                                                                                        startActivity(new Intent(LoginActivity.this, TeamMemberActivity.class));
//                                                                                    }
                                                                                                                    finish();
                                                                                                                }
                                                                                                            }
                                                                                                        }

                                                                                                        @Override
                                                                                                        public void onCancelled(DatabaseError databaseError) {

                                                                                                        }
                                                                                                    });
                                                                                                } else if (!task.isSuccessful()) {
                                                                                                    progressDialog.dismiss();
                                                                                                    AppLogs.logw("signInWithEmail" + task.getException());
                                                                                                    Toast.makeText(LoginActivity.this, "" + task.getException(),
                                                                                                            Toast.LENGTH_LONG).show();
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                }
                                                                            }else{
                                                                                mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                                                                                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                                                                            progressDialog.dismiss();

                                                                                            FirebaseHandler.getInstance().getUsersRef().child(task.getResult().getUser().getUid()).addValueEventListener(new ValueEventListener() {
                                                                                                @Override
                                                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                    if (dataSnapshot != null) {
                                                                                                        if (dataSnapshot.getValue() != null) {
                                                                                                            UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                                                                                            UserModel userModel1 = new UserModel(userModel.getName(), userModel.getFname(), userModel.getAddress(), userModel.getEmail(), userModel.getPassword(), userModel.getCnic(), userModel.getCellNo(), userModel.getUser_type(),userModel.getPicUrl(),userModel.getUid());
                                                                                                            UserModel.myObj = userModel1;
//                                                                                    if (userModel.getUser_type() == 3) {
//                                                                                        //User Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UserHome.class));
//                                                                                    } else if (userModel.getUser_type() == 1) {
//                                                                                        //Admin Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, AdminHome.class));
//                                                                                    } else if (userModel.getUser_type() == 2) {
//                                                                                        //Uc Screen
//                                                                                        startActivity(new Intent(LoginActivity.this, UcHome.class));
//                                                                                    } else if (userModel.getUser_type() == 4) {
//                                                                                        startActivity(new Intent(LoginActivity.this, TeamMemberActivity.class));
//                                                                                    }
                                                                                                            finish();
                                                                                                        }
                                                                                                    }
                                                                                                }

                                                                                                @Override
                                                                                                public void onCancelled(DatabaseError databaseError) {

                                                                                                }
                                                                                            });
                                                                                        } else if (!task.isSuccessful()) {
                                                                                            progressDialog.dismiss();
                                                                                            AppLogs.logw("signInWithEmail" + task.getException());
                                                                                    //        Toast.makeText(LoginActivity.this, "" + task.getException(),
                                                                                   //                 Toast.LENGTH_LONG).show();
                                                                                        }
                                                                                    }
                                                                                });
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });




                    } catch (Exception ex) {
                        ex.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
