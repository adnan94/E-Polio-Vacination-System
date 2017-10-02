package com.example.ali.myapplication.Activities.UI;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ali.myapplication.Activities.ModelClasses.UserModel;
import com.example.ali.myapplication.Activities.Utils.AppLogs;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ADnan on 10/1/2017.
 */

public class SignUp_Fragment extends android.support.v4.app.Fragment {

    private EditText cell_no,email,cnic,password,address,confirmpass,fname,name;
    private Button signup;
    private FirebaseAuth mAuth;
    private FirebaseUser firebase_user;
    private DatabaseReference firebase;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String gender_array[] = {"Male",
            "Female",
    };
    ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.signup_view,null);

        firebase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        editor.clear();
        email = (EditText) rootView.findViewById(R.id.email);
        cnic = (EditText) rootView.findViewById(R.id.cnic);
        password = (EditText) rootView.findViewById(R.id.password);
        fname = (EditText) rootView.findViewById(R.id.father_name);
        name = (EditText) rootView.findViewById(R.id.name);
        address = (EditText) rootView.findViewById(R.id.address);
        cell_no = (EditText) rootView.findViewById(R.id.cell_no);

        signup = (Button)rootView.findViewById(R.id.signup_btn);

   //     profile_image  = (CircleImageView)rootView.findViewById(R.id.profile_img);
    //    spinner_country=(Spinner)rootView.findViewById(R.id.country_signup_spin);
     //   spinner_gender=(Spinner)rootView.findViewById(R.id.country_signup_gender);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass = password.getText().toString();
                //      final String confrim_passwordd = confirmpass.getText().toString();
                //Checking the length of pasword while registering new USER;
                if (pass.length() <= 6) {
                    main(pass);
                }else if(( fname.getText().toString().equals("")
                        || name.getText().toString().equals("")
                        || cnic.getText().toString().equals("")
                        || pass.equals(""))){
                    ///    || confrim_passwordd.equals("")) ){
                    Toast.makeText(getActivity(),"Fields Should not be left Empty",Toast.LENGTH_SHORT).show();

                }
                else if(email.getText().length()==0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() ){
                    email.setError("Enter Valid Email Address");
                }
                else if(fname.getText().length()== 0 || !fname.getText().toString().matches("[a-zA-Z ]+")){
                    fname.setError("Invalid Name");
                }
                else if(name.getText().length() == 0 || !name.getText().toString().matches("[a-zA-Z ]+")){
                    name.setError("Invalid Name");
                }
                else if(cell_no.getText().length() == 0){
                    cell_no.setError("Invalid PHONE");
                }
                //Checking the length of pasword while registering new USER;
                else if (pass.length() <= 6) {
                    main(pass);
                }

                // else if(imageURL==null) {
                //    Toast.makeText(getActivity(),"Upload Image",Toast.LENGTH_SHORT).show();
                //  }
                else{
                    try {
                         progressDialog = ProgressDialog.show(getActivity(), "Sign Up", "Connecting...", true, false);

                        mAuth.createUserWithEmailAndPassword((email.getText().toString()), (password.getText().toString())).addOnCompleteListener(getActivity(),
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        String uid = mAuth.getCurrentUser().getUid();
               //                         String user_country = spinner_country.getSelectedItem().toString();
                 //                       String user_gender = spinner_gender.getSelectedItem().toString();
                                        firebase.child("users").child(uid).setValue(new UserModel(name.getText().toString(), fname.getText().toString(),address.getText().toString(), email.getText().toString(), pass,cnic.getText().toString(),cell_no.getText().toString()));
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Successfull", Toast.LENGTH_SHORT).show();
                                        AppLogs.logd("createUserWithEmail:onComplete: " + task.isSuccessful());
                                        if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container) != null) {
                                            getActivity().getSupportFragmentManager()
                                                    .beginTransaction().
                                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();
                                        }
//                                                } else
                                        if (!task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), " " + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AppLogs.d("FailureSignup",e.getMessage());

                            }
                        });

                    } catch (Exception ex) {
                        progressDialog.dismiss();
                        ex.printStackTrace();
                    }
                }
            }
        });


        return rootView;
    }

    private void main(String pass) {

        Toast.makeText(getActivity(), pass + "\nYou Password is no longer Stronger \nPlease signup Again with \natleast 7 Character of Pasword.\nThanks ", Toast.LENGTH_SHORT).show();
    }
}
