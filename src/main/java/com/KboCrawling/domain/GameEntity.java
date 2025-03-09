package com.KboCrawling.domain;


import com.KboCrawling.type.TeamName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.jsoup.nodes.Element;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "game")
@NoArgsConstructor
@Getter
@ToString
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GameEntity {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private LocalDateTime gameTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TeamName homeTeam;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TeamName awayTeam;

    @NotBlank
    @Column(length = 20)
    private String location;

    @LastModifiedDate
    private LocalDateTime updatedDate;
    @CreatedDate
    private LocalDateTime createdDate;

    public static GameEntity fromCrawling(LocalDateTime gameTime, Element awayteam,
                                          Element hometeam, Element location){
        TeamName homeTeam  = TeamName.fromKoreanName(hometeam.text());
        TeamName awayTeam = TeamName.fromKoreanName(awayteam.text());
        return GameEntity.builder()
                .gameTime(gameTime)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .location(location.text())
                .build();
    }

}
