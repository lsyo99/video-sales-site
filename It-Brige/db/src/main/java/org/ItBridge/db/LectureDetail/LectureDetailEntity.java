package org.ItBridge.db.LectureDetail;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.ItBridge.db.BaseEntity;
import org.ItBridge.db.Lecture.LectureEntity;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Table(name ="lecture_detail")
public class LectureDetailEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private LectureEntity lecture; // 외래 키로 LectureEntity를 참조

    @Column(name = "url", nullable = false, length = 200)
    private String url;

    @Column(name = "sort_img", nullable = false)
    private int sortImg;

    private LocalDateTime updated_at;
    private LocalDateTime created_at;

}
