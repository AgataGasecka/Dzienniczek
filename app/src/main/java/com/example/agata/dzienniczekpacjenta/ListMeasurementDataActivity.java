package com.example.agata.dzienniczekpacjenta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListMeasurementDataActivity extends AppCompatActivity {
    int id;
    DatabaseHelper mDatabaseHelper;
    SQLiteDatabase db;
    ListAdapter listAdapter;
    ListView mListView;
    List<Pomiar> wynikiPomiarow;
    public String measurement_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_list_measurement_data);
        mListView = findViewById(R.id.listView);
        Spinner parameters = (Spinner) findViewById(R.id.wyborRodzajuPomiaru);
        Button wykres= (Button) findViewById(R.id.wykres);

        populateListView();

        wykres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VisualizationActivity.class);
                intent.putExtra("ID", id);
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

        }

    }


    private void populateListView() {
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();

        wynikiPomiarow = new ArrayList<>();

        Cursor cursor = mDatabaseHelper.viewMeasurementData();
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

        Cursor cursor = mDatabaseHelper.viewFilterMeasurementData(measurement_type);
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
