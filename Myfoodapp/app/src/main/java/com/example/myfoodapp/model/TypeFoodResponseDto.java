package com.example.myfoodapp.model;

import android.os.Build;

import java.io.Serializable;
import java.util.Base64;

public class TypeFoodResponseDto implements Serializable {

    private int id;
    private String name;
    private String imageBase64; // Dữ liệu ảnh dưới dạng base64

    // Constructor không tham số
    public TypeFoodResponseDto() {}

    // Constructor với tất cả trường
    public TypeFoodResponseDto(int id, String name, byte[] img) {
        this.id = id;
        this.name = name;
        if (img != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.imageBase64 = Base64.getEncoder().encodeToString(img); // Chuyển đổi ảnh từ byte[] sang base64
            }
        }
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
