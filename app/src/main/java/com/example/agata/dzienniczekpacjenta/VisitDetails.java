package com.example.agata.dzienniczekpacjenta;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class VisitDetails extends AppCompatActivity {

    DatabaseHelper helper;
    String visitDate;
    String visitHour;
    String doctor;
    String place;
    String information;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_details);
        helper = new DatabaseHelper(this);
        TextView selectedD = findViewById(R.id.selectedDate);
        selectedD.setText(getIntent().getStringExtra("selectedDate"));
        Button setVisitTime = findViewById(R.id.setHour);
        setVisitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(VisitDetails.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        TextView visitHour = findViewById(R.id.selectedHour);
                        visitHour.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Wybierz godzinę");
                mTimePicker.show();
            }
        });

        Button saveVisit = findViewById(R.id.saveVisitToDb);
        saveVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            visitDate = ((TextView)findViewById(R.id.selectedDate)).getText().toString().trim();
            visitHour = ((TextView)findViewById(R.id.selectedHour)).getText().toString().trim();
            doctor = ((EditText)findViewById(R.id.doctorsName)).getText().toString().trim();
            place = ((EditText)findViewById(R.id.placesName)).getText().toString().trim();
            information = ((EditText)findViewById(R.id.informationContent)).getText().toString().trim();

            helper.insertNewVisit(visitDate, visitHour, doctor, place, information );

                Intent intent = new Intent(VisitDetails.this, Callendar.class);
                startActivity(intent);
            }
        });
    }
}
