package com.example.agata.dzienniczekpacjenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

     public void createAnAccount(View view){
         Intent intent = new Intent(this, NewAccount.class);
         startActivity(intent);
     }

     public void singIn(View view){
         Intent intent = new Intent(this, Home.class);
         startActivity(intent);
     }
}
