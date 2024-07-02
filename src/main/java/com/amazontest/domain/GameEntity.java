package com.amazontest.domain;


import com.amazontest.type.TeamName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jsoup.nodes.Element;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "game")
@NoArgsConstructor

@ToString
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GameEntity {
    @Id
    @GeneratedValue
    private long id;

    private LocalDateTime gameTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TeamName homeTeam;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TeamName awayTeam;

    @NotBlank
    private String location;

    @LastModifiedDate
    private LocalDateTime updatedDate;
    @CreatedDate
    private LocalDateTime createdDate;

    public static GameEntity fromCrawling(LocalDateTime gameTime, Element team1,
                                          Element team2, Element location){

        return GameEntity.builder()
                .gameTime(gameTime)
                .homeTeam(TeamName.valueOf(team1.text().toUpperCase()))
                .awayTeam(TeamName.valueOf(team2.text().toUpperCase()))
                .location(location.text())
                .build();
    }
}
