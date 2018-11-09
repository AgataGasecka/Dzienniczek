package com.example.agata.dzienniczekpacjenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SingIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
    }

    public void goToHomePage(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
