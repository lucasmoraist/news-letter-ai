package com.lucasmoraist.news_letter_ai.infrastructure.web.controller;

import com.lucasmoraist.news_letter_ai.infrastructure.schedule.DailySchedule;
import com.lucasmoraist.news_letter_ai.infrastructure.web.routes.NoticesRoutes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController implements NoticesRoutes {

    private final DailySchedule dailySchedule;

    public NoticesController(DailySchedule dailySchedule) {
        this.dailySchedule = dailySchedule;
    }

    @Override
    public ResponseEntity<Void> sendNotices() {
        this.dailySchedule.execute();
        return ResponseEntity.ok().build();
    }

}
