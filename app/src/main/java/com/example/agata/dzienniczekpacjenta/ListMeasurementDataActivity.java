package com.example.agata.dzienniczekpacjenta;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_list_measurement_data);
        mListView = findViewById(R.id.listView);

        populateListView();
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
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
