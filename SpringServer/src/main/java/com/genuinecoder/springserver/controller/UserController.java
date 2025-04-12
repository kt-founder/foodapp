package com.genuinecoder.springserver.controller;

import com.genuinecoder.springserver.dto.ChangePasswordRequest;
import com.genuinecoder.springserver.model.User;
import com.genuinecoder.springserver.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    // Đăng ký người dùng mới
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userDAO.registerUser(user); // Lưu người dùng với mật khẩu đã mã hóa
    }
    // Đăng ký người dùng mới
    @PostMapping("/add")
    public User add(@RequestBody User user) {
        return userDAO.addUser(user); // Lưu người dùng với mật khẩu đã mã hóa
    }

    // Đổi mật khẩu người dùng
    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(
            @PathVariable int id,
            @RequestBody ChangePasswordRequest request) {

        // Kiểm tra mật khẩu cũ
        boolean isOldPasswordValid = userDAO.checkOldPassword(id, request.getOldPassword());
        if (!isOldPasswordValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu cũ không đúng.");
        }

        // Tiến hành thay đổi mật khẩu nếu mật khẩu cũ đúng
        User updatedUser = userDAO.updatePassword(id, request.getNewPassword());
        return ResponseEntity.ok("Mật khẩu đã được thay đổi.");
    }



    // Lấy tất cả người dùng
    @GetMapping
    public List<User> getAllUsers() {
        return userDAO.getAllUsers(); // Lấy tất cả người dùng
    }

    // Lấy người dùng theo ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userDAO.getUserById(id); // Lấy người dùng theo ID
    }

    // Đăng nhập người dùng
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User foundUser = userDAO.findByUsername(user.getUsername());
        if (foundUser != null && BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.ok(foundUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username hoặc mật khẩu không đúng");
    }
}
