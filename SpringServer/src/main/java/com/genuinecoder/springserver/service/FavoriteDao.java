package com.genuinecoder.springserver.service;

import com.genuinecoder.springserver.dto.FavoritesDto;
import com.genuinecoder.springserver.model.Favorites;
import com.genuinecoder.springserver.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteDao {

	@Autowired
	private FavoriteRepository favoriteRepository;

	// Lấy danh sách yêu thích của người dùng theo id và thêm tên + ảnh món ăn
	public List<FavoritesDto> findByUserId(int id) {
		List<Favorites> favoritesList = favoriteRepository.findByIdUser(id);
		return favoritesList.stream()
				.map(this::convertToFavoritesDto) // Chuyển đổi thành FavoritesDto
				.collect(Collectors.toList());
	}
	public List<FavoritesDto> findAll() {
		List<Favorites> favoritesList = favoriteRepository.findAll();
		return favoritesList.stream()
				.map(this::convertToFavoritesDto) // Chuyển đổi thành FavoritesDto
				.collect(Collectors.toList());
	}


	// Thêm món ăn vào danh sách yêu thích
	public void addFavorite(Favorites favorites) {
		favoriteRepository.save(favorites);
	}

	// Xóa món ăn khỏi danh sách yêu thích
	public void deleteFavorite(int id) {
		favoriteRepository.deleteById(id);
	}

	// Chuyển đổi Favorites thành FavoritesDto, bao gồm tên và ảnh của món ăn
	private FavoritesDto convertToFavoritesDto(Favorites favorites) {
		String foodName = favorites.getFood().getName(); // Lấy tên món ăn
		byte[] foodImage = favorites.getFood().getImage(); // Lấy ảnh món ăn

		return new FavoritesDto(
				favorites.getId(),
				favorites.getIdUser(),
				favorites.getFood().getId(),
				foodName,
				foodImage
		);
	}
}
