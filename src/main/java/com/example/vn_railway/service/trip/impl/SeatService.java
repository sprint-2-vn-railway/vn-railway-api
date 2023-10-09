package com.example.vn_railway.service.trip.impl;

import com.example.vn_railway.dto.ISeatDto;
import com.example.vn_railway.repository.trip.ISeatRepository;
import com.example.vn_railway.service.train.ICoachService;
import com.example.vn_railway.service.trip.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService implements ISeatService {
    @Autowired
    private ISeatRepository seatRepository;

    @Override
    public List<ISeatDto> getAllSeatByCoachId(Long coachId, Long firstTripId, Long lastTripId) {
        return seatRepository.findAllSeatByCoachId(coachId, firstTripId, lastTripId);
    }
}
