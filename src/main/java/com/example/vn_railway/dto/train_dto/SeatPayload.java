package com.example.vn_railway.dto.train_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatPayload {
    private Long trainId;
    private Long coachId;
    private Long firstTripId;
    private Long lastTripId;
    private String userName;
    private String seatCode;
}
