package com.example.ali.myapplication.Activities.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.myapplication.Activities.ModelClasses.UserModel;
import com.example.ali.myapplication.Activities.UI.SignUp_Fragment;
import com.example.ali.myapplication.Activities.Utils.AppLogs;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.SharedPref;
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


    public TextView signUpText;
    private Button loginBtn;
    private EditText useremail, userpass;
    public FragmentManager fragmentManager;
    DatabaseReference firebase;
    private FirebaseAuth mAuth;
    private CheckBox checkBox;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SharedPreferences permissionStatus;
    private boolean remember_flag = false;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        signUpText = (TextView) findViewById(R.id.signup);
        loginBtn = (Button) findViewById(R.id.user_login);
        useremail = (EditText) findViewById(R.id.editText_Loginemail);
        userpass = (EditText) findViewById(R.id.editText_loginpass);
        firebase = FirebaseDatabase.getInstance().getReference();
        checkBox = (CheckBox) findViewById(R.id.remember_me);
//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, UserHome.class));
//            finish();
//        }

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
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Sign In", "Connecting...", true, false);
                final String emails = useremail.getText().toString();
                final String passo = userpass.getText().toString();
                if (emails.length() == 0) {
                    useremail.setError("This is Required Field");
                    progressDialog.dismiss();
                } else if (passo.length() == 0 && passo.length() <= 6) {
                    userpass.setError("This is Required Field");
                    progressDialog.dismiss();
                } else {
                    try {

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
                                                    UserModel userModel1 = new UserModel(userModel.getName(), userModel.getFname(), userModel.getAddress(), userModel.getEmail(), userModel.getPassword(), userModel.getCnic(), userModel.getCellNo(), userModel.getUser_type());
                                                    UserModel.myObj = userModel1;
                                                    if (userModel.getUser_type() == 3) {
                                                        //User Screen
                                                        startActivity(new Intent(LoginActivity.this, UserHome.class));
                                                    } else if (userModel.getUser_type() == 1) {
                                                        //Admin Screen
                                                        startActivity(new Intent(LoginActivity.this, AdminHome.class));
                                                    } else if (userModel.getUser_type() == 2) {
                                                        //Uc Screen
                                                        startActivity(new Intent(LoginActivity.this, UcHome.class));
                                                    } else if (userModel.getUser_type() == 4) {
                                                        startActivity(new Intent(LoginActivity.this, TeamMemberActivity.class));
                                                    }
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
