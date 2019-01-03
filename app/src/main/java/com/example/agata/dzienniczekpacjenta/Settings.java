package com.example.agata.dzienniczekpacjenta;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Settings extends AppCompatActivity {
    int id;
    DatabaseHelper helper;
    String patientName;
    String patientSurname;
    String patientBirthday;
    String patientPesel;
    boolean patientSex;
    boolean patientSexChecked;

    String title;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_settings);
        helper = new DatabaseHelper(this);
    }

    public void saveDataForNewPatient(View view){

        patientName = ((EditText)findViewById(R.id.patientName)).getText().toString().trim();
        patientSurname = ((EditText)findViewById(R.id.patientSurname)).getText().toString().trim();
        patientBirthday = ((EditText)findViewById(R.id.patientBirthday)).getText().toString().trim();
        patientPesel = ((EditText)findViewById(R.id.patientPesel)).getText().toString().trim();

        Pattern VALID_NAME_REGEX =
                Pattern.compile("^(?i)^(?:[a-z]+(?: |\\. ?)?)+[a-z]$", Pattern.CASE_INSENSITIVE);

        Pattern VALID_DATA_REGEX =
                Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$", Pattern.CASE_INSENSITIVE);

        Pattern VALID_PESEL_REGEX =
                Pattern.compile("^[0-9]{11}$", Pattern.CASE_INSENSITIVE);

        Matcher nameMatcher = VALID_NAME_REGEX.matcher(patientName);
        Matcher surnameMatcher = VALID_NAME_REGEX.matcher(patientSurname);
        Matcher birthdayMatcher = VALID_DATA_REGEX.matcher(patientBirthday);
        Matcher peselMatcher = VALID_PESEL_REGEX.matcher(patientPesel);


        if ( !nameMatcher.find() | !surnameMatcher.find() | !birthdayMatcher.find()| !peselMatcher.find() | !patientSexChecked){
            title = "Blad";
            message =  "Wpisz poprawne dane";
            showAlert(title, message);
        }
        else{
                boolean saved = helper.insertNewPatient(patientName, patientSurname, patientBirthday, patientPesel, patientSex );
                if(saved){
                    title = "Zapisano dane";
                    message = "Dane pacjenta zostały zapisane.";
                    showAlert(title, message);
                    Intent intent = new Intent(this, Settings2.class);
                    startActivity(intent);
                }
                else{
                    title = "UWAGA!";
                    message = "W bazie istnieje pacjent o podanym numerze PESEL.";
                    showAlert(title, message);
                }

        }

        //Sprawdzenie, co się zapisuje w bazie danych
        /*try{
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            String sql ="SELECT * FROM  patient";
            Cursor cursor= db.rawQuery(sql,null);

            cursor.moveToLast();
            Log.d("patient ", (cursor.getString(1)));
            Log.d("patient ", (cursor.getString(2)));
            Log.d("patient ", (cursor.getString(3)));
            Log.d("patient ", (cursor.getString(4)));
            Log.d("patient ", (cursor.getString(5)));

        }catch(SQLException e){
            Toast toast = Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
            toast.show();
        }*/
    }

    public void onRadioButtonClicked(View view) {
        // Czy wynrano plec
        patientSexChecked = ((RadioButton) view).isChecked();

        // Jaka zaznaczono plec
        switch(view.getId()) {
            case R.id.radioFemale:
                if (patientSexChecked)
                    patientSex = true;
                    break;
            case R.id.radioMale:
                if (patientSexChecked)
                    patientSex = false;
                    break;
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

    public void goToHomePage(View view){
        Intent intent = new Intent(this, Settings2.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
