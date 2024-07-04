package com.KboCrawling.controller;

import com.KboCrawling.service.KboCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("crawling")
@RequiredArgsConstructor
public class CrawlingController {
    private final KboCrawlingService kboCrawlingService;

    @GetMapping("gameschedule")
    public ResponseEntity<String> updateSchedule() {
        kboCrawlingService.saveGames();
        return ResponseEntity.ok().body("update game schedule");
    }

}
