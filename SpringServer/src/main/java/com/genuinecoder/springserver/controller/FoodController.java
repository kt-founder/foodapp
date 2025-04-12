package com.genuinecoder.springserver.controller;

import com.genuinecoder.springserver.dto.FoodResponseDto;
import com.genuinecoder.springserver.model.Food;
import com.genuinecoder.springserver.service.FoodDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/food")
@CrossOrigin("http://localhost:3000")
public class FoodController {

	@Autowired
	private FoodDao foodDao;

	// Lấy tất cả món ăn và trả về DTO
	@GetMapping
	public List<FoodResponseDto> getAllFood() {
		return foodDao.getAllFood();
	}
	@GetMapping("/auth/{id}")
	public List<FoodResponseDto> getAllFoodByAuthId(@PathVariable int id) {
		return foodDao.getAllFoodByUserId(id);
	}

	// Thêm món ăn mới
	@PostMapping
	public Food saveFood(@RequestBody Food food) {
		return foodDao.save(food);
	}

	// Cập nhật món ăn
	@PatchMapping("/{id}")
	public ResponseEntity<String> updateFood(@PathVariable Integer id, @RequestBody Food updatedFood) {
		return foodDao.update(id, updatedFood);
	}
	//Laay Mon An
	@GetMapping("/{id}")
	public FoodResponseDto getFoodById(@PathVariable Integer id) {
		return foodDao.getFoodById(id);
	}

	// Xóa món ăn
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFood(@PathVariable int id) {
		foodDao.deleteFoodById(id);
		return ResponseEntity.status(200).build();
	}

	// Lấy món ăn theo loại thực phẩm (TypeFood)
	@GetMapping("/type/{typeFoodId}")
	public List<FoodResponseDto> getFoodByTypeFood(@PathVariable int typeFoodId) {
		return foodDao.getFoodByTypeFood(typeFoodId);
	}

	// Tìm kiếm món ăn theo tên
	@GetMapping("/search")
	public List<FoodResponseDto> searchFoodByName(@RequestParam String name) {
		return foodDao.searchFoodByName(name);
	}
}
