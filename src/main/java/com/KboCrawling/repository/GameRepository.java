package com.KboCrawling.repository;

import com.KboCrawling.domain.GameEntity;
import com.KboCrawling.type.TeamName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
//    List<GameEntity> findbyGa(TeamName homeTeam,TeamName awayTeam);
}
