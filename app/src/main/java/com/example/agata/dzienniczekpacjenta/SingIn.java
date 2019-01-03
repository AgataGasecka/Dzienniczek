package com.example.agata.dzienniczekpacjenta;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SingIn extends AppCompatActivity {
    public String email;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
    }

    public void goToHomePage(View view){
        email = ((EditText)findViewById(R.id.editText)).getText().toString();
        password = ((EditText)findViewById(R.id.editText2)).getText().toString();
        int id = 0;
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(DatabaseHelper.USERS_TABLE, new String[]{"ID", "EMAIL", "PASSWORD"}, "EMAIL=\"" + email + "\" AND PASSWORD=\"" + password + "\"", null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex("ID"));
        }

        if(cursor.getCount()>0) {
            Intent intent = new Intent(this, Home.class);
            intent.putExtra("ID",id);
            startActivity(intent);
        }
        else {
            showAlert(); //do odkomentowania
            //TODO do usuniecia
            //start
            //Intent intent = new Intent(this, Home.class);
            //startActivity(intent);
            //koniec
        }
    }

    private void showAlert(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Logowanie nie powiodło się")
                .setMessage("Wprowadzono niepoprawny adres email lub haslo")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



}
