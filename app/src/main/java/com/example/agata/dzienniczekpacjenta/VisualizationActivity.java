package com.example.agata.dzienniczekpacjenta;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
    public String measurement_type;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);
        measurement_type = getIntent().getStringExtra("MEASUREMENT_TYPE");
        id = getIntent().getIntExtra("ID", 0);
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();
        ListMeasurementDataActivity list = new ListMeasurementDataActivity();
        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();

        for (int i = 0; i < mDatabaseHelper.queryYData(measurement_type).size(); i++)
            yVals.add(new BarEntry(mDatabaseHelper.queryYData(measurement_type).get(i), i));

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < mDatabaseHelper.queryXData(measurement_type).size(); i++)
            xVals.add(mDatabaseHelper.queryXData(measurement_type).get(i));

        BarDataSet dataSet = new BarDataSet(yVals, "Pomiar");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(xVals, dataSet);
//zrobic zeby maxCapasity to byla wartosc pobierana z bazy defaultvalue w zaleznosci
        //od id i od parametru measurement_type
        int maxCapacity=120;
       // String norm=mDatabaseHelper.viewParameterNorm(measurement_type, id);
       // float maxNorm=Float.valueOf(norm);
         LimitLine ll = new LimitLine(maxCapacity, "Norma");

        ll.setTextSize(4f);
        ll.setLineColor(Color.RED);
        ll.setLineWidth(4f);
        barChart.getAxisLeft().addLimitLine(ll);

        barChart.setData(data);
        switch(measurement_type){
            case "Ciśnienie":
                barChart.setDescription("Wykres ciśnienia w poszczególnych dniach");
                break;
            case "Cukier":
                barChart.setDescription("Wykres cukieru w poszczególnych dniach");
                break;
            case "Waga":
                barChart.setDescription("Wykres wagi w poszczególnych dniach");
                break;
            case "Temperatura":
                barChart.setDescription("Wykres temperatury w poszczególnych dniach");
                break;
            case "Puls":
                barChart.setDescription("Wykres pulsu w poszczególnych dniach");
                break;

        }
        barChart.animateY(2000);



    }

}
