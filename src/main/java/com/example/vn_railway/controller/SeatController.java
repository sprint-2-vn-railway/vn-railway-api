package com.example.vn_railway.controller;

import com.example.vn_railway.dto.train_dto.ISeatResponse;
import com.example.vn_railway.service.trip.ISeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/api/seat")
@Controller
@RequiredArgsConstructor
public class SeatController {
    private final ISeatService seatService;

    @GetMapping("/get-all-temporary-seat-by-userName/{userName}")
    public ResponseEntity<?> getAllTemporarySeatByUserName(@PathVariable(required = false) String userName){
        List<ISeatResponse> seatResponseList = seatService.getAllSeatByUserName(userName);
        if(seatResponseList == null) return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Chưa đặt ghế");
        return ResponseEntity.ok(seatResponseList);
    }
}
