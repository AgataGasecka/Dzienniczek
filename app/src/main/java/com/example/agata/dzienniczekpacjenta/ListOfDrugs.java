package com.example.agata.dzienniczekpacjenta;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListOfDrugs extends AppCompatActivity {
    int id;
    DatabaseHelper mDatabaseHelper;
    SQLiteDatabase db;
    ListDrugAdapter listAdapter;
    ListView mListView;
    List<Drug> wynikiPomiarow;
    public String drug_parameter_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_list_drugs);
        mListView = findViewById(R.id.listDrugView);
        Spinner parameters = (Spinner) findViewById(R.id.wyborRodzajuPomiaruDrug);

        populateListView();


        parameters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListOfDrugs.this, "Wybrano opcję " + (parent.getItemAtPosition(position).toString()), Toast.LENGTH_SHORT).show();
                drug_parameter_type=parent.getItemAtPosition(position).toString();
                populateFilterListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void populateListView() {
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();

        wynikiPomiarow = new ArrayList<>();

        Cursor cursor = mDatabaseHelper.viewDrugData();
        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.ColumnData));
            String godzina = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.ColumnHour));
            String nazwa = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.ColumnDrugName));
            String pomiar = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.ColumnDrugDose));

            wynikiPomiarow.add(new Drug(data, godzina, nazwa, pomiar));
        }
        cursor.close();

        listAdapter = new ListDrugAdapter(wynikiPomiarow, getApplicationContext());
        mListView.setAdapter(listAdapter);
    }

    private void populateFilterListView() {
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();

        wynikiPomiarow = new ArrayList<>();

        Cursor cursor = mDatabaseHelper.viewFilterDrugsData(drug_parameter_type);
        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.ColumnData));
            String godzina = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.ColumnHour));
            String nazwa = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.ColumnDrugName));
            String pomiar = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.ColumnDrugDose));

            wynikiPomiarow.add(new Drug(data, godzina,nazwa, pomiar));
        }
        cursor.close();

        listAdapter = new ListDrugAdapter(wynikiPomiarow, getApplicationContext());
        mListView.setAdapter(listAdapter);
    }
}
