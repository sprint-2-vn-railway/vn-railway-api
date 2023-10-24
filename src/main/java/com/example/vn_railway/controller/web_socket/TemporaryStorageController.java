package com.example.vn_railway.controller.web_socket;

import com.example.vn_railway.dto.train_dto.ISeatDto;
import com.example.vn_railway.dto.train_dto.ISeatResponse;
import com.example.vn_railway.dto.train_dto.SeatPayload;
import com.example.vn_railway.model.trip.Seat;
import com.example.vn_railway.service.trip.ISeatService;
import com.example.vn_railway.service.trip.impl.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@CrossOrigin("*")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TemporaryStorageController {

    private final ISeatService seatService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
    private final Map<SeatPayload, ScheduledFuture<?>> futureMap;

    @MessageMapping("/seat")
    @SendTo("/topic/seat")
    public String getSeat(@Payload SeatPayload seatPayload) {
        // >>> check validate

        List<ISeatResponse> seatResponseList = seatService.getAllSeatByUserName(seatPayload.getUserName());
        if (seatResponseList != null && seatResponseList.size() == 5) {
            boolean check = seatResponseList.stream().anyMatch(
                    (pre) -> seatPayload.getUserName().equals(pre.getUserName()) &&
                            seatPayload.getSeatCode().equals(pre.getSeatCode()) &&
                            seatPayload.getLastTripId().equals(pre.getLastTripId()) &&
                            seatPayload.getFirstTripId().equals(pre.getFirstTripId()) &&
                            seatPayload.getTrainId().equals(pre.getTrainId())
            );
            if (!check) return "limited";
        }
        ISeatDto seatDto = seatService.getSeatByCoachIdAndCode(
                seatPayload.getCoachId(),
                seatPayload.getFirstTripId(),
                seatPayload.getLastTripId(),
                seatPayload.getSeatCode()
        );
        if (seatDto == null) return "null";
        boolean checkDuplicate =
                seatDto.getUserName() != null && !seatPayload.getUserName().equals(seatDto.getUserName());

        if (checkDuplicate) return "duplicate";
        // handle change selected status seat

        if (futureMap.containsKey(seatPayload)) {
            futureMap.get(seatPayload).cancel(true);
            futureMap.remove(seatPayload);
        }

        boolean check = seatService.updateSelectedSeat(seatPayload, false);
        if (!check) return "failed";

        ScheduledFuture<?> future = executor.schedule(() -> {
            seatService.updateSelectedSeat(seatPayload, true);
            simpMessagingTemplate.convertAndSend("/topic/seat/", "clear");
            futureMap.remove(seatPayload);
        }, 5, TimeUnit.MINUTES);  // 5 minutes

        return "ok";
    }

}
