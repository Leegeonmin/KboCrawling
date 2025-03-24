package com.KboCrawling.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "stadium")
@NoArgsConstructor
@Getter
@ToString
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class StadiumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "VARCHAR(100) comment '구장 이름'", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(20) comment '중기육상코드'", nullable = false)
    private String mediumPrecipitation;

    @Column(columnDefinition = "VARCHAR(20) comment '중기기온코드'", nullable = false)
    private String mediumTemperature;
}
