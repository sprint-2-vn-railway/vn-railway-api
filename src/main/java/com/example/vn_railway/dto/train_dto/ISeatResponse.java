package com.example.vn_railway.dto.train_dto;

import java.sql.Timestamp;

public interface ISeatResponse {
    String getSeatCode();

    String getUserName();
    String getTrainCode();
    Long getCoachId();

    Long getLastTripId();

    Long getFirstTripId();

    Long getTrainId();

    String getTrainName();

    String getCoachCode();

    Timestamp getStartDate();
    Long getTotalDistance();
    Double getPrice();
}
