package com.KboCrawling.repository;

import com.KboCrawling.domain.GameTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTeamRepository  extends JpaRepository<GameTeam, Long> {
}
