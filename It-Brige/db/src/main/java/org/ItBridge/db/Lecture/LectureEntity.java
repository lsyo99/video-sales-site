package org.ItBridge.db.Lecture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.ItBridge.db.BaseEntity;
import org.springframework.data.annotation.TypeAlias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name ="lecture")
public class LectureEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column
    private Long sales;

    @Column(name = "like", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer likes;

    @Column(name = "thumbnail_url", nullable = false, length = 1024)
    private String thumbnailUrl;

    @Column(columnDefinition = "JSON")
    private String tags;

    @Column(name = "upload_at")
    private LocalDateTime uploadAt;
    //TODO 결제하고 payment해서 팔린 수만큼 할 것이기 때문에 나중에 삭제
    @Column
    private Integer rank;

}
