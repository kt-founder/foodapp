package com.genuinecoder.springserver.repository;

import com.genuinecoder.springserver.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer>{
    // Tìm kiếm món ăn theo tên (không phân biệt chữ hoa/thường)
    List<Food> findByNameContainingIgnoreCase(String name);

    // Tìm tất cả món ăn theo loại thực phẩm (TypeFood)
    List<Food> findFoodByTypefoods_Id(int typeFoodId);
    List<Food> findFoodByIdAut(int idAut);
    long count();

}
