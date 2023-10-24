package com.example.vn_railway.dto.train_dto;

import java.sql.Timestamp;

public interface ITicketDto {
     String getTrainCode();
     String getTrainName();
     String getCoachCode();
     String getTypeOfCoachName();
     String getSeatCode();
     Timestamp getStartDate();
     Timestamp getEndDate();
     String getCustomerName();
     Double getPrice();
     String getFromStation();
     String getToStation();
}
