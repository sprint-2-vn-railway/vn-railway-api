package com.example.vn_railway.dto.train_dto;


import java.sql.Timestamp;

public interface ITrainDto {

     Long getTrainId();
     String getTrainCode();
     String getTrainName();
     Long getTripId();
     Timestamp getStartDate();
     Timestamp getEndDate();
     Float getDistanceLength();
     String getFromStation();
     String getToStation();
}
