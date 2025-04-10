package com.genuinecoder.springserver.service;

import com.genuinecoder.springserver.dto.TypeFoodResponseDto;
import com.genuinecoder.springserver.model.TypeFood;
import com.genuinecoder.springserver.repository.TypeFoodRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeFoodDAO {

    @Autowired
    private TypeFoodRepository typeFoodRepository;

    // Lấy tất cả TypeFood và chuyển đổi sang TypeFoodResponseDto
    public List<TypeFoodResponseDto> getAllTypeFood() {
        List<TypeFood> typeFoods = (List<TypeFood>) typeFoodRepository.findAll();
        return typeFoods.stream()
                .map(this::convertToTypeFoodResponseDto) // Chuyển đổi TypeFood thành TypeFoodResponseDto
                .collect(Collectors.toList());
    }
    public TypeFoodResponseDto getTypeFoodById(int id) {
        TypeFood existingTypeFood = typeFoodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeFood not found"));
        return convertToTypeFoodResponseDto(existingTypeFood);
    }

    // Lưu TypeFood
    public TypeFood save(TypeFood typeFood) {
        return typeFoodRepository.save(typeFood);
    }

    // Xóa TypeFood
    public void deleteTypeFood(TypeFood typeFood) {
        typeFoodRepository.delete(typeFood);
    }

    // Cập nhật TypeFood
    public TypeFoodResponseDto updateTypeFood(int id, TypeFood updatedTypeFood) {
        TypeFood existingTypeFood = typeFoodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeFood not found"));

        existingTypeFood.setName(updatedTypeFood.getName());
        existingTypeFood.setImg(updatedTypeFood.getImg()); // Cập nhật ảnh mới

        typeFoodRepository.save(existingTypeFood);

        // Trả về TypeFoodResponseDto
        return convertToTypeFoodResponseDto(existingTypeFood);
    }

    // Chuyển đổi TypeFood thành TypeFoodResponseDto
    private TypeFoodResponseDto convertToTypeFoodResponseDto(TypeFood typeFood) {
        return new TypeFoodResponseDto(
                typeFood.getId(),
                typeFood.getName(),
                typeFood.getImg() // Truyền byte[] ảnh vào DTO để mã hóa thành base64
        );
    }
}
