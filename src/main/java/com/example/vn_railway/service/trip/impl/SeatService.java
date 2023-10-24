package com.example.vn_railway.service.trip.impl;

import com.example.vn_railway.dto.train_dto.ISeatDto;
import com.example.vn_railway.dto.train_dto.ISeatResponse;
import com.example.vn_railway.dto.train_dto.SeatPayload;
import com.example.vn_railway.repository.trip.ISeatRepository;
import com.example.vn_railway.service.trip.ISeatService;
import com.example.vn_railway.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService implements ISeatService {
    private final ISeatRepository seatRepository;
    private final IUserService userService;

    @Override
    public List<ISeatDto> getAllSeatByCoachId(Long coachId, Long firstTripId, Long lastTripId) {



        return seatRepository.findAllSeatByCoachId(coachId, firstTripId, lastTripId);
    }

    @Override
    public boolean updateSelectedSeat(SeatPayload seatPayload,Boolean clear) {
        Long userId = userService.getUserIdByUserName(seatPayload.getUserName());
        if (userId == null) return false;
        if(clear) return seatRepository.clearSelectedSeatByUserId(seatPayload,userId) > 0;
        return seatRepository.updateSelectedSeat(seatPayload,userId) > 0;
    }

    @Override
    public void clearAllUserIdInSeatEntity() {
        seatRepository.ClearAllUserIdInSeatEntity();
    }

    @Override
    public ISeatDto getSeatByCoachIdAndCode(Long coachId, Long firstTripId, Long lastTripId,String seatCode) {
        return seatRepository.getSeatByCoachIdAndCode(coachId,firstTripId,lastTripId,seatCode);
    }

    @Override
    public boolean clearUserIdInSeatEntity(String userName) {
        Long userId = userService.getUserIdByUserName(userName);
        if (userId == null) return false;
        return  seatRepository.clearUserIdInSeatEntity(userId) > 0;
    }

    @Override
    public List<ISeatResponse> getAllSeatByUserName(String userName) {
        return seatRepository.getAllSeatByUserName(userName) ;
    }
}
