package com.genuinecoder.springserver.service;

import com.genuinecoder.springserver.model.User;
import com.genuinecoder.springserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDAO {

    @Autowired
    private UserRepository userRepository;

    // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
    public User registerUser(User user) {
        // Mã hóa mật khẩu
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword); // Lưu mật khẩu đã mã hóa

        // Thêm role mặc định là "admin" khi đăng ký
        if (user.getRole() == null) {
            user.setRole("user"); // Giả sử khi tạo người dùng mới, sẽ mặc định là "admin"
        }

        return userRepository.save(user);
    }
    // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
    public User addUser(User user) {
        // Mã hóa mật khẩu
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword); // Lưu mật khẩu đã mã hóa

        // Thêm role mặc định là "admin" khi đăng ký
        if (user.getRole() == null) {
            user.setRole("user"); // Giả sử khi tạo người dùng mới, sẽ mặc định là "admin"
        }

        return userRepository.save(user);
    }

    // Đổi mật khẩu
    public User updatePassword(int userId, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            return userRepository.save(user);
        }
        return null;
    }
    public boolean checkOldPassword(int id, String oldPassword) {
        Optional<User> user = userRepository.findById(id);
        return user.filter(value -> BCrypt.checkpw(oldPassword, value.getPassword())).isPresent();
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy người dùng theo ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Tìm người dùng theo tên đăng nhập
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
