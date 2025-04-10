package com.genuinecoder.springserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FavoritesDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("idUser")
    private int idUser;
    @JsonProperty("foodId")// ID của người dùng
    private int foodId;  // ID của món ăn
    @JsonProperty("foodName")
    private String foodName;  // Tên của món ăn
    @JsonProperty("foodImageBase64")
    private String foodImageBase64;  // Ảnh của món ăn dưới dạng base64

    // Constructor không tham số
    public FavoritesDto() {}

    // Constructor với tất cả trường
    public FavoritesDto(int id, int idUser, int foodId, String foodName, byte[] foodImage) {
        this.id = id;
        this.idUser = idUser;
        this.foodId = foodId;
        this.foodName = foodName;
        if (foodImage != null) {
            this.foodImageBase64 = java.util.Base64.getEncoder().encodeToString(foodImage); // Chuyển đổi ảnh từ byte[] sang base64
        }
    }
}
