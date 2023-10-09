package com.example.vn_railway.service.train.impl;

import com.example.vn_railway.dto.ICoachDto;
import com.example.vn_railway.repository.train.ICoachRepository;
import com.example.vn_railway.service.train.ICoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.List;

@Service
public class CoachService implements ICoachService {
    @Autowired
    private ICoachRepository coachRepository;
    @Override
    public List<ICoachDto> getAllCoachByTrainId(Long trainId) {

        List<ICoachDto> coachDtoList = coachRepository.getCoachByTrainId(trainId);
        return coachDtoList;
    }
}
