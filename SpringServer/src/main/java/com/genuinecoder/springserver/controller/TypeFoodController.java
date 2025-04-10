package com.genuinecoder.springserver.controller;

import com.genuinecoder.springserver.dto.TypeFoodResponseDto;
import com.genuinecoder.springserver.model.TypeFood;
import com.genuinecoder.springserver.service.TypeFoodDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/typefood")
public class TypeFoodController {

    @Autowired
    private TypeFoodDAO typeFoodDAO;

    // Lấy tất cả TypeFood và trả về DTO
    @GetMapping
    public List<TypeFoodResponseDto> getAllTypeFood() {
        return typeFoodDAO.getAllTypeFood();
    }
    //
    @GetMapping("/{id}")
    public TypeFoodResponseDto getTypeFoodById(@PathVariable int id) {
        return typeFoodDAO.getTypeFoodById(id);
    }

    // Thêm TypeFood mới
    @PostMapping
    public TypeFood saveTypeFood(@RequestBody TypeFood typeFood) {
        return typeFoodDAO.save(typeFood);
    }

    // Cập nhật TypeFood
    @PutMapping("/{id}")
    public TypeFoodResponseDto updateTypeFood(@PathVariable int id, @RequestBody TypeFood updatedTypeFood) {
        return typeFoodDAO.updateTypeFood(id, updatedTypeFood);
    }

    // Xóa TypeFood
    @DeleteMapping
    public void deleteTypeFood(@RequestBody TypeFood typeFood) {
        typeFoodDAO.deleteTypeFood(typeFood);
    }
}
