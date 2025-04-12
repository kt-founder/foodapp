package com.genuinecoder.springserver.service;

import com.genuinecoder.springserver.dto.StatisticResponseDto;
import com.genuinecoder.springserver.model.Food;
import com.genuinecoder.springserver.repository.FavoriteRepository;
import com.genuinecoder.springserver.repository.FoodRepository;
import com.genuinecoder.springserver.repository.TypeFoodRepository;
import com.genuinecoder.springserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private TypeFoodRepository typeFoodRepository;

    public StatisticResponseDto getAllStatistics() {
        StatisticResponseDto response = new StatisticResponseDto();

        // Thống kê tổng số người dùng
        response.setTotalUsers(userRepository.count());

        // Thống kê tổng số món ăn
        response.setTotalFoods(foodRepository.count());

        // Thống kê tổng số món ăn yêu thích
        response.setTotalFavorites(favoriteRepository.count());

        // Thống kê số lượng món ăn đã đăng
        response.setTotalRecipesUploaded(foodRepository.count());

        // Thống kê món ăn yêu thích nhất
//        Food mostFavoriteFood = favoriteRepository.findTopByOrderByFavoriteCountDesc();
//        response.setMostFavoriteFood(mostFavoriteFood.getName());
//        response.setMostFavoriteFoodCount(favoriteRepository.countByFoodId(mostFavoriteFood.getId()));
        // Thống kê tổng số loại món ăn
        response.setTotalTypeFoods(typeFoodRepository.count());

        return response;
    }
}
