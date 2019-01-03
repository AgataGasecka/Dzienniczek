package com.example.agata.dzienniczekpacjenta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Settings2 extends AppCompatActivity {
    int id;
    String measurement_type;
    String measurement_standard;
    EditText newNorm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_settings2);

        newNorm = findViewById(R.id.editText7);
        Spinner spinnerPatameter = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.parameters_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatameter.setAdapter(adapter);

        spinnerPatameter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Settings2.this, "Wybrano opcję" + (parent.getItemAtPosition(position).toString()), Toast.LENGTH_SHORT).show();
                measurement_type=parent.getItemAtPosition(position).toString();
                setMeasurementStandard();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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


    private void setMeasurementStandard(){
        TextView standard = (TextView) findViewById(R.id.normaPomiaru);

        switch(measurement_type){
            case "Ciśnienie":
                measurement_standard = "120/80";
                standard.setText(measurement_standard + " mmHg");
                break;
            case "Cukier":
                measurement_standard = "70-100";
                standard.setText(measurement_standard + " mg/dl");
                break;
            case "Waga":
                standard.setText("Wpisz swoją normę");
                break;
            case "Temperatura":
                measurement_standard = "36.6";
                standard.setText(measurement_standard + " ℃");
                break;
            case "Puls":
                measurement_standard = "70";
                standard.setText(measurement_standard + " uderzeń/min");
                break;
            default:
                break;
        }
    }

    public void setNewNorm(View view){
        newNorm.setVisibility(View.VISIBLE);
        newNorm.setHint("Nowa norma");
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

    private void getInfo(){
        measurement_standard = newNorm.getText().toString();
    }

}
