package com.example.ali.myapplication.Activities.Uc_Ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.ModelClasses.Form_Token;
import com.example.ali.myapplication.Activities.UI.FragmentEditForm;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFormForUc extends Fragment {

    public TextView name, cnic, childName, relation, religion, fatherName, fatherCnic, motherName, motherCnic, areaOfBirth, dateOfBirth, disability, address, district, gender, vacinated;
    Button edit;
    BForm bform;
    public Button change_status;
    public Dialog filter_dialog;
    public Spinner status_userform;
    public String status[]={"Applied","In-Progress","Completed"};
    public ArrayAdapter<String> statusAdapter;
    public Button form_status_apply,form_status_cancle;
    public EditText token_date;
    public EditText token_time;
    public Calendar myCalendar;


    public ViewFormForUc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_form_for_ui, container, false);
        cast(view);
        init();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("formData",bform);
                FragmentEditForm fragmentEditForm = new FragmentEditForm();
                fragmentEditForm.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_uc,fragmentEditForm).addToBackStack(null).commit();

            }
        });

        change_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_dialog.show();
            }
        });

        form_status_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_dialog.dismiss();
            }
        });



        form_status_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(bform!=null){
                   if(status_userform.getSelectedItem().toString().equals("In-Progress")) {
                       FirebaseHandler.getInstance().getAdd_forms().child(bform.getFormID()).child("form_status").setValue(status_userform.getSelectedItem().toString(), new DatabaseReference.CompletionListener() {
                           @Override
                           public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                               filter_dialog.dismiss();
                               DatabaseReference key = FirebaseHandler.getInstance().getForm_token().child(bform.getUser_uid()).child(bform.getFormID()).push();
                               key.setValue(new Form_Token(key.getKey(), bform.getFormID(),
                                       bform.getUserName(), bform.getApplicantCnic(),
                                       bform.getTimestamp(), token_date.getText().toString(),token_time.getText().toString(), ServerValue.TIMESTAMP));
                                getActivity().getSupportFragmentManager().popBackStack();
                           }
                       });
                   }else{
                       filter_dialog.dismiss();
                   }
               }
            }
        });




        return view;
    }

    private void init() {
        bform = getArguments().getParcelable("formData");
        if(bform!=null){
            name.setText("Applicant Name : "+bform.getUserName());
            cnic.setText("Applicant Cnic : "+bform.getApplicantCnic());
            childName.setText("Child Name : "+bform.getChildName());
            relation.setText("Relation : "+bform.getRelation());
            gender.setText("Gender : "+bform.getGender());
            religion.setText("Relation : "+bform.getReligion());
            fatherName.setText("Father Name : "+bform.getFatherName());
            fatherCnic.setText("Father Cnic : "+bform.getFatherCnic());
            motherName.setText("Mother Name : "+bform.getMotherName());
            motherCnic.setText("Mother Cnic : "+bform.getMotherCnic());
            areaOfBirth.setText("Area Of Birth : "+bform.getAreaOfBirth());
            dateOfBirth.setText("Date Of Birth : "+bform.getDateOfBirth());
            vacinated.setText("Vaccinated : "+bform.isVacinated());
            disability.setText("Disability : "+bform.getDisablity());
            address.setText("Address : "+bform.getAddress());
            district.setText("District : "+bform.getDistrict());

        }
    }

    private void cast(View view) {
        View completeView = getActivity().getLayoutInflater().inflate(R.layout.filter_dialog, null);
        name = (TextView) view.findViewById(R.id.applicantName);
        cnic = (TextView) view.findViewById(R.id.applicantCnic);
        childName = (TextView) view.findViewById(R.id.childName);
        relation = (TextView) view.findViewById(R.id.relation);
        gender = (TextView) view.findViewById(R.id.gender);
        religion = (TextView) view.findViewById(R.id.religion);
        fatherName = (TextView) view.findViewById(R.id.fatherName);
        fatherCnic = (TextView) view.findViewById(R.id.fatherCnic);
        motherName = (TextView) view.findViewById(R.id.motherName);
        motherCnic = (TextView) view.findViewById(R.id.motherCnic);
        areaOfBirth = (TextView) view.findViewById(R.id.areaOfBirth);
        dateOfBirth = (TextView) view.findViewById(R.id.dateOfBirth);
        vacinated = (TextView) view.findViewById(R.id.vacinated);
        disability = (TextView) view.findViewById(R.id.disability);
        address = (TextView) view.findViewById(R.id.address);
        district = (TextView) view.findViewById(R.id.district);
        edit=(Button)view.findViewById(R.id.edit);

        change_status = (Button)view.findViewById(R.id.change_status);


        filter_dialog = new Dialog(getActivity());
        filter_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        filter_dialog.setContentView(completeView);
        form_status_cancle = (Button)completeView.findViewById(R.id.form_status_cancle);
        form_status_apply= (Button)completeView.findViewById(R.id.form_status_apply);
        status_userform= (Spinner)completeView.findViewById(R.id.status_userform);
        token_date = (EditText)completeView.findViewById(R.id.token_date);
        token_time = (EditText)completeView.findViewById(R.id.token_time);
        token_date.setInputType(InputType.TYPE_NULL);
        token_time.setInputType(InputType.TYPE_NULL);
        statusAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,status);
        status_userform.setAdapter(statusAdapter);


         myCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                view.setMinDate(System.currentTimeMillis());
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };



        token_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//your co

                  DatePickerDialog datePickerDialog =   new DatePickerDialog(getActivity(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();

                    return true;

                }


                return false;
            }
        });

        token_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//your co
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            token_time.setText( selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                    return true;

                }

                return false;
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("formData",bform);
                FragmentEditForm fragmentEditForm = new FragmentEditForm();
                fragmentEditForm.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer_uc,fragmentEditForm).addToBackStack(null).commit();


    }
    });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        token_date.setText(sdf.format(myCalendar.getTime()));
    }

}
