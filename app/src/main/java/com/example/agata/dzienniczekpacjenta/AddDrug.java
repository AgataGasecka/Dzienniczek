package com.example.agata.dzienniczekpacjenta;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AlertDialog;
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
    public String drug_parameter_type;
    DatePickerDialog datepicker;
    private Calendar calendar;
    private TextView dataPomiaru;
    private  TextView setDate;
    private int year, month, day;
    ListView lv;
    ImageButton date;
    String [] listItems;
    boolean [] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id = getIntent().getIntExtra("ID", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);
        //lv = (ListView) findViewById(R.id.listview);
        listItems = getResources().getStringArray(R.array.dayOfWeek_array);
        checkedItems = new boolean[listItems.length];
        Spinner parameters = (Spinner) findViewById(R.id.wyborRodzajuPomiaruDrug);
        Button btnAdd = (Button) findViewById(R.id.dodajpomiarDrug);
        Button btnView = (Button) findViewById(R.id.wyswietl_pomiaryDrug);
        final TextView dataPomiaru =(TextView) findViewById(R.id.dataPomiaruDrug);
        final TextView godzinaPomiaru =(TextView) findViewById(R.id.godzinaPomiaruDrug);
        final EditText wynikPomiaru =(EditText) findViewById(R.id.wynikPomiaruDrug);
        final TextView ustawDate = (TextView) findViewById(R.id.dataPomiaruDrug);
        final TextView nazwaLeku = (TextView) findViewById(R.id.nazwaLeku);
        date= (ImageButton) findViewById(R.id.ustawDateDrug);
        Button alarmLek = (Button) findViewById(R.id.AlarmLek);

        date.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
//                calendar=Calendar.getInstance();
//                int day=calendar.get(Calendar.DAY_OF_MONTH);
//                int month=calendar.get(Calendar.MONTH);
//                int year=calendar.get(Calendar.YEAR);
//
//
//                datepicker = new DatePickerDialog(AddDrug.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDayOfMonth) {
//                        ustawDate.setText(mDayOfMonth +"/"+ (mMonth+1)+ "/" +mYear);
//
//                    }
//                }, year, month,day);
//                datepicker.show();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddDrug.this);
                mBuilder.setTitle("Wybierz dzień tygodnia");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
            }
        });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        dataPomiaru.setText(item);
                    }
                });
                mBuilder.setNegativeButton("Pomiń", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Wyczyść", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            dataPomiaru.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String userid = "";
                String data = dataPomiaru.getText().toString();
                String godzina = godzinaPomiaru.getText().toString();
                String wynik = wynikPomiaru.getText().toString();
                String nazwa= nazwaLeku.getText().toString();
                if (data.length() !=0 & godzina.length() !=0 & wynik.length() !=0){
                    AddData(userid,data,godzina,nazwa, wynik,drug_parameter_type);
                    dataPomiaru.setText("");
                    godzinaPomiaru.setText("");
                    wynikPomiaru.setText("");
                    nazwaLeku.setText("");
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
                drug_parameter_type=parent.getItemAtPosition(position).toString();
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

        alarmLek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR,10);
                intent.putExtra(AlarmClock.EXTRA_MINUTES,10);
                startActivity(intent);
            }
        });
    }


    public void AddData(String userid, String date, String hour, String drug_name, String dose, String drug_parameter_type) {
        boolean insertData = controllerdb.addDrugsData(userid,date,hour,drug_name,dose, drug_parameter_type);

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