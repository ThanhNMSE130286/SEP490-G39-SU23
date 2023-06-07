package com.teachsync.dtos.priceLog;

import com.teachsync.utils.enums.PromotionType;
import com.teachsync.utils.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PriceLogReadDTO {
    private Long id;

    private Long courseId;

    private Double price;

    private Boolean isCurrent;

    private Boolean isPromotion;

    private PromotionType promotionType;

    private Double promotionAmount;

    private String promotionDesc;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    private Status status;

    private Double finalPrice;
}
