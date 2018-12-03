package com.example.agata.dzienniczekpacjenta;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewAccount extends AppCompatActivity {
    DatabaseHelper helper;
    String email;
    String password;
    String repeatPassword;
    String name;
    String surname;

    String title;
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        helper = new DatabaseHelper(this);
    }

    public void saveDataForNewAccount(View view){

        email = ((EditText)findViewById(R.id.email)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
        repeatPassword = ((EditText)findViewById(R.id.repeatPassword)).getText().toString().trim();
        name = ((EditText)findViewById(R.id.name)).getText().toString().trim();
        surname = ((EditText)findViewById(R.id.surname)).getText().toString().trim();

        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Pattern VALID_NAME_REGEX =
                Pattern.compile("^(?i)^(?:[a-z]+(?: |\\. ?)?)+[a-z]$", Pattern.CASE_INSENSITIVE);

        Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        Matcher nameMatcher = VALID_NAME_REGEX.matcher(name);
        Matcher surnameMatcher = VALID_NAME_REGEX.matcher(surname);

        //POTEM DODAM PODSWIETLENIA PÓL NA CZERWONO
        if ( !nameMatcher.find() | !surnameMatcher.find() | !emailMatcher.find() ){
            title = "Blad";
            message =  "Wpisz poprawne dane";
            showAlert(title, message);
        }
        else{
            if(repeatPassword.equals(password)) {
                helper.insertNewUser(email, password);
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
            }
            else {
                title = "Rejestracja nie powiodla sie";
                message = "Istnieje juz uzytkownik o podanych adresie email";
                showAlert(title, message);
            }
        }
    }

    private void showAlert(String title, String message){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
