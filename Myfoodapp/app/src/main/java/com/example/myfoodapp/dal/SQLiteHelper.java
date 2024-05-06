package com.example.myfoodapp.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myfoodapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FoodApp.db";
    private static final int DATABASE_VERSION = 7;
    private static final String TAG = "SQLiteHelper";
    private final Context context;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        typefood
        String tbtypefood = "CREATE TABLE typefood (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "  +
               "img INTEGER," +
                "name TEXT" +
                ")";
        db.execSQL(tbtypefood);
//        food
        String tbfood ="CREATE TABLE food (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,mota TEXT,img BLOB,time TEXT,"+
                "type TEXT,nguyenlieu TEXT,nutrition TEXT,cachlam TEXT,video TEXT);";
        db.execSQL(tbfood);

        insertSampleData(db);
    }
    private void insertSampleData(SQLiteDatabase db) {
        String[] Names = {"Bún phở", "Hải Sản", "Thịt Bò", "Thịt gà","Thịt heo", "Nộm-Gỏi", "Cháo", "Món Chay",
                "Bánh Mì-Xôi",  "Món Xào", "Món Kho", "Món Chiên","Đồ ăn vặt", "Món Tết","Tất cả"};
        int[] Images = {R.drawable.tf_bunpho, R.drawable.tf_haisan, R.drawable.tf_thitbo,
                R.drawable.tf_thitga,R.drawable.tf_thitheo, R.drawable.tf_nom, R.drawable.tf_chao,
                R.drawable.tf_chay, R.drawable.tf_banhmi_xoi,
                R.drawable.tf_xao, R.drawable.tf_kho, R.drawable.tf_monchien,R.drawable.tf_anvat,
                R.drawable.tf_montet,R.drawable.tf_tatca};

        db.beginTransaction();
        for (int i = 0; i < Names.length; i++) {
            ContentValues values = new ContentValues();

            values.put("name", Names[i]);
            values.put("img",Images[i] );
            db.insert("typefood", null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: Database upgrade from version " + oldVersion + " to " + newVersion);
        if (oldVersion < newVersion) { // Đảm bảo rằng oldVersion < newVersion để chạy lại chỉ khi có sự thay đổi
            db.execSQL("DROP TABLE IF EXISTS typefood");
            db.execSQL("DROP TABLE IF EXISTS food");
            db.execSQL("DROP TABLE IF EXISTS nutrition");
            db.execSQL("DROP TABLE IF EXISTS food_typefood");
            onCreate(db);
        }
    }

}
