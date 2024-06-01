package com.genuinecoder.springserver.model.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteDao {
	
	@Autowired
	private FavoriteRepository favoriteRepository;
	public List<Favorites> findByUserId(int id){
		return favoriteRepository.findByIdUser(id);
	}
	public void addFavorite(Favorites favorites) {
		favoriteRepository.save(favorites);
	}
	public void deleteFavorite(Favorites favorites) {
		favoriteRepository.delete(favorites);
	}
}
