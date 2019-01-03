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
    int id;
    DatabaseHelper helper;
    String visitDate;
    String visitHour;
    String doctor;
    String place;
    String info;
    String editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_visit_details);
        helper = new DatabaseHelper(this);
        editMode = getIntent().getStringExtra("editMode");
        Button addVisit = findViewById(R.id.saveVisitToDb);
        Button updateVisit = findViewById(R.id.button14);
        Button deleteVisit = findViewById(R.id.button15);
        if (editMode.equals("no")) {

            addVisit.setVisibility(View.VISIBLE);
            updateVisit.setVisibility(View.GONE);
            deleteVisit.setVisibility(View.GONE);
        }
        else if(editMode.equals("yes")){
            addVisit.setVisibility(View.GONE);
            updateVisit.setVisibility(View.VISIBLE);
            deleteVisit.setVisibility(View.VISIBLE);
        }
        TextView selectedD = findViewById(R.id.selectedDate);
        selectedD.setText(getIntent().getStringExtra("selectedDate"));
        TextView visitHour1 = findViewById(R.id.selectedHour);
        visitHour1.setText(getIntent().getStringExtra("hourOfVisit"));
        TextView doctor1 = findViewById(R.id.doctorsName);
        doctor1.setText(getIntent().getStringExtra("doctor"));
        TextView place1 = findViewById(R.id.placesName);
        place1.setText(getIntent().getStringExtra("place"));
        TextView info1 = findViewById(R.id.informationContent);
        info1.setText(getIntent().getStringExtra("info"));
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
            info = ((EditText)findViewById(R.id.informationContent)).getText().toString().trim();

            helper.insertNewVisit(visitDate, visitHour, doctor, place, info );

                Intent intent = new Intent(VisitDetails.this, Callendar.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        Button updateVisit1 = findViewById(R.id.button14);

        updateVisit1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                visitDate = ((TextView)findViewById(R.id.selectedDate)).getText().toString().trim();
                visitHour = ((TextView)findViewById(R.id.selectedHour)).getText().toString().trim();
                doctor = ((EditText)findViewById(R.id.doctorsName)).getText().toString().trim();
                place = ((EditText)findViewById(R.id.placesName)).getText().toString().trim();
                info = ((EditText)findViewById(R.id.informationContent)).getText().toString().trim();

                helper.updateVisit(visitDate, visitHour, doctor, place, info );

            }
        });
        Button deleteVisit1 = findViewById(R.id.button15);
        deleteVisit1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                visitDate = ((TextView)findViewById(R.id.selectedDate)).getText().toString().trim();
                visitHour = ((TextView)findViewById(R.id.selectedHour)).getText().toString().trim();
                doctor = ((EditText)findViewById(R.id.doctorsName)).getText().toString().trim();
                place = ((EditText)findViewById(R.id.placesName)).getText().toString().trim();
                info = ((EditText)findViewById(R.id.informationContent)).getText().toString().trim();

                helper.deleteVisit(visitDate, visitHour, doctor, place, info );
            }
        });

    }
}
