package com.KboCrawling.repository;

import com.KboCrawling.domain.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    TeamEntity findByName(String name);
}
