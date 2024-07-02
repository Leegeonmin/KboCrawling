package com.watchbaseballlive;

import com.watchbaseballlive.service.KboCrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Slf4j
public class TestController {
    private final KboCrawlingService kboCrawlingService;

    @GetMapping
    public ResponseEntity<String> updateKboGames(){
        kboCrawlingService.saveGames();
        return  ResponseEntity.ok().body("kbo 경기 일정 업로드 성공");
    }
}
