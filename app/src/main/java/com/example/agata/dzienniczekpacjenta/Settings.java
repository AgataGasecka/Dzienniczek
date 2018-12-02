package com.example.agata.dzienniczekpacjenta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void goToHomePage(View view){
        Intent intent = new Intent(this, Settings2.class);
        startActivity(intent);
    }
}
