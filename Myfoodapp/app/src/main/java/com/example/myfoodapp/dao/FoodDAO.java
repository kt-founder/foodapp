package com.example.myfoodapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.myfoodapp.dal.SQLiteHelper;

public class FoodDAO {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;

    public FoodDAO(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }
    public void insertFood(String name,String mota,byte[] img,String time,String type,String nguyenlieu,String nutrititon,String cachlam,String video){
        db = sqLiteHelper.getWritableDatabase();
        String sql="INSERT INTO food VALUES(null,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement =db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,mota);
        statement.bindBlob(3,img);
        statement.bindString(4,time);
        statement.bindString(5,type);
        statement.bindString(6,nguyenlieu);
        statement.bindString(7,nutrititon);
        statement.bindString(8,cachlam);
        statement.bindString(9,video);
        statement.executeInsert();
    }
}
