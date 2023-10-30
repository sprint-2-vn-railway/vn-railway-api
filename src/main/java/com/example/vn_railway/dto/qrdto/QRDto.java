package com.example.vn_railway.dto.qrdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QRDto {
    private String trainCode;
    private String coachCode;
    private String seatCode;
    private String startDate;
    private String fromStation;
}
