package com.genuinecoder.springserver.model.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorites, Integer>{
	List<Favorites> findByIdUser(int idUser);	
}
