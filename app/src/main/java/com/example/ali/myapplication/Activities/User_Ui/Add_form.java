package com.example.ali.myapplication.Activities.User_Ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.User_Ui.UserHomeFragment;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Sami Khan on 10/7/2017.
 */

public class Add_form extends android.support.v4.app.Fragment {

    public EditText name, cnic, childName, cell, drops, fatherName, fatherCnic, motherName, motherCnic, areaOfBirth, dateOfBirth, disability, address;
    public CheckBox yes, no, male, female;
    public Button submit, addLocation;
    public DatabaseReference ref;
    public String randomNumber;
    public static LatLng location;
    public Spinner relation, religion, district;
    public ArrayAdapter<String> relationAdapter, religionAdapter, districtAdapter;
    public String relations[] = {"Father", "Mother", "Sister", "Brother", "Cousin"};
    public String religions[] = {"Islam", "Ahmadiyya"};
    public String districts[] = {"East District", "Central District", "West District", "Korangi District", "Malir District"};
    public Calendar myCalendar;
    private int age = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.polio_form, null);
        init();
        cast(view);
        clickListeners();


        return view;
    }

    private void init() {
        ref = FirebaseHandler.getInstance().getAdd_forms();
    }


    public boolean checkEmptyFields() {

        boolean flag = true;
        if (cnic.getText().toString().length() == 0) {
            cnic.setError("Enter Applicant Cnic");
            flag = false;
        }
        if (name.getText().toString().length() == 0) {
            name.setError("Enter Applicant Name");
            flag = false;
        }
        if (childName.getText().toString().length() == 0) {
            childName.setError("Enter Child Name");
            flag = false;
        }
//        if (relation.getText().toString().length() == 0) {
//            flag = false;
//            relation.setError("Enter Relation");
//        }
//        if (religion.getText().toString().length() == 0) {
//            religion.setError("Enter Religion ");
//            flag = false;
//        }
        if (fatherCnic.getText().toString().length() == 0) {
            fatherCnic.setError("Enter Father Cnic");
            flag = false;
        }
        if (fatherName.getText().toString().length() == 0) {
            flag = false;
            fatherName.setError("Enter Father Name");
        }
        if (motherName.getText().toString().length() == 0) {
            flag = false;
            motherName.setError("Enter Mother Name");
        }
        if (motherCnic.getText().toString().length() == 0) {
            flag = false;
            motherCnic.setError("Enter Mother Cnic");
        }
        if (areaOfBirth.getText().toString().length() == 0) {
            flag = false;
            areaOfBirth.setError("Enter Area Of Birth");
        }
        if (dateOfBirth.getText().toString().length() == 0) {
            flag = false;
            dateOfBirth.setError("Enter Date Of Birth");
        }
        if (address.getText().toString().length() == 0) {
            flag = false;
            address.setError("Enter Address");
//            address.requestFocus();
        }
        if (cell.getText().toString().length() == 0) {
            flag = false;
            cell.setError("Enter Contact Number");
//            address.requestFocus();
        }
//        if (district.getText().toString().length() == 0) {
//            district.setError("Enter District");
//            flag = false;
////            district.requestFocus();
//        }
        if (!drops.getText().toString().isEmpty()) {
            if (drops.getText().toString().length() != 0) {
                if (Integer.parseInt(drops.getText().toString()) > 10) {
                    drops.setError("No Of Drops Not Greater Than 10");
                    flag = false;
                }
            } else {
                drops.setError("No Of Drops Should Not Kept Empty");
                flag = false;
            }


        }
        return flag;
    }

    private void clickListeners() {
        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    no.setChecked(false);
                } else {
                    no.setChecked(true);
                }
            }
        });
        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    yes.setChecked(false);
                } else {
                    yes.setChecked(true);
                }
            }
        });
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    female.setChecked(false);
                } else {
                    female.setChecked(true);
                }
            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    male.setChecked(false);
                } else {
                    male.setChecked(true);
                }
            }
        });
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new AddFormLocation()).addToBackStack(null).commit();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location != null && checkEmptyFields()) {
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Wait While Processing");
                    progressDialog.show();
                    if (age < 5) {
                        if (drops.getText().toString().isEmpty()) {
                            Snackbar.make(v, "No Of Drops Should Not Kept Empty", Snackbar.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {

                            Random random = new Random();
                            randomNumber = generateRandom();
                            BForm bForm = getFormData();
                            bForm.setLat(location.latitude);
                            bForm.setLng(location.longitude);
                            int i = age * 2;
                            if (i != 0) {
                                if (Integer.parseInt(drops.getText().toString()) > i) {
                                    Snackbar.make(v, "Invalid No Of Vaccinated Before", Snackbar.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else {
                                    bForm.setDrops(Integer.parseInt(drops.getText().toString()));
                                    progressDialog.dismiss();
                                    add(bForm, progressDialog);

                                }
                            } else {
                                if (Integer.parseInt(drops.getText().toString()) > 1) {
                                    Snackbar.make(v, "Invalid No Of Vaccinated Before", Snackbar.LENGTH_SHORT).show();
                               progressDialog.dismiss();
                                } else {
                                    bForm.setDrops(Integer.parseInt(drops.getText().toString()));
                                    add(bForm,progressDialog);
                                }
                            }
                        }
                    } else {
                        Snackbar.make(v, "Child Age Should Be Less Than 5", Snackbar.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } else {

                    if (location == null) {
                        Snackbar.make(v, "Add Location", Snackbar.LENGTH_SHORT).show();

                    } else {
                        Snackbar.make(v, "Empty Fields Not Allowed", Snackbar.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    public void add(BForm bForm, final ProgressDialog progressDialog) {
        if (cell.getText().toString().length() != 11) {
            Snackbar.make(submit, "Cell Number Should Be 11 Numbers", Snackbar.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            ref.child(randomNumber).setValue(bForm, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if (getActivity().getSupportFragmentManager().findFragmentById(R.id.maincontainer) != null) {
                        Utils.toast(getActivity(), "Sucessfully Submitted");
                        progressDialog.dismiss();

                        getActivity().getSupportFragmentManager()
                                .beginTransaction().
                                remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.maincontainer)).commit();

                    }
                    location = null;
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.maincontainer, new UserHomeFragment())
                            .commit();

                }
            });

        }


    }

    //
    public BForm getFormData() {

        randomNumber = generateRandom();
        BForm bForm = new BForm();
        bForm.setUserName(name.getText().toString());
        bForm.setApplicantCnic(cnic.getText().toString());
        bForm.setChildName(childName.getText().toString());
        bForm.setRelation(relation.getSelectedItem().toString());
        if (male.isChecked()) {
            bForm.setGender("Male");
        } else if (female.isChecked()) {
            bForm.setGender("Female");
        }
        bForm.setReligion(religion.getSelectedItem().toString());
        bForm.setFatherName(fatherName.getText().toString());
        bForm.setFatherCnic(fatherCnic.getText().toString());
        bForm.setMotherName(motherName.getText().toString());
        bForm.setMotherCnic(motherCnic.getText().toString());
        bForm.setAreaOfBirth(areaOfBirth.getText().toString());
        bForm.setDateOfBirth(myCalendar.getTimeInMillis() + "");
        bForm.setVacinationDate(System.currentTimeMillis());
        bForm.setUser_uid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        bForm.setForm_status("Applied");
        if (yes.isChecked()) {
            bForm.setVacinated(true);
        } else {
            bForm.setVacinated(false);
        }
        bForm.setDisablity(disability.getText().toString());
        bForm.setAddress(address.getText().toString());
        bForm.setDistrict(district.getSelectedItem().toString());
        bForm.setFormID(randomNumber);
        bForm.setCell(cell.getText().toString());
        bForm.setTimestamp(System.currentTimeMillis());
        return bForm;

    }

    private void cast(View view) {
        name = (EditText) view.findViewById(R.id.editTextApplicantName);
        cnic = (EditText) view.findViewById(R.id.editTextApplicantCnic);
        childName = (EditText) view.findViewById(R.id.editTextChildName);
        relation = (Spinner) view.findViewById(R.id.editTextRelation);
        religion = (Spinner) view.findViewById(R.id.TextReligion);
        fatherName = (EditText) view.findViewById(R.id.editTextFatherName);
        fatherCnic = (EditText) view.findViewById(R.id.editTextFatherCnic);
        motherName = (EditText) view.findViewById(R.id.editTextMotherName);
        motherCnic = (EditText) view.findViewById(R.id.editTextMotherCnic);
        areaOfBirth = (EditText) view.findViewById(R.id.editTextAreaOfBirth);
        dateOfBirth = (EditText) view.findViewById(R.id.editTextDateOfBirth);
        disability = (EditText) view.findViewById(R.id.editTextDisability);
        address = (EditText) view.findViewById(R.id.editTextAddress);
        cell = (EditText) view.findViewById(R.id.editTextCell);
        district = (Spinner) view.findViewById(R.id.editTextDistrict);
        submit = (Button) view.findViewById(R.id.submitForm);
        Utils.relwayRegular(getActivity(),submit);
        addLocation = (Button) view.findViewById(R.id.addLocation);

        Utils.relwayRegular(getActivity(),addLocation);
        yes = (CheckBox) view.findViewById(R.id.checkBoxYes);
        no = (CheckBox) view.findViewById(R.id.checkBoxNo);
        male = (CheckBox) view.findViewById(R.id.checkBoxMale);
        female = (CheckBox) view.findViewById(R.id.checkBoxFemale);
        drops = (EditText) view.findViewById(R.id.editTextDrops);
        drops.setText("0");
        relationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, relations);
        relation.setAdapter(relationAdapter);

        religionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, religions);
        religion.setAdapter(religionAdapter);

        districtAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, districts);
        district.setAdapter(districtAdapter);


        relation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }
            }
        });

        religion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        myCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                //   view.setMinDate(System.currentTimeMillis());
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Calendar calendar = Calendar.getInstance();
                age = calendar.get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR);
                Utils.toast(getActivity(), "" + age);
                updateLabel();
            }

        };

        dateOfBirth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//your co

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));

                    //          datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();

                    return true;

                }


                return false;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Toast.makeText(getActivity(), "Got It", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String generateRandom() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random rnd = new Random();
        char rndNumber;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++)
            sb.append(chars[rnd.nextInt(chars.length)]);

        return sb.toString();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateOfBirth.setText(sdf.format(myCalendar.getTime()));
    }
}
