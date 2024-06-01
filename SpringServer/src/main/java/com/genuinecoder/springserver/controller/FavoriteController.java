package com.genuinecoder.springserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genuinecoder.springserver.model.employee.FavoriteDao;
import com.genuinecoder.springserver.model.employee.Favorites;

@RestController
@CrossOrigin("*")
public class FavoriteController {

	@Autowired
	private FavoriteDao favoriteDao;
	
	@GetMapping("/getFavorite/")
	public List<Favorites> getByUserId(@RequestParam(value = "id", required = false) int id){
		return favoriteDao.findByUserId(id);
	}
	@PostMapping("/addFavorite")
	public ResponseEntity<String> addFavo(@RequestBody Favorites fv) {
		favoriteDao.addFavorite(fv);
		return ResponseEntity.ok("Them thanh cong mon an yeu thich");
	}
	@PostMapping("/Favorite/")
    public ResponseEntity<String> removeFavorite(@RequestBody Favorites favorites) {
		favoriteDao.deleteFavorite(favorites);	
        return ResponseEntity.ok("Xoa thanh cong");
    }
}
