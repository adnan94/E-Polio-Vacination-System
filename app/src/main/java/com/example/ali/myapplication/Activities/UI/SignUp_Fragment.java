package com.example.ali.myapplication.Activities.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.myapplication.Activities.ModelClasses.UserModel;
import com.example.ali.myapplication.Activities.Utils.AppLogs;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

/**
 * Created by ADnan on 10/1/2017.
 */

public class SignUp_Fragment extends android.support.v4.app.Fragment {
    public static TextView ActionBartitle;
    public ImageView back_arrow;
    private EditText cell_no, email, cnic, address, fname, name;
    public TextInputEditText password;
    private Button signup;
    private FirebaseAuth mAuth;
    //    private FirebaseUser firebase_user;
    private DatabaseReference firebase;
    //    private SharedPreferences sharedPreferences;
//    private SharedPreferences.Editor editor;
//    String gender_array[] = {"Male",
//            "Female",
//    };
    ProgressDialog progressDialog;
    public Button verified;
    public Dialog show_token;
    public View completeView;
    public Button cancle;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    public Button verfiy_code;
    public EditText code_area;
    public String code;
    public CheckBox check;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.signup_view, null);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);

        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("SignUp");
        back_arrow = (ImageView)toolbar.findViewById(R.id.back_image);
        firebase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        editor = sharedPreferences.edit();
//        editor.clear();
        check= (CheckBox)rootView.findViewById(R.id.check);
        email = (EditText) rootView.findViewById(R.id.email);
        cnic = (EditText) rootView.findViewById(R.id.cnic);
        password = (TextInputEditText) rootView.findViewById(R.id.password);
        fname = (EditText) rootView.findViewById(R.id.father_name);
        name = (EditText) rootView.findViewById(R.id.name);
        address = (EditText) rootView.findViewById(R.id.address);
        cell_no = (EditText) rootView.findViewById(R.id.cell_no);
        verified = (Button)rootView.findViewById(R.id.verified);

        completeView = getActivity().getLayoutInflater().inflate(R.layout.number_verified, null);
        show_token = new Dialog(getActivity());
        show_token.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        show_token.setContentView(completeView);
        show_token.setCancelable(false);

        cancle = (Button)completeView.findViewById(R.id.cancle);
        verfiy_code = (Button)completeView.findViewById(R.id.verfiy_code);
        code_area = (EditText)completeView.findViewById(R.id.code_area);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_token.dismiss();
            }
        });

        verfiy_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPhoneNumberWithCode(mVerificationId,code_area.getText().toString());
            }
        });

        verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(cell_no.getText().toString().equals("") || cell_no.getText().toString().length() <11 || cell_no.getText().toString().length() >11) {
                Snackbar.make(view,"Please Enter Valid Number",Snackbar.LENGTH_SHORT).show();
            }else{
                show_token.show();
                startPhoneNumberVerification(cell_no.getText().toString());
            }
            }
        });


        signup = (Button) rootView.findViewById(R.id.signup_btn);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //     profile_image  = (CircleImageView)rootView.findViewById(R.id.profile_img);
        //    spinner_country=(Spinner)rootView.findViewById(R.id.country_signup_spin);
        //   spinner_gender=(Spinner)rootView.findViewById(R.id.country_signup_gender);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
             //   updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
            //    signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    cell_no.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Toast.makeText(getActivity(), "Quota exceeded.",
                            Toast.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
             //   updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;


                // [END_EXCLUDE]
            }
        };



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass = password.getText().toString();
                //      final String confrim_passwordd = confirmpass.getText().toString();
                //Checking the length of pasword while registering new USER;
                if (pass.length() <= 6) {
                    main(pass);
                } else if ((fname.getText().toString().equals("") || !fname.getText().toString().matches("[a-zA-Z ]+")
                        || name.getText().toString().equals("") || !name.getText().toString().matches("[a-zA-Z ]+")
                        || cnic.getText().toString().equals("")
                        || pass.equals(""))) {
                    ///    || confrim_passwordd.equals("")) ){
                    Toast.makeText(getActivity(), "Fields Should not be left Empty", Toast.LENGTH_SHORT).show();
                } else if (email.getText().length() == 0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Enter Valid Email Address");
                } else if (fname.getText().length() == 0 || !fname.getText().toString().matches("[a-zA-Z ]+")) {
                    fname.setError("Invalid Name");
                } else if (name.getText().length() == 0 || !name.getText().toString().matches("[a-zA-Z ]+")) {
                    name.setError("Invalid Name");
                } else if (cell_no.getText().length() == 0) {
                  //  cell_no.setError("Invalid PHONE");
                    Snackbar.make(v,"Please Enter valid Number",Snackbar.LENGTH_SHORT).show();

                }else if(cnic.getText().toString().equals("") || cnic.getText().toString().length() <13 || cnic.getText().toString().length() >13){
                    Snackbar.make(v,"Please Enter CNIC Number",Snackbar.LENGTH_SHORT).show();

                }
                //Checking the length of pasword while registering new USER;
                else if (pass.length() <= 6) {
                    main(pass);
                }else if (!check.isChecked()){
                    Snackbar.make(v,"Please verified your number",Snackbar.LENGTH_SHORT).show();
                }

                // else if(imageURL==null) {
                //    Toast.makeText(getActivity(),"Upload Image",Toast.LENGTH_SHORT).show();
                //  }
                else {
                    try {
                        progressDialog = ProgressDialog.show(getActivity(), "Sign Up", "Connecting...", true, false);

                        mAuth.createUserWithEmailAndPassword((email.getText().toString()), (password.getText().toString())).addOnCompleteListener(getActivity(),
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), " " + task.getException(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            String uid = task.getResult().getUser().getUid();
                                            //                         String user_country = spinner_country.getSelectedItem().toString();
                                            //                       String user_gender = spinner_gender.getSelectedItem().toString();
                                            firebase.child("users").child(uid).setValue(new UserModel(name.getText().toString(), fname.getText().toString(), address.getText().toString(), email.getText().toString(), pass, cnic.getText().toString(), cell_no.getText().toString(),3,"",uid));
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Successfull", Toast.LENGTH_SHORT).show();
                                            AppLogs.logd("createUserWithEmail:onComplete: " + task.isSuccessful());
                                            firebase.child("ActivitySeen").child(uid).setValue(ServerValue.TIMESTAMP);

                                            if (getActivity().getSupportFragmentManager().findFragmentById(R.id.container) != null) {
                                                getActivity().getSupportFragmentManager()
                                                        .beginTransaction().
                                                        remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();
                                            }
//                                                } else

                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AppLogs.d("FailureSignup", e.getMessage());

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

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            check.setChecked(true);
                            show_token.dismiss();
                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                       //     updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                code_area.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                          //  updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }



    private void main(String pass) {

        Toast.makeText(getActivity(), pass + "\nYou Password is no longer Stronger \nPlease signup Again with \natleast 7 Character of Pasword.\nThanks ", Toast.LENGTH_SHORT).show();
    }
}
