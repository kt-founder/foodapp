package com.genuinecoder.springserver.model.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodDao {
	
	@Autowired
	private FoodRepository foodRepository;
	
	public List<Food> getAllFoods(){
		return (List<Food>) foodRepository.findAll();
	}
	public Food save(Food food) {
		return foodRepository.save(food);
	}
	public void delete(Food food) {
		foodRepository.delete(food);
	}
	
}
