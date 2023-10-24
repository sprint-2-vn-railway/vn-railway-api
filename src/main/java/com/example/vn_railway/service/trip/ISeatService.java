package com.example.vn_railway.service.trip;

import com.example.vn_railway.dto.train_dto.ISeatDto;
import com.example.vn_railway.dto.train_dto.ISeatResponse;
import com.example.vn_railway.dto.train_dto.SeatPayload;

import java.util.List;

public interface ISeatService {
    List<ISeatDto> getAllSeatByCoachId(Long coachId, Long firstTripId, Long lastTripId);

    boolean updateSelectedSeat(SeatPayload seatPayload,Boolean clear);

    void clearAllUserIdInSeatEntity();

    ISeatDto getSeatByCoachIdAndCode(Long coachId, Long firstTripId, Long lastTripId,String seatCode);

    boolean clearUserIdInSeatEntity(String userName);

    List<ISeatResponse> getAllSeatByUserName(String userName);

}
