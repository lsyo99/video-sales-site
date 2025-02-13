package org.ItBridge.db.Post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.ItBridge.db.BaseEntity;
import org.ItBridge.db.Comment.CommentEntity;
import org.ItBridge.db.borad.BoardEntity;
import org.ItBridge.db.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name ="post")
public class PostEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    private String title;
    @Column(length = 5000)
    private String body;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> comments;
}
