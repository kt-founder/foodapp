package com.genuinecoder.springserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Base64;

@Data
public class FoodResponseDto {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("time")
    private String time;

    @JsonProperty("video")
    private String video;

    @JsonProperty("guide")
    private String guide;

    @JsonProperty("ingredient")
    private String ingredient;

    @JsonProperty("nutrition")
    private String nutrition;

    @JsonProperty("idAut")
    private int idAut;

    @JsonProperty("typefoods")
    private List<TypeFoodDto> typefoods;

    @JsonProperty("imageBase64")
    private String imageBase64;

    public FoodResponseDto() {}

    public FoodResponseDto(int id, String name, String detail, String time, String video, String guide,
                           String ingredient, String nutrition, int idAut, List<TypeFoodDto> typefoods, byte[] image) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.time = time;
        this.video = video;
        this.guide = guide;
        this.ingredient = ingredient;
        this.nutrition = nutrition;
        this.idAut = idAut;
        this.typefoods = typefoods;
        if (image != null) {
            this.imageBase64 = Base64.getEncoder().encodeToString(image);
        }
    }
}
