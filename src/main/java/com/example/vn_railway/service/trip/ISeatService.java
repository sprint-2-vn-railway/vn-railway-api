package com.example.vn_railway.service.trip;

import com.example.vn_railway.dto.ISeatDto;

import java.util.List;

public interface ISeatService {
    List<ISeatDto> getAllSeatByCoachId(Long coachId, Long firstTripId, Long lastTripId);
}
