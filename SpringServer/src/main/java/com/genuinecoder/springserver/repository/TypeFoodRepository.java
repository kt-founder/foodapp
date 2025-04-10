package com.genuinecoder.springserver.repository;

import com.genuinecoder.springserver.model.TypeFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeFoodRepository extends JpaRepository<TypeFood, Integer> {
}
