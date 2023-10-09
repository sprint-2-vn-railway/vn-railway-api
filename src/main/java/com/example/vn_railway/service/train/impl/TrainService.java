package com.example.vn_railway.service.train.impl;

import com.example.vn_railway.common.FindAndHandleInEnum;
import com.example.vn_railway.common.TrainEnum;
import com.example.vn_railway.dto.ITrainDto;
import com.example.vn_railway.model.train.Train;
import com.example.vn_railway.repository.train.ITrainRepository;
import com.example.vn_railway.service.train.ITrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TrainService implements ITrainService {
    @Autowired
    private ITrainRepository trainRepository;

    @Override
    public List<ITrainDto> getAllTrain(String fromStationFirst, String toStationSecond, String startDate) {
        Integer fromStationNumber = FindAndHandleInEnum.getNumberByStation(fromStationFirst);
        Integer toStationNumber = FindAndHandleInEnum.getNumberByStation(toStationSecond);
        List<ITrainDto> trainDtoList = null;
        if (fromStationNumber == null || toStationNumber == null) {
            return null;
        } else {

            boolean check = fromStationNumber > toStationNumber;
            LocalDate date = null;

            try {
                if (!check) {
                    String toStationFirst = FindAndHandleInEnum.getNextStationNameByStation(fromStationFirst);
                    String fromStationSecond = FindAndHandleInEnum.getPreviousStationNameByStation(toStationSecond);
                    date = LocalDate.parse(startDate);
                    trainDtoList = trainRepository.findAllTrain(
                            fromStationFirst, toStationFirst, fromStationSecond, toStationSecond, date);
                } else {
                    String toStationFirst = FindAndHandleInEnum.getPreviousStationNameByStation(fromStationFirst);
                    String fromStationSecond = FindAndHandleInEnum.getNextStationNameByStation(toStationSecond);
                    date = LocalDate.parse(startDate);
                    trainDtoList = trainRepository.findAllTrain(
                            fromStationSecond, toStationSecond, fromStationFirst, toStationFirst, date);
                }
            } catch (DateTimeException e) {
                //validate format date
            } catch (Exception e) {
                //validate exception
            }

        }
    return trainDtoList;
    }

}
