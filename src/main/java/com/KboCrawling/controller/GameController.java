package com.KboCrawling.controller;

import com.KboCrawling.dto.response.GameListByTeamNameDto;
import com.KboCrawling.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game")

public class GameController {

    private final GameService gameService;

//    @GetMapping
//    public ResponseEntity<List<GameListByTeamNameDto>> getGamesByTeamName(@RequestParam("teamName") String teamName) {
//        List<GameListByTeamNameDto> games = gameService.getGamesByTeamName(teamName);
//
//        return new ResponseEntity<>(games, HttpStatus.OK);
//    }
}
