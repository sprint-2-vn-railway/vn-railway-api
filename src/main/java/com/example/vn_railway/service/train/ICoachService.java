package com.example.vn_railway.service.train;


import com.example.vn_railway.dto.train_dto.ICoachDto;

import java.util.List;

public interface ICoachService {
    List<ICoachDto> getAllCoachByTrainId(Long trainId);
}
