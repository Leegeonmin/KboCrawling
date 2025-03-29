package com.KboCrawling.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity(name = "weather")
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "날씨ID")
    private Long id;

    @Column(name = "기상날짜", nullable = false)
    private LocalDate date;

    @Column(name = "날씨 설명", nullable = false)
    private String description;

    @Column(name = "최저 기온", nullable = false)
    private double minTemperature;

    @Column(name = "최고 기온", nullable = false)
    private double maxTemperature;

    @Column(name = "강수 확률 (%)", nullable = false)
    private int precipitationProbability;

    @ManyToOne
    @JoinColumn(name = "스타디움id", nullable = false)
    private StadiumEntity stadium;

}
