package com.example.agata.dzienniczekpacjenta;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Settings2 extends AppCompatActivity {

    DatabaseHelper helper;

    int id;
    String measurement_type;
    String measurement_standard_up;
    String measurement_standard_down;
    String measurement_unit;

    EditText newNorm;
    TextView hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_settings2);
        helper = new DatabaseHelper(this);
        newNorm =(EditText) findViewById(R.id.editText7);
        hint = (TextView)findViewById(R.id.hint);

        Spinner spinnerPatameter = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.parameters_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatameter.setAdapter(adapter);

        spinnerPatameter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Settings2.this, "Wybrano opcję " + (parent.getItemAtPosition(position).toString()), Toast.LENGTH_SHORT).show();
                measurement_type=parent.getItemAtPosition(position).toString();
                setMeasurementStandard();
                hideEditTextNewNorm();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void setMeasurementStandard(){
        TextView standard = (TextView) findViewById(R.id.normaPomiaru);

        switch(measurement_type){
            case "Ciśnienie":
                measurement_standard_up = "120";
                measurement_standard_down = "80";
                measurement_unit = "mmHg";
                standard.setText("120/80 mmHg");
                break;
            case "Cukier":
                measurement_standard_up = "100";
                measurement_standard_down = "70";
                measurement_unit = "mg/dl";
                standard.setText("70-100 mg/dl");
                break;
            case "Waga":
                measurement_standard_up = "";
                measurement_standard_down = "";
                measurement_unit = "kg";
                standard.setText("Wpisz swoją normę");
                break;
            case "Temperatura":
                measurement_standard_up = "36.6";
                measurement_standard_down = "";
                measurement_unit = "℃";
                standard.setText("36.6 ℃");
                break;
            case "Puls":
                measurement_standard_up = "70";
                measurement_standard_down = "";
                measurement_unit = "uderzeń/min";
                standard.setText("70 uderzeń/min");
                break;
            default:
                break;
        }
    }

    public void setNewNorm(View view){
        newNorm.setVisibility(View.VISIBLE);
        newNorm.setHint("Nowa norma");
        measurement_standard_up = "";
        measurement_standard_down = "";

        TextView hint = (TextView)findViewById(R.id.hint);
        hint.setVisibility(View.VISIBLE);
        if(measurement_type.equals("Ciśnienie")){
            hint.setText("Wpisz dwie liczby oddzielone \"/\" np. 130/70");
        }
        else{
            hint.setText("Wpisz jedną liczbę np. 80");
        }
    }

    public void returnNorm(View view){
        if(measurement_type.equals("Waga")&&measurement_standard_up.isEmpty()){
            Toast.makeText(Settings2.this, "Nie podano normy wagi!", Toast.LENGTH_SHORT).show();
        }else{
            hideEditTextNewNorm();
            setMeasurementStandard();
        }
    }

    private void hideEditTextNewNorm(){
        newNorm.setText("");
        newNorm.setVisibility(View.INVISIBLE);
        hint.setText("");
        hint.setVisibility(View.INVISIBLE);
    }

    public void saveInfo(View view){
        boolean correctly = false;
        String newNormFromUser = newNorm.getText().toString();

        if(!newNormFromUser.isEmpty()){
            if(measurement_type.equals("Ciśnienie")){
                String norms[] = newNormFromUser.split("/");
                measurement_standard_up = norms[0];
                measurement_standard_down = norms[1];
            }
            else{
                measurement_standard_up = newNormFromUser;
            }
        }

        if(measurement_type.equals("Waga") && measurement_standard_up.isEmpty()){
            Toast.makeText(Settings2.this, "Nie podano normy wagi!", Toast.LENGTH_SHORT).show();
        }
        else if(measurement_standard_up.isEmpty()){
            Toast.makeText(Settings2.this, "Nie podano normy!", Toast.LENGTH_SHORT).show();
        }
        else{
            correctly = helper.insertUserSettings(id, measurement_type, measurement_standard_up, measurement_standard_down, measurement_unit);
            Intent intent = new Intent(this, Home.class);
            intent.putExtra("ID", id);
            startActivityForResult(intent, 1);
        }

        if(correctly){
            Toast.makeText(Settings2.this, "Zapisano w bazie", Toast.LENGTH_SHORT).show();
            hideEditTextNewNorm();
            measurement_standard_up = "";
            measurement_standard_down = "";
            setMeasurementStandard(); //dzięki temu po zapisaniu pomiarów można zapisać to samo jeszcze raz

            //sprawdzenie zapisu do bazy
            /*try{
                SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                String sql ="SELECT * FROM  settings";
                Cursor cursor= db.rawQuery(sql,null);

                cursor.moveToLast();
                Log.d("parametr ", (cursor.getString(1)));
                Log.d("parametr ", (cursor.getString(2)));
                Log.d("parametr ", (cursor.getString(3)));
                Log.d("parametr ", (cursor.getString(4)));
                Log.d("parametr ", (cursor.getString(5)));

            }catch(SQLException e){
                Toast toast = Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
                toast.show();
            }*/

        }
        else{
            Toast.makeText(Settings2.this, "Błąd", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("ID", id);
        setResult(RESULT_OK, intent);
        finish();
    }

}
