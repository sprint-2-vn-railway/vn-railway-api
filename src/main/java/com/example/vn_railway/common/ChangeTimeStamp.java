package com.example.vn_railway.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeTimeStamp {
    public static String handleChangeFormatTimeStampToString(Timestamp time){
        // Tạo định dạng mới cho đầu ra
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Chuyển đổi Timestamp thành chuỗi với định dạng mới
        return sdf.format(time);

    }
}
