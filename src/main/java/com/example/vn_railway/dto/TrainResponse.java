package com.example.vn_railway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
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

}
