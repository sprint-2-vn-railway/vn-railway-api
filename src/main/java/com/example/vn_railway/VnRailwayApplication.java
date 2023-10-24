package com.example.vn_railway;

import com.example.vn_railway.service.trip.ISeatService;
import com.example.vn_railway.service.trip.impl.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VnRailwayApplication {


	public static void main(String[] args) {
		SpringApplication.run(VnRailwayApplication.class, args);
	}

}
