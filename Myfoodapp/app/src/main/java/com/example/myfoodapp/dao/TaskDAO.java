package com.example.myfoodapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.myfoodapp.database.DBHelper;

public class TaskDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public TaskDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
}
