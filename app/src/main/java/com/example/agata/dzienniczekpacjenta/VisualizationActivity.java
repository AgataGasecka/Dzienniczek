package com.example.agata.dzienniczekpacjenta;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class VisualizationActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);

        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        addData();






    }
    public void addData(){
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();
        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();

        for (int i = 0; i <mDatabaseHelper.queryYData().size(); i++)
            yVals.add(new BarEntry(mDatabaseHelper.queryYData().get(i), i));

        ArrayList<String> xVals = new ArrayList<String>();
        for(int i = 0; i < mDatabaseHelper.queryXData().size(); i++)
            xVals.add(mDatabaseHelper.queryXData().get(i));

        BarDataSet dataSet = new BarDataSet(yVals, "Pomiar");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(xVals, dataSet);


        LimitLine line = new LimitLine(12f, "");
        line.setTextSize(12f);
        line.setLineWidth(4f);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.addLimitLine(line);

        barChart.setData(data);
        barChart.setDescription("Wykres cukru w poszczególnych dniach");
        barChart.animateY(2000);

    }
}
