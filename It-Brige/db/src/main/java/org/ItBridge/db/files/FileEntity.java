package org.ItBridge.db.files;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.ItBridge.db.BaseEntity;
import org.ItBridge.db.Post.PostEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name ="files")
public class FileEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @JoinColumn(name = "post_id", nullable = false) // 외래 키 컬럼 이름과 설정
    private PostEntity post;
    @Column(name = "file_name", nullable = false) // 파일 이름
    private String file_name;
    @Column(name = "file_url", nullable = false) // 파일 URL
    private String file_url;
    @Column(name = "created_at", nullable = false, updatable = false) // 생성 시간
    private LocalDateTime created_at;
}
