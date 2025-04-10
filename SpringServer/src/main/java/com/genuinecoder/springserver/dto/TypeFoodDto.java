package com.genuinecoder.springserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TypeFoodDto {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;

    public TypeFoodDto() {}

    public TypeFoodDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
