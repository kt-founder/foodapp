package com.genuinecoder.springserver.model.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FoodDao {

	@Autowired
	private FoodRepository foodRepository;
	public List<Food> getAllFood(){
		return (List<Food>) foodRepository.findAll();
	}
	public Food save( Food food) {
		return foodRepository.save(food);
	}
	public void deleteFoodById(int id) {
		foodRepository.deleteById(id);
	}
	public ResponseEntity<String> update(Integer id, Food updatedFood) {
		Food existingFood = foodRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Food not found"));

		existingFood.setName(updatedFood.getName());
		existingFood.setDetail(updatedFood.getDetail());
		existingFood.setTime(updatedFood.getTime());
		existingFood.setImage(updatedFood.getImage());
		existingFood.setVideo(updatedFood.getVideo());
		existingFood.setGuide(updatedFood.getGuide());
		existingFood.setIngredient(updatedFood.getIngredient());
		//existingFood.setState(updatedFood.isState());
		existingFood.setIdAut(updatedFood.getIdAut());

		foodRepository.save(existingFood);

		return ResponseEntity.ok("Food updated successfully.");
	}

}
