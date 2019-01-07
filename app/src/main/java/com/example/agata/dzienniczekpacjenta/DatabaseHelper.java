package com.example.agata.dzienniczekpacjenta;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dzienniczek5.db";
    public static final String USERS_TABLE = "users";
    public static final String PATIENT_TABLE = "patient";
    public static final String VISITS_TABLE = "visits";
    public static final String MEASUREMETS_TABLE = "measurements";
    public static final String SETTINGS_TABLE = "settings";
    public static final String DRUGS_TABLE = "drugs";
    private String USUWANIE_DO_TESTOW = "DELETE FROM USERS";
    private static final String TAG = "DatabaseHelper";


    public static final String Table_Column_data = "DATE";
    public static final String Table_Column_1_hour = "HOUR";
    public static final String Table_Column_2_measurement = "MEASUREMENT";
    public static final String Table_Column_3_measurement_type = "MEASUREMENT_TYPE";
    public static final String Table_Column_parameter_name = "PARAMETERNAME";

    //DRUGS
    public static final String ColumnData="DATE";
    public static final String ColumnHour="HOUR";
    public static final String ColumnDrugName="DRUG_NAME";
    public static final String ColumnDrugDose="DOSE"; //DOSE OF DRUG
    public static final String ColumnDrugParameterType= "DRUG_PARAMETER_TYPE";

    public static final String Visits_Date = "DATE";
    public static final String Visits_Hour = "HOUR";
    public static final String Visits_Doctor = "DOCTOR";
    public static final String Visits_Place = "PLACE";
    public static final String Visits_Info = "INFO";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String usersQuery = "create table " + USERS_TABLE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EMAIL TEXT, " +
                "PASSWORD TEXT, " +
                "NAME TEXT, " +
                "SURNAME TEXT, " +
                "BIRTHDAY TEXT, " +
                "PESEL TEXT, " +
                "SEX BOOLEAN)";
        db.execSQL(usersQuery);

        String visitsQuery = "create table " + VISITS_TABLE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_ID INTEGER, " +
                "DATE TEXT, " +
                "HOUR TEXT, " +
                "DOCTOR TEXT, " +
                "PLACE TEXT, " +
                "INFORMATION TEXT)";
        db.execSQL(visitsQuery);

        String measuremetsQuery = "create table " + MEASUREMETS_TABLE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_ID INTEGER, " +
                "DATE TEXT, " +
                " HOUR TEXT," +
                "MEASUREMENT TEXT," +
                "MEASUREMENT_TYPE TEXT)";
        db.execSQL(measuremetsQuery);

        String settingsQuery = "create table " + SETTINGS_TABLE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_ID INTEGER, " +
                "PARAMETERNAME TEXT, " +
                "DEFAULTVALUEUP TEXT," +
                "DEFAULTVALUEDOWN TEXT," +
                "UNIT TEXT)";
        db.execSQL(settingsQuery);

        String drugsQuery = "create table " + DRUGS_TABLE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_ID INTEGER, " +
                "DATE TEXT, " +
                "HOUR TEXT," +
                "DRUG_NAME TEXT," +
                "DOSE TEXT," +
                "DRUG_PARAMETER_TYPE TEXT)";
        db.execSQL(drugsQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertNewUser(String email, String password, String name, String surname) {
        String Query = "Select * from " + USERS_TABLE + " where EMAIL=" + email;

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            if (!ifDataExists(db, USERS_TABLE, "EMAIL", email)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("EMAIL", email);
                contentValues.put("PASSWORD", password);
                contentValues.put("NAME", name);
                contentValues.put("SURNAME", surname);
                db.insert(USERS_TABLE, null, contentValues);
            }
        }
    }

    public void insertNewVisit(String visitDate, String visitHour, String doctor, String place, String information, int userId) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("USER_ID", userId);
            contentValues.put("DATE", visitDate);
            contentValues.put("HOUR", visitHour);
            contentValues.put("DOCTOR", doctor);
            contentValues.put("PLACE", place);
            contentValues.put("INFORMATION", information);
            db.insert(VISITS_TABLE, null, contentValues);


        }
    }

    public void updateVisit(String visitDate, String visitHour, String doctor, String place, String information, int visitId) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("DATE", visitDate);
            contentValues.put("HOUR", visitHour);
            contentValues.put("DOCTOR", doctor);
            contentValues.put("PLACE", place);
            contentValues.put("INFORMATION", information);
            db.update(VISITS_TABLE, contentValues, "ID=" + visitId, null);

        }
    }

    public void deleteVisit(String visitDate, String visitHour, String doctor, String place, String information, int id) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(VISITS_TABLE, "ID=" + id, null);

        }
    }

    public boolean updateUser(String patientName, String patientSurname, String patientBirthday, String patientPesel, Boolean patientSex, int userId) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            if (!ifDataExists(db, USERS_TABLE, "ID", String.valueOf(userId))) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("NAME", patientName);
                contentValues.put("SURNAME", patientSurname);
                contentValues.put("BIRTHDAY", patientBirthday);
                contentValues.put("PESEL", patientPesel);
                contentValues.put("SEX", patientSex);
                db.update(USERS_TABLE, contentValues, "ID=" + userId, null);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean ifDataExists(SQLiteDatabase db, String TableName, String dbField, String dbValue) {
        db.execSQL(USUWANIE_DO_TESTOW);
        Cursor cursor = null;
        String sql = "SELECT * FROM " + TableName + " WHERE " + dbField + "=\"" + dbValue + "\"";
        cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }

    public boolean addMeasurementData(int userid, String date, String hour, String measurement, String measurement_type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER_ID", userid);
        contentValues.put("DATE", date);
        contentValues.put("HOUR", hour);
        contentValues.put("MEASUREMENT", measurement);
        contentValues.put("MEASUREMENT_TYPE", measurement_type);

        Log.d(TAG, "addData: Adding data to " + MEASUREMETS_TABLE);

        long result = db.insert(MEASUREMETS_TABLE, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertUserSettings(int userId, String parametrName, String parametrStandardUp, String parametrStandardDown, String parametrUnit) {

        long result;

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("USER_ID", userId);
            contentValues.put("PARAMETERNAME", parametrName);
            contentValues.put("DEFAULTVALUEUP", parametrStandardUp);
            contentValues.put("DEFAULTVALUEDOWN", parametrStandardDown);
            contentValues.put("UNIT", parametrUnit);
            result = db.insert(SETTINGS_TABLE, null, contentValues);
        }

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor viewMeasurementData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        AddMeasurement addMeasurement = new AddMeasurement();
        //    String query= "SELECT * FROM " + MEASUREMETS_TABLE + " WHERE " + Table_Column_3_measurement_type + "=?";
        //  Cursor cursor=db.rawQuery(query, new String[]{"Ciśnienie"});
        String query = "SELECT * FROM " + MEASUREMETS_TABLE + " WHERE USER_ID=" + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }


    public Cursor viewFilterMeasurementData(String measurement_type, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ListMeasurementDataActivity list = new ListMeasurementDataActivity();
        String query = "SELECT * FROM " + MEASUREMETS_TABLE + " WHERE " + Table_Column_3_measurement_type + "=\"" + measurement_type + "\" AND USER_ID=" + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor viewListOfVisits(int userId) {
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + VISITS_TABLE + " where USER_ID=" + userId;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public ArrayList<String> queryXData(String measurement_type, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ListMeasurementDataActivity list = new ListMeasurementDataActivity();
        ArrayList<String> xNewData = new ArrayList<String>();
        String query = "SELECT " + Table_Column_data + " FROM " + MEASUREMETS_TABLE + " WHERE " + Table_Column_3_measurement_type + "=\"" + measurement_type + "\" AND USER_ID=" + id;
        Cursor cursor = db.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            xNewData.add(cursor.getString(cursor.getColumnIndex(Table_Column_data)));
        }
        cursor.close();
        return xNewData;
    }

    public ArrayList<Integer> queryYData(String measurement_type, int id) {
        ArrayList<Integer> yNewData = new ArrayList<Integer>();
        ListMeasurementDataActivity list = new ListMeasurementDataActivity();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + Table_Column_2_measurement + " FROM " + MEASUREMETS_TABLE + " WHERE " + Table_Column_3_measurement_type + "=\"" + measurement_type + "\" AND USER_ID=" + id;
        Cursor cursor = db.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            yNewData.add(cursor.getInt(cursor.getColumnIndex(Table_Column_2_measurement)));
        }
        cursor.close();
        return yNewData;
    }

    public String viewParameterNorm(String measurement_type, int userId) {
        Cursor cursor = null;
        String result = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select DEFAULTVALUE from " + SETTINGS_TABLE + " WHERE " + Table_Column_parameter_name + "=? AND USER_ID=" + userId;
        cursor = db.rawQuery(query, new String[]{measurement_type});
        if(cursor.moveToFirst()){
            result = cursor.getString(cursor.getColumnIndex("DEFAULTVALUE"));
        }
        cursor.close();
        return result;
    }

    public boolean addDrugsData(int userid, String date, String hour, String drug_name, String dose, String drug_parameter_type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER_ID", userid);
        contentValues.put("DATE", date);
        contentValues.put("HOUR", hour);
        contentValues.put("DRUG_NAME", drug_name);
        contentValues.put("DOSE", dose);
        contentValues.put("DRUG_PARAMETER_TYPE", drug_parameter_type);

        Log.d(TAG, "addData: Adding data to " + DRUGS_TABLE);

        long result = db.insert(DRUGS_TABLE, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor viewDrugData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
     //   AddDrug addMeasurement = new AddDrug();
        //    String query= "SELECT * FROM " + MEASUREMETS_TABLE + " WHERE " + Table_Column_3_measurement_type + "=?";
        //  Cursor cursor=db.rawQuery(query, new String[]{"Ciśnienie"});
        String query= "SELECT * FROM " + DRUGS_TABLE + " WHERE USER_ID=" + id ;
        Cursor cursor=db.rawQuery(query,null);
        return cursor;
    }


    public Cursor viewFilterDrugsData(String drugs_type, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        //zmienic na drug
       // ListMeasurementDataActivity list = new ListMeasurementDataActivity();
        String query= "SELECT * FROM " + DRUGS_TABLE + " WHERE " + ColumnDrugParameterType + "=? AND USER_ID=" + id;
        Cursor cursor=db.rawQuery(query, new String[]{drugs_type});
        return cursor;
    }
}
