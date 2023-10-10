package com.example.vn_railway.dto;


import java.sql.Timestamp;
import java.util.Date;

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
