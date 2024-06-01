package com.genuinecoder.springserver.model.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeFoodDAO {
    @Autowired
    private TypeFoodRepository typeFoodRepository;
    public List<TypeFood> getAllTypeFood() { return (List<TypeFood>) typeFoodRepository.findAll();}
    public TypeFood save(TypeFood typeFood) {return typeFoodRepository.save(typeFood);}
    public void deleteTypeFood(TypeFood typeFood) { typeFoodRepository.delete(typeFood);}

}
