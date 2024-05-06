package com.genuinecoder.springserver.controller;

import com.genuinecoder.springserver.model.employee.Food;
import com.genuinecoder.springserver.model.employee.EmployeeDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

  @Autowired
  private EmployeeDao employeeDao;

  @GetMapping("/employee/get-all")
  public List<Food> getAllEmployees() {
    return employeeDao.getAllEmployees();
  }

  @PostMapping("/employee/save")
  public Food save(@RequestBody Food employee) {
    return employeeDao.save(employee);
  }
}
