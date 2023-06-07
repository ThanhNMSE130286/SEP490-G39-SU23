package com.teachsync.entities;

import com.teachsync.utils.enums.PromotionType;
import com.teachsync.utils.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "price_log")
public class PriceLog {
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courseId", referencedColumnName = "id", nullable = false)
    private Course course;
    @Positive
    @Column(name = "courseId", insertable = false, updatable = false)
    private Long courseId;

    @NotNull
    @Min(1)
    @Positive
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "isCurrent")
    private Boolean isCurrent = true;

    @Column(name = "isPromotion")
    private Boolean isPromotion = false;

    @Column(name = "promotionType", length = 45)
    private PromotionType promotionType;

    @Positive
    @Column(name = "promotionAmount")
    private Double promotionAmount;

    @Lob
    @Column(name = "promotionDesc")
    private String promotionDesc;

    @Column(name = "validFrom")
    private LocalDateTime validFrom;

    @Column(name = "validTo")
    private LocalDateTime validTo;

    @Column(name = "status")
    private Status status;
}
