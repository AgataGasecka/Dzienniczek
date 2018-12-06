package com.example.agata.dzienniczekpacjenta;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "dzienniczek.db";
    public static final String USERS_TABLE = "users";
    public static final String PATIENT_TABLE = "patient";
    public static final String VISITS_TABLE = "visits";
    public static final String MEASUREMETS_TABLE = "measurements";
    public static final String SETTINGS_TABLE = "settings";
    private String USUWANIE_DO_TESTOW = "DELETE FROM USERS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String usersQuery  = "create table " + USERS_TABLE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EMAIL TEXT, " +
                "PASSWORD TEXT, " +
                "NAME TEXT, " +
                "SURNAME TEXT)";
        db.execSQL(usersQuery);

        String patientQuery  = "create table " + PATIENT_TABLE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "SURNAME TEXT, " +
                "BIRTHDAY TEXT, " +
                "PESEL TEXT, " +
                "SEX BOOLEAN)";
        db.execSQL(patientQuery);

        String visitsQuery  = "create table " + VISITS_TABLE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DATE TEXT, " +
                "HOUR TEXT, " +
                "DOCTOR TEXT, " +
                "PLACE TEXT, " +
                "INFORMATION TEXT)";
        db.execSQL(visitsQuery);

        String measuremetsQuery = "create table " + MEASUREMETS_TABLE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERID INTEGER, " +
                "NAME TEXT, " +
                "VALUE TEXT)";
        db.execSQL(measuremetsQuery);

        String settingsQuery = "create table " + SETTINGS_TABLE +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERID INTEGER, " +
                "PARAMETERNAME TEXT, " +
                "DEFAULTVALUE TEXT)";
        db.execSQL(settingsQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertNewUser(String email, String password, String name, String surname) {
        String Query = "Select * from " + USERS_TABLE + " where EMAIL=" + email;

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            if(!ifDataExists(db, USERS_TABLE, "EMAIL", email)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("EMAIL", email);
                contentValues.put("PASSWORD", password);
                contentValues.put("NAME", name);
                contentValues.put("SURNAME", surname);
                db.insert(USERS_TABLE, null, contentValues);
            }
        }
    }

    public void insertNewVisit(String visitDate, String visitHour, String doctor, String place, String information) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("DATE", visitDate);
                contentValues.put("HOUR", visitHour);
                contentValues.put("DOCTOR", doctor);
                contentValues.put("PLACE", place);
                contentValues.put("INFORMATION", information);
                db.insert(VISITS_TABLE, null, contentValues);

        }
    }

    public boolean insertNewPatient(String patientName, String patientSurname, String patientBirthday, String patientPesel, Boolean patientSex) {
        String Query = "Select * from " + PATIENT_TABLE + " where PESEL=" + patientPesel;

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            if(!ifDataExists(db, PATIENT_TABLE, "PESEL", patientPesel)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("NAME", patientName);
                contentValues.put("SURNAME", patientSurname);
                contentValues.put("BIRTHDAY", patientBirthday);
                contentValues.put("PESEL", patientPesel);
                contentValues.put("SEX", patientSex);
                db.insert(PATIENT_TABLE, null, contentValues);

                return true;
            }
            else {
                return false;
            }
        }
    }

    public boolean ifDataExists(SQLiteDatabase db, String TableName, String dbField, String dbValue){
        db.execSQL(USUWANIE_DO_TESTOW);
        Cursor cursor = null;
        String sql ="SELECT * FROM "+TableName+" WHERE "+ dbField + "=\"" + dbValue + "\"";
        cursor= db.rawQuery(sql,null);

        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }

    }
}
