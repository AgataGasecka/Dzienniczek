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

        //kod do eksportu do csv
        Button export=findViewById(R.id.export);

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission (VisualizationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
// Jeśli nie przyznawaj uprawnień do zapisu zewnętrznego.
        if (writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Poproś użytkownika o nadanie uprawnień do zapisu w pamięci zewnętrznej.
            ActivityCompat.requestPermissions (VisualizationActivity.this, new String [] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        export.setOnClickListener(new View.OnClickListener() {
            Cursor c = null;

            @Override
            public void onClick(View v) {

                try {
                    String query =  "SELECT " + Table_Column_data +"," + Table_Column_1_hour+ "," + Table_Column_2_measurement + "," + Table_Column_3_measurement_type+ " FROM " + MEASUREMETS_TABLE;
                    c = db.rawQuery(query, null);
                    int rowcount = 0;
                    int colcount = 0;
                    File sdCardDir = Environment.getExternalStorageDirectory();
                    String filename = "MyBackUp.csv";
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
                        Toast.makeText(VisualizationActivity.this, "Exported succes", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (db.isOpen()) {
                        db.close();
                        Toast.makeText(VisualizationActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        //koniec kodu do eksportu


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
