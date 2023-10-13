package com.example.vn_railway.dto.train_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainResponse {
    private Long trainId;
    private String trainCode;
    private String trainName;
    private Long firstTripId;
    private Long lastTripId;
    private Timestamp startDate;
    private Timestamp endDate;
    private Float totalDistanceLength;
    private String fromStation;
    private String toStation;

}
