package com.example.vn_railway.dto.train_dto;

public interface ISeatDto {
    String getSeatCode();
    Boolean getAvailable();
    Long getCoachId();
    String getUserName();
}
