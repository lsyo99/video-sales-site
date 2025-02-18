package org.ItBridge.db.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.ItBridge.db.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Table(name = "authority")
public class AuthorityEntity extends BaseEntity {

    @Column(length = 20,nullable = false )
    private String name;
    @Column(nullable = false)
    private int level;
    @PrePersist
    public void setDefaults() {
        if (this.level == 0) { // 기본값이 0일 경우 설정
            this.level = 2;
        }
    }
}
