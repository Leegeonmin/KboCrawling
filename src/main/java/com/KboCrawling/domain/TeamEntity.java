package com.KboCrawling.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "team")
@NoArgsConstructor
@Getter
@ToString
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private StadiumEntity stadium;

    @NotBlank
    @Column(length = 20)
    private String name;

    @OneToMany(mappedBy = "team")
    private List<GameTeam> gameTeams;

    @LastModifiedDate
    private LocalDateTime updatedDate;
    @CreatedDate
    private LocalDateTime createdDate;
}
