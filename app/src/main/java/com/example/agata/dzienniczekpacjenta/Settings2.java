package com.example.agata.dzienniczekpacjenta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class Settings2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        Spinner spinnerPatameter = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.parameters_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatameter.setAdapter(adapter);

        Spinner spinnerVisits = findViewById(R.id.spinner3);
        Spinner spinnerMedicines = findViewById(R.id.spinner4);
        Spinner spinnerMeasurements = findViewById(R.id.spinner5);

        ArrayAdapter<CharSequence> adapterMedicines = ArrayAdapter.createFromResource(this,
                R.array.reminders_array, android.R.layout.simple_spinner_item);

        adapterMedicines.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVisits.setAdapter(adapterMedicines);
        spinnerMedicines.setAdapter(adapterMedicines);
        spinnerMeasurements.setAdapter(adapterMedicines);
    }

    public void setNewNorm(View view){

        EditText newNorm = findViewById(R.id.editText7);
        newNorm.setVisibility(View.VISIBLE);
    }

    public void setReminderAboutVisits(View view){
        CheckBox remindAboutVisitCheckbox = findViewById(R.id.checkBox);
        Spinner whenToRemindAboutVisit = findViewById(R.id.spinner3);
        if(remindAboutVisitCheckbox.isChecked())
            whenToRemindAboutVisit.setVisibility(View.VISIBLE);
        else if(!remindAboutVisitCheckbox.isChecked())
            whenToRemindAboutVisit.setVisibility(view.GONE);
    }

    public void setReminderAboutMedicines(View view){
        CheckBox remindAboutMedicinesCheckbox = findViewById(R.id.checkBox2);
        Spinner whenToRemindAboutMedicines = findViewById(R.id.spinner4);
        if(remindAboutMedicinesCheckbox.isChecked())
            whenToRemindAboutMedicines.setVisibility(View.VISIBLE);
        else if(!remindAboutMedicinesCheckbox.isChecked())
            whenToRemindAboutMedicines.setVisibility(View.GONE);
    }
    public void setReminderAboutMeasurement(View view){
        CheckBox remindAboutMeasurementCheckbox = findViewById(R.id.checkBox3);
        Spinner whenToRemindAboutMeasurement = findViewById(R.id.spinner5);
        if(remindAboutMeasurementCheckbox.isChecked())
            whenToRemindAboutMeasurement.setVisibility(View.VISIBLE);
        else if(!remindAboutMeasurementCheckbox.isChecked())
            whenToRemindAboutMeasurement.setVisibility(View.GONE);
    }

}
