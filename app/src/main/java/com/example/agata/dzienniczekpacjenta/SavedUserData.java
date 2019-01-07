package com.example.agata.dzienniczekpacjenta;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.layout.simple_list_item_1;

public class SavedUserData extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_user_data);

        TextView name  = (TextView)findViewById(R.id.name);
        TextView surname  = (TextView)findViewById(R.id.surname);
        TextView email  = (TextView)findViewById(R.id.email);
        TextView info = (TextView)findViewById(R.id.info);

        info.setText("Utworzono konto");

        try{
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
            db = databaseHelper.getReadableDatabase();
            String sql ="SELECT * FROM  users";
            cursor= db.rawQuery(sql,null);

            cursor.moveToLast();
            name.setText(cursor.getString(3));
            surname.setText(cursor.getString(4));
            email.setText(cursor.getString(1));

        }catch(SQLException e){
            Toast toast = Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void singIn(View view){
        Intent intent = new Intent(this,SingIn.class);
        startActivity(intent);
    }
}
