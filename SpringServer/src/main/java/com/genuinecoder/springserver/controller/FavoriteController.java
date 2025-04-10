package com.genuinecoder.springserver.controller;

import com.genuinecoder.springserver.dto.FavoritesDto;
import com.genuinecoder.springserver.model.Favorites;
import com.genuinecoder.springserver.service.FavoriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

	@Autowired
	private FavoriteDao favoriteDao;

	// Lấy danh sách yêu thích của người dùng, bao gồm tên và ảnh món ăn
	@GetMapping("/user/{userId}")
	public List<FavoritesDto> getFavoritesByUserId(@PathVariable int userId) {
		return favoriteDao.findByUserId(userId);
	}

	// Thêm món ăn vào danh sách yêu thích
	// Thêm món ăn vào danh sách yêu thích
	@PostMapping
	public ResponseEntity<Void> addFavorite(@RequestBody Favorites favorites) {
		favoriteDao.addFavorite(favorites);
		return ResponseEntity.status(201).build(); // Trả về HTTP 201 Created
	}

	// Xóa món ăn khỏi danh sách yêu thích
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFavorite(@PathVariable int id) {
		favoriteDao.deleteFavorite(id);
		return ResponseEntity.status(200).build(); // Trả về HTTP 200 OK
	}
}
