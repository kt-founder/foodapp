package com.genuinecoder.springserver.repository;

import java.util.List;

import com.genuinecoder.springserver.model.Favorites;
import com.genuinecoder.springserver.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorites, Integer>{
	List<Favorites> findByIdUser(int idUser);
	long countByFoodId(long foodId);
}
