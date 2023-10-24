package com.example.vn_railway.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VNPayResponse implements Serializable {
    private String status;
    private String message;
    private String URL;

}
