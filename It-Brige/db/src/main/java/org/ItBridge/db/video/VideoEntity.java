package org.ItBridge.db.video;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.ItBridge.db.BaseEntity;
import org.ItBridge.db.Lecture.LectureEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "video")
public class VideoEntity extends BaseEntity {

    @Column(name = "url", nullable = false, length = 1024)
    private String url;

    @JoinColumn(name="lecture_id", nullable = false)
    @ManyToOne(fetch=FetchType.LAZY)
    private LectureEntity lecture;

    @Column(name = "sort_id", nullable = false)
    private Integer sortId;
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    @Column(name = "duration", length = 255, nullable = false)
    private String duration;
}
