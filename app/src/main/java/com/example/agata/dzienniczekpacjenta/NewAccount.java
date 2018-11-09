package com.example.agata.dzienniczekpacjenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
    }

    public void saveDataForNewAccount(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
