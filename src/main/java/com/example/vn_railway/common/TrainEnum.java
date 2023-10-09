package com.example.vn_railway.common;

public enum  TrainEnum {
    HANOI("Hà Nội", 1),
    VINH("Vinh", 2),
    HUE("Huế", 3),
    DANANG("Đà Nẵng", 4),
    NHATRANG("Nha Trang", 5),
    TPHCM("TP Hồ Chí Minh", 6);

    private String station;
    private Integer number;

    TrainEnum(String station, Integer number) {
        this.station = station;
        this.number = number;
    }

    public String getStation() {
        return station;
    }

    public Integer getNumber() {
        return number;
    }
}
