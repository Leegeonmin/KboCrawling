package com.KboCrawling.dto.response;

import com.KboCrawling.domain.GameEntity;

import java.time.LocalDateTime;

public record GameListByTeamNameDto(
        String homeTeam,
        String awayTeam,
        LocalDateTime gameTime,
        String location
) {
    public GameListByTeamNameDto (GameEntity game){
        this(game.getHomeTeam().getKoreanName(), game.getAwayTeam().getKoreanName(), game.getGameTime(), game.getLocation());
    }
}
