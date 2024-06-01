package com.genuinecoder.springserver.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.genuinecoder.springserver.model.employee.Food;
import com.genuinecoder.springserver.model.employee.User;
import com.genuinecoder.springserver.model.employee.UserDAO;

@RestController
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserDAO userDAO;
	
	@PostMapping("/register")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		User newUser = userDAO.registerUser(user);
		return ResponseEntity.ok("User added successfully.");
	}
	

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginDetails) {
        User user = userDAO.findByUsername(loginDetails.getUsername());
        if (user != null && user.getPassword().equals(loginDetails.getPassword())) {
            return ResponseEntity.ok(new User(user.getId(),user.getUsername(),user.getRole()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}
