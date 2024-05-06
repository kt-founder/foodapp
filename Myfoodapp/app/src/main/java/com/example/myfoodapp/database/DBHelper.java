package com.example.myfoodapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context,"DBTask", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sSQLTask = "CREATE TABLE TASKS(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITTLE TEXT, CONTENT TEXT, DATE TEXT, TYPE TEXT)";
        db.execSQL(sSQLTask);
        String sSQLInsert = "INSERT INTO TASKS (ID,TITTLE,CONTENT,DATE,TYPE) VALUES (\n" +
                "  '1',\n" +
                "  'android',\n" +
                "  'Hoc android co ban',\n" +
                "  '16/4/2024',\n" +
                "  'De'\n" +
                "), (\n" +
                "  '2',\n" +
                "  'android2',\n" +
                "  'Hoc android co ban 2',\n" +
                "  '16/4/2024',\n" +
                "  'De'\n" +
                "); ";
        db.execSQL(sSQLInsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS TASKS");
            onCreate(db);
        }
    }
}
