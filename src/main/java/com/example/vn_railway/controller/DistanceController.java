package com.example.vn_railway.controller;

import com.example.vn_railway.common.TrainEnum;
import com.example.vn_railway.service.trip.IDistanceService;
import com.example.vn_railway.service.trip.impl.DistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/distance")
@RequiredArgsConstructor
public class DistanceController {
    private final IDistanceService distanceService;
    @GetMapping("/list")
    public ResponseEntity<?> getAllListDistance(){
        List<String> trainEnumList = distanceService.getAllDistanceFromTrainEnum();
        return ResponseEntity.ok(trainEnumList);
    }
}
