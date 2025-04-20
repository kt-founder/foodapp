package com.genuinecoder.springserver.service;

import com.genuinecoder.springserver.model.Food;
import com.genuinecoder.springserver.dto.FoodResponseDto;
import com.genuinecoder.springserver.dto.TypeFoodDto;
import com.genuinecoder.springserver.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodDao {

	@Autowired
	private FoodRepository foodRepository;

	// Lấy tất cả món ăn và trả về DTOs
	public List<FoodResponseDto> getAllFood() {
		List<Food> foods = foodRepository.findAll();
		return foods.stream().map(this::convertToFoodResponseDto).collect(Collectors.toList());
	}
	public List<FoodResponseDto> getAllFoodByUserId(int userId) {
		List<Food> foods = foodRepository.findFoodByIdAut(userId);
		return foods.stream().map(this::convertToFoodResponseDto).collect(Collectors.toList());
	}
	public FoodResponseDto getFoodById(int id) {
		Food existingFood = foodRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Food not found"));
		return convertToFoodResponseDto(existingFood);
	}

	// Lưu món ăn mới
	public Food save(Food food) {
		return foodRepository.save(food);
	}

	// Xóa món ăn theo ID
	public void deleteFoodById(int id) {
		foodRepository.deleteById(id);
	}

	// Cập nhật món ăn
	public ResponseEntity<String> update(Integer id, Food updatedFood) {
		Food existingFood = foodRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Food not found"));

		if (updatedFood.getName() != null) {
			existingFood.setName(updatedFood.getName());
		}
		if (updatedFood.getDetail() != null) {
			existingFood.setDetail(updatedFood.getDetail());
		}
		if (updatedFood.getTime() != null) {
			existingFood.setTime(updatedFood.getTime());
		}
		if (updatedFood.getImage() != null) {
			existingFood.setImage(updatedFood.getImage());
		}
		if (updatedFood.getVideo() != null) {
			existingFood.setVideo(updatedFood.getVideo());
		}
		if (updatedFood.getGuide() != null) {
			existingFood.setGuide(updatedFood.getGuide());
		}
		if (updatedFood.getIngredient() != null) {
			existingFood.setIngredient(updatedFood.getIngredient());
		}
		if (updatedFood.getNutrition() != null) {
			existingFood.setNutrition(updatedFood.getNutrition());
		}
		existingFood.setIdAut(updatedFood.getIdAut());

		if (updatedFood.getTypefoods() != null && !updatedFood.getTypefoods().isEmpty()) {
			existingFood.setTypefoods(updatedFood.getTypefoods());
		}

		foodRepository.save(existingFood);

		return ResponseEntity.ok("Food updated successfully.");
	}


	// Lấy tất cả món ăn theo loại thực phẩm (TypeFood)
	public List<FoodResponseDto> getFoodByTypeFood(int typeFoodId) {
		List<Food> foods = foodRepository.findFoodByTypefoods_Id(typeFoodId); // Gọi phương thức trong repository
		return foods.stream().map(this::convertToFoodResponseDto).collect(Collectors.toList());
	}

	// Tìm kiếm món ăn theo tên
	public List<FoodResponseDto> searchFoodByName(String name) {
		List<Food> foods = foodRepository.findByNameContainingIgnoreCase(name); // Tìm kiếm không phân biệt chữ hoa/thường
		return foods.stream().map(this::convertToFoodResponseDto).collect(Collectors.toList());
	}

	// Chuyển đổi Food thành FoodResponseDto
	private FoodResponseDto convertToFoodResponseDto(Food food) {
		List<TypeFoodDto> typeFoodDtos = food.getTypefoods().stream()
				.map(typeFood -> new TypeFoodDto(typeFood.getId(), typeFood.getName()))
				.collect(Collectors.toList());

		return new FoodResponseDto(
				food.getId(),
				food.getName(),
				food.getDetail(),
				food.getTime(),
				food.getVideo(),
				food.getGuide(),
				food.getIngredient(),
				food.getNutrition(),
				food.getIdAut(),
				typeFoodDtos,
				food.getImage() // Truyền ảnh vào để chuyển đổi thành base64
		);
	}
}
