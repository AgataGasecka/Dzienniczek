package com.example.agata.dzienniczekpacjenta;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListOfVisits extends AppCompatActivity {
    int id;
    DatabaseHelper mDatabaseHelper;
    SQLiteDatabase db;
    ListAdapterVisit listAdapter;
    ListView mListView;
    List<Visit> addedVisits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        setContentView(R.layout.activity_list_of_visits);
        mListView = findViewById(R.id.visitsList);
        showVisitsList();
    }

    private void showVisitsList() {
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();

        addedVisits = new ArrayList<>();

        Cursor cursor = mDatabaseHelper.viewListOfVisits(id);
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Visits_Date));
            String hour = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Visits_Hour));
            String doctor = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Visits_Doctor));
            String place = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Visits_Place));
            int visitId = cursor.getInt(cursor.getColumnIndex("ID"));
            String info = "brak";

            addedVisits.add(new Visit(date, hour, doctor, place, info, visitId));
        }
        cursor.close();

        listAdapter = new ListAdapterVisit(addedVisits, getApplicationContext());
        mListView.setAdapter(listAdapter);
    }
}
