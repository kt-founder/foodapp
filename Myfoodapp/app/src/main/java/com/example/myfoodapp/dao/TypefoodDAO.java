package com.example.myfoodapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myfoodapp.dal.SQLiteHelper;
import com.example.myfoodapp.ui.home.Food_Type;

import java.util.ArrayList;
import java.util.List;

public class TypefoodDAO {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;

    public TypefoodDAO(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
        db = sqLiteHelper.getReadableDatabase(); // Sử dụng getReadableDatabase() vì bạn chỉ đọc dữ liệu
    }
    public List<Food_Type> getAllFoodTypes() {
        List<Food_Type> foodTypes = new ArrayList<>();

        // Thực hiện truy vấn để lấy toàn bộ dữ liệu từ bảng "typefood"
        Cursor cursor = db.rawQuery("SELECT * FROM typefood", null);

        // Kiểm tra xem cursor có null không và có dòng dữ liệu nào không
        if (cursor != null && cursor.getCount() > 0) {
            // Di chuyển con trỏ đến vị trí đầu tiên
            cursor.moveToFirst();
            do {
                // Kiểm tra xem cột có tồn tại trong kết quả truy vấn không
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("name");
                int imgIndex = cursor.getColumnIndex("img");
                if (idIndex != -1 && nameIndex != -1 && imgIndex != -1) {
                    // Đọc dữ liệu từ Cursor và tạo đối tượng Food_Type tương ứng
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    byte[] imgBytes = cursor.getBlob(imgIndex);

                    // Tạo đối tượng Food_Type và thêm vào danh sách
                    Food_Type foodType = new Food_Type(id, imgBytes,name);
                    foodTypes.add(foodType);
                }
            } while (cursor.moveToNext());

            // Đóng con trỏ sau khi sử dụng xong
            cursor.close();
        }

        return foodTypes;
    }

}

