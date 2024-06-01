package com.genuinecoder.springserver.model.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

