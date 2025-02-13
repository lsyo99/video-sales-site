package org.ItBridge.db.mypage;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.ItBridge.db.BaseEntity;
import org.ItBridge.db.Lecture.LectureEntity;
import org.ItBridge.db.user.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name ="payment")
public class PaymentEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private LectureEntity lecture;

    @Column(name = "payed_date")
    private LocalDateTime payedDate;
    @Column(name = "payed_method", length = 40)
    private String payedMethod;
    @Column(name = "salse")
    private Integer salse;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal account;
    @Column(name = "first_price",nullable = false, precision = 10, scale = 2)
    private BigDecimal firstPrice;
    @Column(name = "merchant_uid", length = 255)
    private String merchantUid;
    @Column(name = "imp_uid", length = 255)
    private String impUid;
}
