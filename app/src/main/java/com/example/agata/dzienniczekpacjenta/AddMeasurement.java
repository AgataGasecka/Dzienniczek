package com.example.agata.dzienniczekpacjenta;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddMeasurement extends AppCompatActivity {

    DatabaseHelper controllerdb = new DatabaseHelper(this);
    SQLiteDatabase db;
    private ArrayList<String> email = new ArrayList<String>();
    private ArrayList<String> password = new ArrayList<String>();
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);
        //lv = (ListView) findViewById(R.id.listview);
        Button btnAdd = (Button) findViewById(R.id.dodajpomiar);
        Button btnView = (Button) findViewById(R.id.wyswietl_pomiary);
        final EditText dataPomiaru =(EditText) findViewById(R.id.dataPomiaru);
        final EditText godzinaPomiaru =(EditText) findViewById(R.id.godzinaPomiaru);
        final EditText wynikPomiaru =(EditText) findViewById(R.id.wynikPomiaru);

            btnAdd.setOnClickListener(new View.OnClickListener(){
              public void onClick(View v){
                  String userid = "";
                  String data = dataPomiaru.getText().toString();
                  String godzina = godzinaPomiaru.getText().toString();
                  String wynik = wynikPomiaru.getText().toString();
                  if (data.length() !=0 & godzina.length() !=0 & wynik.length() !=0){
                      AddData(userid,data,godzina,wynik);
                      dataPomiaru.setText("");
                      godzinaPomiaru.setText("");
                      wynikPomiaru.setText("");
                  } else {
                      toastMessage("Nie umieściłeś danych w polach!");
                  }
              }
            });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListMeasurementDataActivity.class);
                startActivity(intent);
            }
        });
    }


    public void AddData(String userid, String data, String hour, String measurement) {
        boolean insertData = controllerdb.addMeasurementData(userid,data,hour, measurement);

        if (insertData) {
            toastMessage("Pomiar został dodany!");
        } else {
            toastMessage("Coś poszło nie tak");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}