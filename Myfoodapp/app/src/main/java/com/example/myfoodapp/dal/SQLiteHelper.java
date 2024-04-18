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
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "SQLiteHelper";
    private final Context context;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbtypefood = "CREATE TABLE typefood (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "  +
                "img BLOB," +
                "name TEXT" +
                ")";
        db.execSQL(tbtypefood);

        String tbfood ="CREATE TABLE food (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "img INTEGER," +
                "name TEXT," +
                "glucid INTEGER," +
                "lipit INTEGER," +
                "protid INTEGER," +
                "chatxo INTEGER," +
                "calo INTEGER," +
                "nguyenlieu TEXT" +
                ")";

        db.execSQL(tbfood);

        // Thêm dữ liệu mẫu vào bảng typefood
        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        String[] foodNames = {"Bún phở", "Hải Sản", "Thịt Bò", "Thịt gà", "Nộm-Gỏi", "Cháo", "Món Chay",
                "Bánh Mì-Xôi", "Cơm", "Món Xào", "Món Kho", "Món Chiên", "Món Tết"};
        int[] foodImages = {R.drawable.tf_bunpho, R.drawable.tf_haisan, R.drawable.tf_thitbo,
                R.drawable.tf_thitga, R.drawable.tf_nom, R.drawable.tf_chao,
                R.drawable.tf_chay, R.drawable.tf_banhmi_xoi, R.drawable.tf_com,
                R.drawable.tf_xao, R.drawable.tf_kho, R.drawable.tf_monchien,
                R.drawable.tf_montet};

        db.beginTransaction();
        for (int i = 0; i < foodImages.length; i++) {
            ContentValues values = new ContentValues();
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), foodImages[i]);
            if (bitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                values.put("name", foodNames[i]);
                values.put("img", byteArray);
                db.insert("typefood", null, values);
                // Đóng luồng stream sau khi sử dụng
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: Database upgrade from version " + oldVersion + " to " + newVersion);
    }

}
