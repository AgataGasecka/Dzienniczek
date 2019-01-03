package com.example.agata.dzienniczekpacjenta;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListOfVisits extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    SQLiteDatabase db;
    ListAdapterVisit listAdapter;
    ListView mListView;
    List<Visit> addedVisits;

    static String doctor;
    static String place;
    static String info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_visits);
        mListView = findViewById(R.id.visitsList);
        showVisitsList();
    }

    private void showVisitsList() {
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();

        addedVisits = new ArrayList<>();

        Cursor cursor = mDatabaseHelper.viewListOfVisits();
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Visits_Date));
            String hour = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Visits_Hour));
            doctor = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.Visits_Doctor));
            place = cursor.getString((cursor.getColumnIndex(mDatabaseHelper.Visits_Place)));
            info = "brak";
            //info = cursor.getString((cursor.getColumnIndex(mDatabaseHelper.Visits_Info)));

            addedVisits.add(new Visit(date, hour));
        }
        cursor.close();

        listAdapter = new ListAdapterVisit(addedVisits, getApplicationContext());
        mListView.setAdapter(listAdapter);
    }
}
