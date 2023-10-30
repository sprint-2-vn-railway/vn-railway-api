package com.example.vn_railway.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeTime {
    public static String handleChangeFormatTimeStampToString(Timestamp time) {
        // Tạo định dạng mới cho đầu ra
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Chuyển đổi Timestamp thành chuỗi với định dạng mới
        return sdf.format(time);

    }

    public static Date formatDateFromString(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.parse(date);
    }
}
