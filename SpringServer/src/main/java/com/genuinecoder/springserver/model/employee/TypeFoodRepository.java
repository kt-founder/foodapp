package com.genuinecoder.springserver.model.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TypeFoodRepository extends JpaRepository<TypeFood, Integer> {
}
