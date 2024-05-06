package com.example.myfoodapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myfoodapp.dal.SQLiteHelper;
import com.example.myfoodapp.model.Food_Type;

import java.util.ArrayList;
import java.util.List;

public class TypefoodDAO {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;

    public TypefoodDAO(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
        db = sqLiteHelper.getWritableDatabase(); // Sử dụng getWritableDatabase() để ghi dữ liệu
    }


    public List<Food_Type> getAllFoodTypes() {
        List<Food_Type> foodTypes = new ArrayList<>();
        db = sqLiteHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM typefood",null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do{
                    foodTypes.add(new Food_Type(cursor.getInt(0),cursor.getInt(1),cursor.getString(2)));
                }
                while (cursor.moveToNext());

            }
        }
        catch (Exception ex){

        }
        return foodTypes;
    }

}
