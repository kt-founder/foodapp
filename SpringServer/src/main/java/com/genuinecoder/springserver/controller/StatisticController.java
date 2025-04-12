package com.genuinecoder.springserver.controller;

import com.genuinecoder.springserver.dto.StatisticResponseDto;
import com.genuinecoder.springserver.service.StatisticDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin("http://localhost:3000")
public class StatisticController {

    @Autowired
    private StatisticDao statisticDao;

    // API để lấy tất cả thông tin thống kê
    @GetMapping("/all")
    public ResponseEntity<StatisticResponseDto> getAllStatistics() {
        StatisticResponseDto statistics = statisticDao.getAllStatistics();
        return ResponseEntity.ok(statistics);
    }
}

