package com.genuinecoder.springserver.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.genuinecoder.springserver.model.employee.Food;
import com.genuinecoder.springserver.model.employee.FoodDao;
import com.mysql.cj.exceptions.ExceptionInterceptor;

import javax.imageio.ImageIO;
import javax.persistence.Lob;
@RestController
public class FoodController {
	
	@Autowired
	private FoodDao foodDao;
	
	public static BufferedImage blobToImage(Blob blob) throws IOException, SQLException {
        // Đọc dữ liệu từ Blob
        byte[] imageData = blob.getBytes(1, (int) blob.length());

        // Tạo một ByteArrayInputStream từ dữ liệu Blob
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);

        // Đọc ảnh từ ByteArrayInputStream
        BufferedImage image = ImageIO.read(bais);

        return image;
    }
	@GetMapping("food/get-all")
	public List<Food> getAFoods() throws SQLException, IOException{
		System.out.println(1);
		List<Food> image = foodDao.getAllFoods();
//        for(Food i:image) {
//        	Blob blob = i.getImage();// Retrieve blob from database
//        	System.out.println(1);
//        	System.out.println(blob);
//	        // Convert blob to byte array
//
//	       byte[] a = blob.getBytes(1, (int) blob.length());
//
//	        // Convert byte array to base64 string
//	        String base64Data = Base64.getEncoder().encodeToString(a);
//	        BufferedImage im = blobToImage(blob);
//	        i.setBase64(base64Data);
//	        i.setImage(null);
//
//        }
        return image;
		
	}
	@PostMapping("/food/save")
	public Food addFoods(@RequestBody Food food) {
		return foodDao.save(food);
		
	}
}
