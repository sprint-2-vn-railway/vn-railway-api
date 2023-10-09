package com.example.vn_railway.common;

public class FindAndHandleInEnum {
    public static Integer getNumberByStation(String station) {
        for (TrainEnum train : TrainEnum.values()) {
            if (train.getStation().equalsIgnoreCase(station)) {
                return train.getNumber();
            }
        }
        return null;
    }
    public static String getNextStationNameByStation(String station) {
        TrainEnum[] trainEnumList = TrainEnum.values();
        try {
            for (int i = 0; i < trainEnumList.length - 1; i++) {
                if (trainEnumList[i].getStation().equalsIgnoreCase(station)) {
                    return trainEnumList[i + 1].getStation();
                }
            }
            return null;

        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
    public static String getPreviousStationNameByStation(String station) {
        TrainEnum[] trainEnumList = TrainEnum.values();
        try {
            for (int i = 1; i <= trainEnumList.length - 1; i++) {
                if (trainEnumList[i].getStation().equalsIgnoreCase(station)) {
                    return trainEnumList[i - 1].getStation();
                }
            }
            return null;

        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}
