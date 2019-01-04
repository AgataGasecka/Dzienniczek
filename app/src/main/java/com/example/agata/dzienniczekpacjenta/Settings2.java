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

    DatabaseHelper helper;
    int id;
    String measurement_type;
    String measurement_standard = "";
    String remindAboutVisit;
    String remindAboutMedicines;
    String remindAboutMeasurement;
    EditText newNorm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_settings2);
        helper = new DatabaseHelper(this);
        newNorm = findViewById(R.id.editText7);

        Spinner spinnerPatameter = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.parameters_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatameter.setAdapter(adapter);

        spinnerPatameter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Settings2.this, "Wybrano opcję " + (parent.getItemAtPosition(position).toString()), Toast.LENGTH_SHORT).show();
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

        if(remindAboutVisitCheckbox.isChecked()) {
            whenToRemindAboutVisit.setVisibility(View.VISIBLE);
            whenToRemindAboutVisit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(Settings2.this, "Wybrano opcję " + (parent.getItemAtPosition(position).toString()), Toast.LENGTH_SHORT).show();
                    remindAboutVisit=parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else if(!remindAboutVisitCheckbox.isChecked()) {
            whenToRemindAboutVisit.setVisibility(view.INVISIBLE);
            remindAboutVisit="";
        }
    }

    public void setReminderAboutMedicines(View view){
        CheckBox remindAboutMedicinesCheckbox = findViewById(R.id.checkBox2);
        Spinner whenToRemindAboutMedicines = findViewById(R.id.spinner4);

        if(remindAboutMedicinesCheckbox.isChecked()) {
            whenToRemindAboutMedicines.setVisibility(View.VISIBLE);
            whenToRemindAboutMedicines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(Settings2.this, "Wybrano opcję " + (parent.getItemAtPosition(position).toString()), Toast.LENGTH_SHORT).show();
                    remindAboutMedicines=parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else if(!remindAboutMedicinesCheckbox.isChecked()) {
            whenToRemindAboutMedicines.setVisibility(View.INVISIBLE);
            remindAboutMedicines="";
        }
    }
    public void setReminderAboutMeasurement(View view){
        CheckBox remindAboutMeasurementCheckbox = findViewById(R.id.checkBox3);
        Spinner whenToRemindAboutMeasurement = findViewById(R.id.spinner5);

        if(remindAboutMeasurementCheckbox.isChecked()) {
            whenToRemindAboutMeasurement.setVisibility(View.VISIBLE);
            whenToRemindAboutMeasurement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(Settings2.this, "Wybrano opcję " + (parent.getItemAtPosition(position).toString()), Toast.LENGTH_SHORT).show();
                    remindAboutMeasurement=parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else if(!remindAboutMeasurementCheckbox.isChecked()) {
            whenToRemindAboutMeasurement.setVisibility(View.INVISIBLE);
            remindAboutMeasurement="";
        }
    }

    public void saveInfo(View view){
        boolean correctly = false;

        if(newNorm.isEnabled() && !(newNorm.getText().toString() == ""))
            measurement_standard = newNorm.getText().toString();

        if(measurement_type.equals("Waga") && measurement_standard == "" ) {
            Toast.makeText(Settings2.this, "Nie podano normy wagi!", Toast.LENGTH_SHORT).show();
        }
        else if(measurement_standard == ""){
            Toast.makeText(Settings2.this, "Nie podano normy!", Toast.LENGTH_SHORT).show();
        }
        else{
            correctly = helper.insertUserSettings(id, measurement_type, measurement_standard);
        }

        if(correctly){
            Toast.makeText(Settings2.this, "Zapisano w bazie", Toast.LENGTH_SHORT).show();
            newNorm.setText("");
            newNorm.setVisibility(View.INVISIBLE);
            measurement_standard = "";
        }
        else{
            Toast.makeText(Settings2.this, "Błąd", Toast.LENGTH_SHORT).show();
        }
    }

}
