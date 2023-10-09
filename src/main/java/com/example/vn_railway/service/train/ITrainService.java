package com.example.vn_railway.service.train;

import com.example.vn_railway.dto.ITrainDto;
import com.example.vn_railway.model.train.Train;

import java.util.List;

public interface ITrainService {
    List<ITrainDto> getAllTrain(String fromStation, String toStation, String startDate);
}
