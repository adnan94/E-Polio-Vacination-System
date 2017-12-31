package com.example.ali.myapplication.Activities.Admin_Ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.ali.myapplication.Activities.Adaptor.RangeTimePickerDialog;
import com.example.ali.myapplication.Activities.Adaptor.Team_visitDate;
import com.example.ali.myapplication.Activities.ModelClasses.Polio_Schedule;
import com.example.ali.myapplication.Activities.ModelClasses.Polio_Team;
import com.example.ali.myapplication.Activities.ModelClasses.Visit_DateObject;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sami Khan on 12/10/2017.
 */

public class AddScheduleFragment extends android.support.v4.app.Fragment {

    public ImageView back_arrow;
    public TextView ActionBartitle;
    public EditText schedule_from, schedule_to, schedule_title, schedule_des;
    public LinearLayout add_visit_dates;
  //  public Dialog filter_dialog;
    public Button save_schedule;
    public DatabaseReference databaseReference;
    public String key = "";
    public Calendar myCalendarStart, myCalendarEnd;
    DatePickerDialog datePickerDialogStart;
    DatePickerDialog datePickerDialogEnd;
    DatePickerDialog datePickerDialogvisit;
    public Calendar time_fromm;
    public EditText visit_date, time_from, time_to;
    public Polio_Schedule polio_schedule;
    public Calendar dialog_calender;
    Calendar now;
    public ListView visit_dates;
    public ArrayList<Visit_DateObject> visit_dateObjects;
    public Team_visitDate team_visitDateAdapter;
    public ProgressDialog progressDialog;
    AlertDialog alertDialog;
    public TextView add_to_schdeule,from,too,schedule_t,sceh_des,datess;
    public int min_from,min_to,max_for,max_to;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_schedule, null);
            key = FirebaseHandler.getInstance().getPolio_schedule().push().getKey();
        initView(view);

        myCalendarStart = Calendar.getInstance();
        myCalendarEnd = Calendar.getInstance();

        if(getArguments()!=null) {
            if (getArguments().getParcelable("obj") != null) {
                polio_schedule = getArguments().getParcelable("obj");
                schedule_to.setText(polio_schedule.getSchedule_to());
                schedule_des.setText(polio_schedule.getSchedule_des());
                schedule_title.setText(polio_schedule.getSchedule_titile());
                schedule_from.setText(polio_schedule.schedule_from);
                key = polio_schedule.getSchedule_id();
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                //        Date d = new Date(polio_schedule.getSchedule_to());
                try {
                    Date date = sdf.parse(polio_schedule.getSchedule_to());
                    myCalendarEnd.setTime(date);
                    Date datee = sdf.parse(polio_schedule.getSchedule_from());
                    myCalendarStart.setTime(datee);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //   Date dd = new Date(polio_schedule.getSchedule_from());
                //  Toast.makeText(getActivity(),""+d,Toast.LENGTH_SHORT).show();


            }
        }

        FirebaseHandler.getInstance().getPolio_subSchedule()
                .child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if(dataSnapshot.getValue()!=null){
                        visit_dateObjects.clear();
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            Visit_DateObject visit_dateObject = data.getValue(Visit_DateObject.class);
                            visit_dateObjects.add(visit_dateObject);
                            team_visitDateAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }

        };

        final DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelend();
            }

        };


        schedule_from.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//your co
                    datePickerDialogStart = new DatePickerDialog(getActivity(), dateStart, myCalendarStart
                            .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                            myCalendarStart.get(Calendar.DAY_OF_MONTH));
                    if (datePickerDialogEnd != null) {

                        int day = datePickerDialogEnd.getDatePicker().getDayOfMonth();
                        int month = datePickerDialogEnd.getDatePicker().getMonth();
                        int year = datePickerDialogEnd.getDatePicker().getYear();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        datePickerDialogStart.getDatePicker().setMaxDate(calendar.getTimeInMillis() - 10000);

                    }

                    datePickerDialogStart.show();

                    return true;

                }
                return false;

            }
        });

        schedule_to.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    datePickerDialogEnd = new DatePickerDialog(getActivity(), dateEnd, myCalendarEnd
                            .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                            myCalendarEnd.get(Calendar.DAY_OF_MONTH));
                    if (datePickerDialogStart != null) {
                        int day = datePickerDialogStart.getDatePicker().getDayOfMonth();
                        int month = datePickerDialogStart.getDatePicker().getMonth();
                        int year = datePickerDialogStart.getDatePicker().getYear();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        datePickerDialogEnd.getDatePicker().setMinDate(calendar.getTimeInMillis() + 10000);
                    }
                    datePickerDialogEnd.show();


                    return true;
                }
                return false;
            }
        });


        return view;
    }

    private void initView(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        now = Calendar.getInstance();
        visit_dateObjects = new ArrayList<>();
        visit_dates = (ListView)view.findViewById(R.id.visit_dates);
        //   back_arrow.setVisibility(View.INVISIBLE);
        back_arrow.setImageResource(R.mipmap.menu);
        ActionBartitle.setText("Add Polio Schedule");
        Utils.relwayMedium(getActivity(),ActionBartitle);
        save_schedule = (Button) view.findViewById(R.id.save_schedule);
        Utils.relwayRegular(getActivity(),save_schedule);
        schedule_from = (EditText) view.findViewById(R.id.schedule_from);
        Utils.relwayRegular(getActivity(),schedule_from);
        schedule_to = (EditText) view.findViewById(R.id.schedule_to);
        Utils.relwayRegular(getActivity(),schedule_to);
        schedule_title = (EditText) view.findViewById(R.id.schedule_title);
        Utils.relwayRegular(getActivity(),schedule_title);
        schedule_des = (EditText) view.findViewById(R.id.schedule_des);
        Utils.relwayRegular(getActivity(),schedule_des);
        team_visitDateAdapter = new Team_visitDate(visit_dateObjects,getActivity());
        visit_dates.setAdapter(team_visitDateAdapter);

        add_to_schdeule = (TextView)view.findViewById(R.id.add_to_schdeule);
        Utils.relwayRegular(getActivity(),add_to_schdeule);

        from = (TextView)view.findViewById(R.id.from);
        Utils.relwayRegular(getActivity(),from);
        too = (TextView)view.findViewById(R.id.too);
        Utils.relwayRegular(getActivity(),too);
        sceh_des = (TextView)view.findViewById(R.id.sceh_des);
        Utils.relwayRegular(getActivity(),sceh_des);
        schedule_t = (TextView)view.findViewById(R.id.schedule_t);
        Utils.relwayRegular(getActivity(),schedule_t);

        datess = (TextView)view.findViewById(R.id.datess);
        Utils.relwaySemiBold(getActivity(),datess);

    //    filter_dialog = new Dialog(getActivity());
    //    filter_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    //    filter_dialog.setContentView(completeView);

        add_visit_dates = (LinearLayout) view.findViewById(R.id.add_visit_dates);
        dialog_calender = Calendar.getInstance();

         alertDialog = new AlertDialog.Builder(
                getActivity()).create();
        View completeView = getActivity().getLayoutInflater().inflate(R.layout.add_visit_date, null);
        Button btn = (Button) completeView.findViewById(R.id.save_time);
        Utils.relwayRegular(getActivity(),btn);
        visit_date = (EditText) completeView.findViewById(R.id.visit_date);
        Utils.relwayRegular(getActivity(),visit_date);
        time_from = (EditText) completeView.findViewById(R.id.time_from);
        Utils.relwayRegular(getActivity(),time_from);
        time_to = (EditText) completeView.findViewById(R.id.time_to);
        Utils.relwayRegular(getActivity(),time_to);
        alertDialog.setView(completeView);


        final DatePickerDialog.OnDateSetListener dialog_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                dialog_calender.set(Calendar.YEAR, year);
                dialog_calender.set(Calendar.MONTH, monthOfYear);
                dialog_calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDialaog();
            }

        };


        visit_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//your co
                    datePickerDialogvisit = new DatePickerDialog(getActivity(), dialog_date, dialog_calender
                            .get(Calendar.YEAR), dialog_calender.get(Calendar.MONTH),
                            dialog_calender.get(Calendar.DAY_OF_MONTH));
                    if (datePickerDialogStart != null) {
                        int day = datePickerDialogStart.getDatePicker().getDayOfMonth();
                        int month = datePickerDialogStart.getDatePicker().getMonth();
                        int year = datePickerDialogStart.getDatePicker().getYear();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        datePickerDialogvisit.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    }
                    if (datePickerDialogEnd != null) {

                        int day = datePickerDialogEnd.getDatePicker().getDayOfMonth();
                        int month = datePickerDialogEnd.getDatePicker().getMonth();
                        int year = datePickerDialogEnd.getDatePicker().getYear();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        datePickerDialogvisit.getDatePicker().setMaxDate(calendar.getTimeInMillis() +10000);

                    }

                    datePickerDialogvisit.show();

                    return true;

                }
                return false;

            }
        });

        time_fromm = Calendar.getInstance();

        time_from.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {


                    int hour = time_fromm.get(Calendar.HOUR_OF_DAY);
                    int minute = time_fromm.get(Calendar.MINUTE);
                    RangeTimePickerDialog mTimePicker;
                    mTimePicker = new RangeTimePickerDialog(getActivity(), new RangeTimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            time_from.setText(selectedHour + ":" + selectedMinute);
                            min_from = selectedHour;
                            min_to = selectedMinute;
                            time_to.setEnabled(true);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
            //        mTimePicker.setMin(time_fromm.get(Calendar.HOUR_OF_DAY),time_fromm.get(Calendar.MINUTE));
            //        mTimePicker.setMax(max_for,max_to);
                    mTimePicker.show();

                    return true;
                }

                return false;
            }
        });

        time_to.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    RangeTimePickerDialog mTimePicker;
                    mTimePicker = new RangeTimePickerDialog(getActivity(), new RangeTimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                             time_to.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.setMin(min_from,min_to);
             //       mTimePicker.setMax(max_for,max_to);
                    mTimePicker.show();

                return true;
                }
                return false;
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String visit_key = FirebaseHandler.getInstance().getPolio_subSchedule().child(key)
                        .push().getKey();

                Visit_DateObject visit_dateObject = new Visit_DateObject(visit_date.getText().toString(),time_from.getText().toString(),
                        time_to.getText().toString(),visit_key,key);

                visit_dateObjects.add(visit_dateObject);

                FirebaseHandler.getInstance().getPolio_subSchedule()
                        .child(key)
                        .child(visit_key)
                        .setValue(visit_dateObject, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                alertDialog.dismiss();
                            }
                        });


            }
        });


        add_visit_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(schedule_from.getText().toString().length()==0 || schedule_from.getText().toString().equals("")){
                    schedule_from.setError("Please Enter From Date");
                }else if(schedule_to.getText().toString().length()==0 || schedule_to.getText().toString().equals("")){
                    schedule_to.setError("Please Enter to Date");
                }else {
                    alertDialog.show();
                }
            }
        });

        save_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = ProgressDialog.show(getActivity(), "Add Schedule", "Adding...", true, false);


                if (schedule_from.getText().toString().equals("") || schedule_from.getText().toString().length() == 0) {
                    schedule_from.setError("Enter Valid Date");
                    progressDialog.dismiss();
                } else if (schedule_to.getText().toString().equals("") || schedule_to.getText().toString().length() == 0) {
                    schedule_to.setError("Enter Valid Date");
                    progressDialog.dismiss();
                } else if (schedule_des.getText().toString().equals("") || schedule_des.getText().toString().length() == 0) {
                    schedule_des.setError("Enter Description");
                    progressDialog.dismiss();
                } else if (schedule_title.getText().toString().equals("") || schedule_title.getText().toString().length() == 0) {
                    schedule_title.setText("Enter Title");
                    progressDialog.dismiss();
                } else {


                    Polio_Schedule polioSchedule = new Polio_Schedule(schedule_from.getText().toString(), schedule_to.getText().toString(),
                            schedule_des.getText().toString(), schedule_title.getText().toString(), key);


                    FirebaseHandler.getInstance().getPolio_schedule()
                            .child(key).setValue(polioSchedule, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            progressDialog.dismiss();
                            getActivity().getSupportFragmentManager().popBackStack();

                        }
                    });
                }
            }
        });



    }

    private void updateLabelDialaog() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        // schedule_from.setTag(myCalendarStart.getTimeInMillis());
        visit_date.setText(sdf.format(dialog_calender.getTime()));
        time_from.setEnabled(true);
    }

    private void updateLabelStart() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        // schedule_from.setTag(myCalendarStart.getTimeInMillis());
        schedule_from.setText(sdf.format(myCalendarStart.getTime()));
     //   time_to.setEnabled(true);
    }

    private void updateLabelend() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        schedule_to.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public Boolean updateTime(int minute, int hourOfDay) throws ParseException {
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", Locale.getDefault());
        SimpleDateFormat secondFormat = new SimpleDateFormat("ss", Locale.getDefault());
        int hour = Integer.parseInt(hourFormat.format(now.getTime()));
        int minutes = Integer.parseInt(minuteFormat.format(now.getTime()));
        String seconds = (secondFormat.format(now.getTime()));
        //hour += 12;
        hour = hourOfDay - hour;
        minute = minute - minutes;
        if(minute < 0){
            minute += 60;
            hour -= 1;
        }
        if(hour >= 0) {
          //  this.hour.setText(Integer.toString(hour));
          //  this.minute.setText(Integer.toString(minute));
          //  this.second.setText(seconds);
            time_to.setText(Integer.toString(hour)+" : "+Integer.toString(minute)+" : "+seconds);

            return true;
        }
        else{
            Toast.makeText(getActivity(), "Invalid time selected",Toast.LENGTH_SHORT).show();
        }
        now = Calendar.getInstance();
        return false;
    }

}
