package com.example.agata.dzienniczekpacjenta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Callendar extends AppCompatActivity {

     int id;
     Date dateInCalendar = Calendar.getInstance().getTime();
     SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
     String date = df.format(dateInCalendar);
     String hour = "9:00";
     String doctor;
     String place;
     String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_callendar);


        CalendarView calendar = findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                 date = dayOfMonth + "-" + (month+1) + "-" + year;
                 hour = "9:00";
                 doctor = "";
                 place= "";
                 info ="";

            }
        });

        Button addVisit = findViewById(R.id.button10);
        addVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Callendar.this, VisitDetails.class);
                intent.putExtra("selectedDate",date);
intent.putExtra("hourOfVisit", hour);
 intent.putExtra("ID", id);

                intent.putExtra("doctor", doctor);
                intent.putExtra("place", place);
                intent.putExtra("info", info);
                intent.putExtra("editMode", "no");
                startActivity(intent);

            }
        });
    }

    public void goToVisitsList(View view){
        Intent intent = new Intent(this, ListOfVisits.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

}





