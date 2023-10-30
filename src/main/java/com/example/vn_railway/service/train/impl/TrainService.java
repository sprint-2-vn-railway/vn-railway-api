package com.example.vn_railway.service.train.impl;

import com.example.vn_railway.common.FindAndHandleInEnum;
import com.example.vn_railway.dto.train_dto.ITrainDto;
import com.example.vn_railway.dto.train_dto.TrainResponse;
import com.example.vn_railway.model.train.Train;
import com.example.vn_railway.repository.train.ITrainRepository;
import com.example.vn_railway.service.train.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainService implements ITrainService {
    @Autowired
    private ITrainRepository trainRepository;

    @Override
    public List<TrainResponse> getAllTrain(String fromStationFirst, String toStationSecond, String startDate) {
        Integer fromStationNumber = FindAndHandleInEnum.getNumberByStation(fromStationFirst);
        Integer toStationNumber = FindAndHandleInEnum.getNumberByStation(toStationSecond);
        List<ITrainDto> trainDtoList;
        List<TrainResponse> trainResponseList = null;
        if (fromStationNumber == null || toStationNumber == null) {
            return null;
        } else {

            boolean check = fromStationNumber > toStationNumber;
            LocalDate date;

            try {
                String toStationFirst;
                String fromStationSecond;
                if (!check) {
                    toStationFirst = FindAndHandleInEnum.getNextStationNameByStation(fromStationFirst);
                    fromStationSecond = FindAndHandleInEnum.getPreviousStationNameByStation(toStationSecond);
                    date = LocalDate.parse(startDate);
                    trainDtoList = trainRepository.findAllTrain(
                            fromStationFirst, toStationFirst, fromStationSecond, toStationSecond, date);
                } else {
                    toStationFirst = FindAndHandleInEnum.getPreviousStationNameByStation(fromStationFirst);
                    fromStationSecond = FindAndHandleInEnum.getNextStationNameByStation(toStationSecond);
                    date = LocalDate.parse(startDate);
                    trainDtoList = trainRepository.findAllTrain(
                            fromStationFirst, toStationFirst, fromStationSecond, toStationSecond, date);
                }


                List<ITrainDto> result = new ArrayList<>();
                boolean flag = false;
                String currentCode = null;
                for (ITrainDto train : trainDtoList) {
                    String code = train.getTrainCode();
                    if (train.getFromStation().equals(fromStationFirst)
                            && train.getToStation().equals(toStationFirst)) {
                        currentCode = code;
                        flag = trainDtoList.stream().anyMatch(value ->
                                value.getTrainCode().equals(code)
                                        && value.getFromStation().equals(fromStationSecond)
                                        && value.getToStation().equals(toStationSecond)
                        );
                    }
                    if (currentCode != null && currentCode.equals(train.getTrainCode()) && flag) {
                        result.add(train);
                    }
                }

                Map<String, TrainResponse> resultMap = result.stream()
                        .collect(Collectors.groupingBy(
                                ITrainDto::getTrainCode,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        trains -> {
                                            TrainResponse responseTrain = new TrainResponse();
                                            responseTrain.setTrainCode(trains.get(0).getTrainCode());
                                            responseTrain.setTrainId(trains.get(0).getTrainId());
                                            responseTrain.setStartDate(trains.get(0).getStartDate());
                                            responseTrain.setEndDate(trains.get(trains.size() - 1).getEndDate());
                                            responseTrain.setFromStation(trains.get(0).getFromStation());
                                            responseTrain.setToStation(trains.get(trains.size()-1).getToStation());
                                            responseTrain.setTrainName(trains.get(0).getTrainName());
                                            responseTrain.setFirstTripId(trains.get(0).getTripId());
                                            responseTrain.setLastTripId(trains.get(trains.size() - 1).getTripId());
                                            responseTrain.setTotalDistanceLength(
                                                    (float) trains.stream().mapToDouble(ITrainDto::getDistanceLength).sum()
                                            );
                                            return responseTrain;
                                        }
                                )
                        ));
                Set<String> keySet = resultMap.keySet();
                trainResponseList = new ArrayList<>();
                for (String key : keySet) {
                    LocalDate curDate = resultMap.get(key).getStartDate().toLocalDateTime().toLocalDate();
                    if (date.equals(curDate)) {
                        trainResponseList.add(resultMap.get(key));
                    }
                }


                } catch(DateTimeException e){
                    System.out.println("DateTimeException");
                } catch(Exception e){
                    System.out.println("Exception");
                }

        }
        return trainResponseList;
    }

    @Override
    public List<Train> findAllTrain() {
        return trainRepository.findAll();
    }

}
