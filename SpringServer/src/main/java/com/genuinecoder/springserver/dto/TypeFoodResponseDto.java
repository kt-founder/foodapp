package com.genuinecoder.springserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class TypeFoodResponseDto {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("imageBase64")
    private String imageBase64; // Dữ liệu ảnh dưới dạng base64

    // Constructor không tham số
    public TypeFoodResponseDto() {}

    // Constructor với tất cả trường
    public TypeFoodResponseDto(int id, String name, byte[] img) {
        this.id = id;
        this.name = name;
        if (img != null) {
            this.imageBase64 = java.util.Base64.getEncoder().encodeToString(img); // Chuyển đổi ảnh từ byte[] sang base64
        }
    }
}
