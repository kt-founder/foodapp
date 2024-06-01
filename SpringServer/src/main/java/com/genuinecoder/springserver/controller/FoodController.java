package com.genuinecoder.springserver.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import com.genuinecoder.springserver.model.employee.TypeFood;
import com.genuinecoder.springserver.model.employee.TypeFoodDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.genuinecoder.springserver.model.employee.Food;
import com.genuinecoder.springserver.model.employee.FoodDao;
import com.mysql.cj.exceptions.ExceptionInterceptor;

import javax.imageio.ImageIO;
import javax.persistence.Lob;
@RestController
@CrossOrigin(value = "*")
public class FoodController {
	
	@Autowired
	private FoodDao foodDao;
	@Autowired
	private TypeFoodDAO typeFoodDAO;
	
	public static BufferedImage blobToImage(Blob blob) throws IOException, SQLException {
        // Đọc dữ liệu từ Blob
        byte[] imageData = blob.getBytes(1, (int) blob.length());

        // Tạo một ByteArrayInputStream từ dữ liệu Blob
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);

        // Đọc ảnh từ ByteArrayInputStream
        BufferedImage image = ImageIO.read(bais);

        return image;
    }
	@GetMapping("/food/get-all")
	public List<Food> getAllFoods() {
		return foodDao.getAllFood();
	}
	
	@PostMapping("/food/addFood")
	public ResponseEntity<String> addFoods(@RequestBody Food food) {
		Food savedFood = foodDao.save(food);
		
		return ResponseEntity.ok("Food added successfully.");

	}
	@PostMapping("food/delete/{id}")
	public ResponseEntity<String> deleteFoodById(@PathVariable int id) {
		foodDao.deleteFoodById(id);
		return ResponseEntity.ok("Food deleted successfully.");
	}


	@PutMapping("/food/update/{id}")
	public ResponseEntity<String> updateFood(@PathVariable int id, @RequestBody Food food) {
		return foodDao.update(id, food);
	}
}
