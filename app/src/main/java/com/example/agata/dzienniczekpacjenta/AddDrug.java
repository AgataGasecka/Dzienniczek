package com.example.agata.dzienniczekpacjenta;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddDrug extends AppCompatActivity {

    int id;
    DatabaseHelper controllerdb = new DatabaseHelper(this);
    SQLiteDatabase db;
    public String drug_type;
    DatePickerDialog datepicker;
    private Calendar calendar;
    private TextView dataPomiaru;
    private  TextView setDate;
    private int year, month, day;
    ListView lv;
    ImageButton date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id = getIntent().getIntExtra("ID", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);
        //lv = (ListView) findViewById(R.id.listview);
        Spinner parameters = (Spinner) findViewById(R.id.wyborRodzajuPomiaruDrug);
        Button btnAdd = (Button) findViewById(R.id.dodajpomiarDrug);
        Button btnView = (Button) findViewById(R.id.wyswietl_pomiaryDrug);
        final TextView dataPomiaru =(TextView) findViewById(R.id.dataPomiaruDrug);
        final TextView godzinaPomiaru =(TextView) findViewById(R.id.godzinaPomiaruDrug);
        final EditText wynikPomiaru =(EditText) findViewById(R.id.wynikPomiaruDrug);
        final TextView ustawDate = (TextView) findViewById(R.id.dataPomiaruDrug);
        date= (ImageButton) findViewById(R.id.ustawDateDrug);


        date.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);


                datepicker = new DatePickerDialog(AddDrug.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDayOfMonth) {
                        ustawDate.setText(mDayOfMonth +"/"+ (mMonth+1)+ "/" +mYear);

                    }
                }, year, month,day);
                datepicker.show();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String userid = "";
                String data = dataPomiaru.getText().toString();
                String godzina = godzinaPomiaru.getText().toString();
                String wynik = wynikPomiaru.getText().toString();
                if (data.length() !=0 & godzina.length() !=0 & wynik.length() !=0){
                    AddData(userid,data,godzina,wynik,drug_type);
                    dataPomiaru.setText("");
                    godzinaPomiaru.setText("");
                    wynikPomiaru.setText("");
                } else {
                    toastMessage("Nie umieściłeś danych w polach!");
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListOfDrugs.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        parameters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Toast.makeText(AddMeasurement.this, "Wybrano opcję" + (parent.getItemAtPosition(position).toString()), Toast.LENGTH_SHORT).show();
                drug_type=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ImageButton setVisitTime = findViewById(R.id.ustawGodzineDrug);
        setVisitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddDrug.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        TextView visitHour = findViewById(R.id.godzinaPomiaruDrug);
                        visitHour.setText(String.format("%02d:%02d",selectedHour,selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Wybierz godzinę");
                mTimePicker.show();
            }
        });
    }


    public void AddData(String userid, String data, String hour, String drugs, String drug_type) {
        boolean insertData = controllerdb.addDrugsData(userid,data,hour, drugs,drug_type);

        if (insertData) {
            toastMessage("Dawka leku została dodana!");
        } else {
            toastMessage("Coś poszło nie tak");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
