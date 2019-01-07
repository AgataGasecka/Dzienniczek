package com.example.agata.dzienniczekpacjenta;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import static com.example.agata.dzienniczekpacjenta.DatabaseHelper.MEASUREMETS_TABLE;
import static com.example.agata.dzienniczekpacjenta.DatabaseHelper.Table_Column_1_hour;
import static com.example.agata.dzienniczekpacjenta.DatabaseHelper.Table_Column_2_measurement;
import static com.example.agata.dzienniczekpacjenta.DatabaseHelper.Table_Column_3_measurement_type;
import static com.example.agata.dzienniczekpacjenta.DatabaseHelper.Table_Column_data;


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
        TextView wartoscNorm = (TextView) findViewById(R.id.wartoscNormatywna);
        TextView jednostka = (TextView) findViewById(R.id.unit);

        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();

        for (int i = 0; i < mDatabaseHelper.queryYData(measurement_type, id).size(); i++)
            yVals.add(new BarEntry(mDatabaseHelper.queryYData(measurement_type, id).get(i), i));

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < mDatabaseHelper.queryXData(measurement_type, id).size(); i++)
            xVals.add(mDatabaseHelper.queryXData(measurement_type, id).get(i));

        BarDataSet dataSet = new BarDataSet(yVals, "Pomiar");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(xVals, dataSet);
        //zrobic zeby maxCapasity to byla wartosc pobierana z bazy defaultvalue w zaleznosci
        //od id i od parametru measurement_type
        int maxCapacity=120;
        String norm=mDatabaseHelper.viewParameterNorm(measurement_type, id);
        Float maxNorm=Float.parseFloat(norm);
        wartoscNorm.setText(String.valueOf(maxNorm));
        LimitLine ll = new LimitLine(maxNorm, "Norma");

        ll.setTextSize(4f);
        ll.setLineColor(Color.RED);
        ll.setLineWidth(4f);
        barChart.getAxisLeft().addLimitLine(ll);

        barChart.setData(data);
        switch(measurement_type){
            case "Ciśnienie":
                barChart.setDescription("Wykres ciśnienia w poszczególnych dniach");
                dataSet.setLabel("mmHg");
                jednostka.setText("mmHg");

                break;
            case "Cukier":
                barChart.setDescription("Wykres cukru w poszczególnych dniach");
                dataSet.setLabel("mg/dl");
                jednostka.setText("mg/dl");

                break;
            case "Waga":
                barChart.setDescription("Wykres wagi w poszczególnych dniach");
                dataSet.setLabel("kg");
                jednostka.setText("kg");

                break;
            case "Temperatura":
                barChart.setDescription("Wykres temperatury w poszczególnych dniach");
                dataSet.setLabel("stopnie Celsjusza");
                jednostka.setText("stopnie Celsjusza");

                break;
            case "Puls":
                barChart.setDescription("Wykres pulsu w poszczególnych dniach");
                dataSet.setLabel("udzerzenia/min");
                jednostka.setText("udzerzenia/min");

                break;

        }
        barChart.animateY(1000);




    }


}
