package com.example.vn_railway.controller;

import com.example.vn_railway.common.ChangeTime;
import com.example.vn_railway.common.TrainEnum;
import com.example.vn_railway.dto.train_dto.ICoachDto;
import com.example.vn_railway.dto.train_dto.ISeatDto;
import com.example.vn_railway.dto.train_dto.TrainResponse;
import com.example.vn_railway.service.train.IChairService;
import com.example.vn_railway.service.train.ICoachService;
import com.example.vn_railway.service.train.ITrainService;
import com.example.vn_railway.service.trip.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private ITrainService trainService;
    @Autowired
    private ICoachService coachService;

    @Autowired
    private ISeatService seatService;

    @GetMapping("/get-all-train")
    public ResponseEntity<?> getAllTrain(@RequestParam(required = false) String fromStation,
                                         @RequestParam(required = false) String toStation,
                                         @RequestParam(required = false) String startDate) {
        //Validate
        Map<String, String> errMap = new HashMap<>();
        if (fromStation == null || fromStation.trim().equals("")) {
            errMap.put("fromStation", "Không để trống ga đi");
        } else {

        }

        if (toStation == null || toStation.trim().equals("")) {
            errMap.put("toStation", "Không để trống ga đến");
        } else {

        }
        if (startDate == null || startDate.trim().equals("")) {
            errMap.put("startDate", "Không để trống ngày đi");
        } else {
            Date now = new Date();
            try {
                Date date = ChangeTime.formatDateFromString(startDate);
                if (date.before(now)) {
                    errMap.put("startDate", "Hãy chọn ngày lớn hơn hoặc bằng hiện tại");
                }
            } catch (ParseException e) {
                errMap.put("startDate", "Ngày đi không đúng định dạng");
            }
        }

        if (!errMap.isEmpty()) return ResponseEntity.badRequest().body(errMap);


        List<TrainResponse> trainList = trainService.getAllTrain(fromStation, toStation, startDate);
        if (trainList == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lỗi");
        if (trainList.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Hiện tại không có tàu nào trong danh sách.");
        }
        return ResponseEntity
                .ok(trainList);
    }

    @GetMapping("/get-all-coach-by-train-id/{trainId}")
    public ResponseEntity<?> getAllCoach(@PathVariable(required = false) Long trainId) {
        // validate

        List<ICoachDto> coachDtoList = coachService.getAllCoachByTrainId(trainId);
        if (coachDtoList == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lỗi");
        if (coachDtoList.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Hiện tại không có toa tàu nào trong danh sách.");
        }
        return ResponseEntity
                .ok(coachDtoList);
    }

    @GetMapping("/get-seat/{coachId}/{firstTripId}/{lastTripId}")
    public ResponseEntity<?> getAllSeatOffCoach(@PathVariable(required = false) Long coachId,
                                                @PathVariable(required = false) Long firstTripId,
                                                @PathVariable(required = false) Long lastTripId) {
        // validate

        List<ISeatDto> seatDtoList = seatService.getAllSeatByCoachId(coachId, firstTripId, lastTripId);
        if (seatDtoList == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lỗi");
        if (seatDtoList.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Hiện tại không có toa tàu nào trong danh sách.");
        }

        return ResponseEntity
                .ok(seatDtoList);
    }

    @GetMapping("/list-station")
    public ResponseEntity<?> getAllStation() {
        List<String> distanceList = Arrays.stream(TrainEnum.values())
                .map(value -> value.getStation())
                .collect(Collectors.toList());
        return ResponseEntity.ok(distanceList);
    }

    @GetMapping("/clear-temporary/{userName}")
    public ResponseEntity<?> clearTemporary(@PathVariable("userName") String userName) {
        boolean check = seatService.clearUserIdInSeatEntity(userName);
        if (!check) return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("clear failed");
        return ResponseEntity.ok("clear success");
    }

}
