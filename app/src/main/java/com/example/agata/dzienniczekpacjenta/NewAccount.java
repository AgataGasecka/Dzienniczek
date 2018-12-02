package com.example.agata.dzienniczekpacjenta;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewAccount extends AppCompatActivity {
    DatabaseHelper helper;
    String email;
    String password;
    String repeatPassword;
    String name;
    String surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        helper = new DatabaseHelper(this);
    }

    public void saveDataForNewAccount(View view){
        email = ((EditText)findViewById(R.id.email)).getText().toString();
        password = ((EditText)findViewById(R.id.password)).getText().toString();
        repeatPassword = ((EditText)findViewById(R.id.repeatPassword)).getText().toString();
        name = ((EditText)findViewById(R.id.name)).getText().toString();
        surname = ((EditText)findViewById(R.id.surname)).getText().toString();

        if(repeatPassword.equals(password)) {
            helper.insertNewUser(email, password);
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }
    }

    private void showAlert(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Rejestracja nie powiodła się")
                .setMessage("Istnieje juz użytkownik o podanych adresie email")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
