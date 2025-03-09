package com.KboCrawling.service;

import com.KboCrawling.domain.GameEntity;
import com.KboCrawling.dto.response.GameListByTeamNameDto;
import com.KboCrawling.repository.GameRepository;
import com.KboCrawling.type.TeamName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class GameService {
    private final GameRepository gameRepository;

    public List<GameListByTeamNameDto> getGamesByTeamName(String teamName) {
        TeamName team = TeamName.fromKoreanName(teamName);
        List<GameEntity> games = gameRepository.
                findByHomeTeamOrAwayTeam(team, team);

        return games.stream().map(GameListByTeamNameDto::new).collect(Collectors.toList());
    }
}
