package com.example.vn_railway.service.train;

import com.example.vn_railway.dto.train_dto.TrainResponse;

import java.util.List;

public interface ITrainService {
    List<TrainResponse> getAllTrain(String fromStation, String toStation, String startDate);
}
