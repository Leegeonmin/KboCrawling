package com.watchbaseballlive;

import com.watchbaseballlive.service.KboCrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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


    @GetMapping("/msg")
    public ResponseEntity<String> test(){
        return  ResponseEntity.ok().body("테스트");
    }



    @GetMapping("/file")
    public ResponseEntity<Resource[]> taest() throws IOException {
        Resource[] resources = ResourcePatternUtils
                .getResourcePatternResolver(new DefaultResourceLoader())
                .getResources("classpath*:chromedriver");
        return  ResponseEntity.ok().body(resources);
    }
}
