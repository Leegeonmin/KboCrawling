package com.KboCrawling.dto.response;

import com.KboCrawling.domain.GameEntity;
import com.KboCrawling.domain.GameTeam;

import java.time.LocalDateTime;

public record GameListByTeamNameDto(
        String homeTeam,
        String awayTeam,
        LocalDateTime gameTime,
        String location
) {
    public GameListByTeamNameDto(GameEntity game) {
        this(
                extractHomeTeam(game),
                extractAwayTeam(game),
                game.getGameTime(),
                game.getLocation()
        );
    }

    private static String extractHomeTeam(GameEntity game) {
        return game.getGameTeams().stream()
                .filter(GameTeam::isHomeGame)
                .map(gt -> gt.getTeam().getName())
                .findFirst()
                .orElse("Unknown");
    }

    private static String extractAwayTeam(GameEntity game) {
        return game.getGameTeams().stream()
                .filter(gt -> !gt.isHomeGame())
                .map(gt -> gt.getTeam().getName())
                .findFirst()
                .orElse("Unknown");
    }
}
