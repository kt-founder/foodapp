package com.genuinecoder.springserver.model.employee;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDao {

  @Autowired
  private EmployeeRepository repository;

  public Food save(Food employee) {
    return repository.save(employee);
  }

  public List<Food> getAllEmployees() {
    List<Food> employees = new ArrayList<>();
    Streamable.of(repository.findAll())
        .forEach(employees::add);
    return employees;
  }

  public void delete(int employeeId) {
    repository.deleteById(employeeId);
  }
}
