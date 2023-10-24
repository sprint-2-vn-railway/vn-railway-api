package com.example.vn_railway.common;

import com.example.vn_railway.service.trip.ISeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class AutoRunsWhenStartingProject implements ApplicationListener<ApplicationReadyEvent> {
    private final ISeatService seatService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("clear all");
        seatService.clearAllUserIdInSeatEntity();
        log.info("clear success");
    }
}
