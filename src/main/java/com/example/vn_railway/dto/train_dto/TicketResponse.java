package com.example.vn_railway.dto.train_dto;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private String trainCode;
    private String trainName;
    private String coachCode;
    private String typeOfCoachName;
    private String seatCode;
    private String startDate;
    private String endDate;
    private String customerName;
    private Double price;
    private String fromStation;
    private String toStation;
    private String base64StringQRCode;
    private String mail;
    public TicketResponse(String trainCode, String trainName, String coachCode,
                          String typeOfCoachName, String seatCode, String startDate,
                          String endDate, String customerName, Double price, String fromStation,
                          String toStation, String mail) {
        this.trainCode = trainCode;
        this.trainName = trainName;
        this.coachCode = coachCode;
        this.typeOfCoachName = typeOfCoachName;
        this.seatCode = seatCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerName = customerName;
        this.price = price;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.mail = mail;
    }
}
