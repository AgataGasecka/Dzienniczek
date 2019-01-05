package com.example.agata.dzienniczekpacjenta;

import android.app.AlertDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class Home extends AppCompatActivity {

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_home);
    }

    public void goToSettings(View view){
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    public void goToCalendar(View view){
        Intent intent = new Intent(this, Callendar.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    public void goToAddMeasurement(View view){
        Intent intent = new Intent(this, AddMeasurement.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    public void goToListMeasurementDataActivity(View view){
        Intent intent = new Intent(this, ListMeasurementDataActivity.class);
        startActivity(intent);
    }

    public void goToAddDrugs(View view){
        Intent intent = new Intent(this, AddDrug.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
