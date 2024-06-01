package com.genuinecoder.springserver.controller;

import com.genuinecoder.springserver.model.employee.TypeFood;
import com.genuinecoder.springserver.model.employee.TypeFoodDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "*")

public class TypeFoodController {
    @Autowired
    private TypeFoodDAO typeFoodDAO;

    @GetMapping("/typefood/get-all")
    public List<TypeFood> getAllTypeFood() {
        return typeFoodDAO.getAllTypeFood();

    }


}
