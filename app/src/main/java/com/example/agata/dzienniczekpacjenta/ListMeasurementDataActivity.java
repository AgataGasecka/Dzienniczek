package com.example.agata.dzienniczekpacjenta;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static com.example.agata.dzienniczekpacjenta.DatabaseHelper.MEASUREMETS_TABLE;
import static com.example.agata.dzienniczekpacjenta.DatabaseHelper.Table_Column_1_hour;
import static com.example.agata.dzienniczekpacjenta.DatabaseHelper.Table_Column_2_measurement;
import static com.example.agata.dzienniczekpacjenta.DatabaseHelper.Table_Column_3_measurement_type;
import static com.example.agata.dzienniczekpacjenta.DatabaseHelper.Table_Column_data;

public class ListMeasurementDataActivity extends AppCompatActivity {
    int UserId;
    DatabaseHelper mDatabaseHelper;
    SQLiteDatabase db;
    ListAdapter listAdapter;
    ListView mListView;
    List<Pomiar> wynikiPomiarow;
    public String measurement_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserId = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_list_measurement_data);
        mListView = findViewById(R.id.listView);
        Spinner parameters = (Spinner) findViewById(R.id.wyborRodzajuPomiaru);
        Button wykres= (Button) findViewById(R.id.wykres);

        populateListView();

        wykres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VisualizationActivity.class);
                intent.putExtra("ID", UserId);
                intent.putExtra("MEASUREMENT_TYPE", measurement_type);
                startActivity(intent);
            }
        });
        parameters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(ListMeasurementDataActivity.this, "Wybrano opcję " + (parent.getItemAtPosition(position).toString()), Toast.LENGTH_SHORT).show();
                measurement_type=parent.getItemAtPosition(position).toString();
                setUnit();
                populateFilterListView();
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //kod do eksportu do csv
        Button export=findViewById(R.id.export);

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission (ListMeasurementDataActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // Jeśli nie przyznawaj uprawnień do zapisu zewnętrznego.
        if (writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Poproś użytkownika o nadanie uprawnień do zapisu w pamięci zewnętrznej.
            ActivityCompat.requestPermissions (ListMeasurementDataActivity.this, new String [] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        export.setOnClickListener(new View.OnClickListener() {
            Cursor c = null;

            @Override
            public void onClick(View v) {

                try {
                    String query =  "SELECT " + Table_Column_data +"," + Table_Column_1_hour+ "," + Table_Column_2_measurement + "," + Table_Column_3_measurement_type+ " FROM " + MEASUREMETS_TABLE + " WHERE " + Table_Column_3_measurement_type + "=?  ";//AND USER_ID=" + id;
                    c = db.rawQuery(query,  new String[]{measurement_type});
                    int rowcount = 0;
                    int colcount = 0;
                    File sdCardDir = Environment.getExternalStorageDirectory();
                    String filename = "wyniki_z_dzienniczka_pacjenta.csv";
                    // the name of the file to export with
                    File saveFile = new File(sdCardDir, filename);
                    FileWriter fw = new FileWriter(saveFile);

                    BufferedWriter bw = new BufferedWriter(fw);
                    rowcount = c.getCount();
                    colcount = c.getColumnCount();
                    if (rowcount > 0) {
                        c.moveToFirst();

                        for (int i = 0; i < colcount; i++) {
                            if (i != colcount - 1) {

                                bw.write(c.getColumnName(i) + ",");

                            } else {

                                bw.write(c.getColumnName(i));

                            }
                        }
                        bw.newLine();

                        for (int i = 0; i < rowcount; i++) {
                            c.moveToPosition(i);

                            for (int j = 0; j < colcount; j++) {
                                if (j != colcount - 1)
                                    bw.write(c.getString(j) + ",");
                                else
                                    bw.write(c.getString(j));
                            }
                            bw.newLine();
                        }
                        bw.flush();
                        Toast.makeText(ListMeasurementDataActivity.this, "Wyniki zostały wyeksportowane!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (db.isOpen()) {
                        db.close();
                        Toast.makeText(ListMeasurementDataActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        //koniec kodu do eksportu
    }

    private void setUnit(){
        TextView unit = (TextView) findViewById(R.id.jednostkaPomiaru);

        switch(measurement_type){
            case "Ciśnienie":
                unit.setText("mmHg");
                break;
            case "Cukier":
                unit.setText("mg/dl");
                break;
            case "Waga":
                unit.setText("kg");
                break;
            case "Temperatura":
                unit.setText("stopnie Celsjusza");
                break;
            case "Puls":
                unit.setText("uderzenia/min");
                break;
            default:
                break;
        }

    }


    private void populateListView() {
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();

        wynikiPomiarow = new ArrayList<>();

        Cursor cursor = mDatabaseHelper.viewMeasurementData(UserId);
        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Table_Column_data));
            String godzina = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Table_Column_1_hour));
            String pomiar = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Table_Column_2_measurement));

            wynikiPomiarow.add(new Pomiar(data, godzina, pomiar));
        }
        cursor.close();

        listAdapter = new ListAdapter(wynikiPomiarow, getApplicationContext());
        mListView.setAdapter(listAdapter);
    }

    private void populateFilterListView() {
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();

        wynikiPomiarow = new ArrayList<>();

        Cursor cursor = mDatabaseHelper.viewFilterMeasurementData(measurement_type, UserId);
        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Table_Column_data));
            String godzina = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Table_Column_1_hour));
            String pomiar = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Table_Column_2_measurement));

            wynikiPomiarow.add(new Pomiar(data, godzina, pomiar));
        }
        cursor.close();

        listAdapter = new ListAdapter(wynikiPomiarow, getApplicationContext());
        mListView.setAdapter(listAdapter);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


}
