package com.example.vn_railway.service.trip.impl;

import com.example.vn_railway.common.TrainEnum;
import com.example.vn_railway.service.trip.IDistanceService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DistanceService implements IDistanceService {
    @Override
    public List<String> getAllDistanceFromTrainEnum() {
//        List<TrainEnum> trainEnumList = Arrays.asList(Arrays.stream(TrainEnum.values()).map());
        return null;
    }
}
