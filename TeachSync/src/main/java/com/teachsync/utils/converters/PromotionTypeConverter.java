package com.teachsync.utils.converters;

import com.teachsync.utils.enums.PromotionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PromotionTypeConverter implements AttributeConverter<PromotionType, String> {
    @Override
    public String convertToDatabaseColumn(PromotionType promotionType) {
        if (promotionType == null) {
            return null; }

        return promotionType.getStringValue();
    }

    @Override
    public PromotionType convertToEntityAttribute(String promotionType) {
         if (promotionType == null) {
             return null; }

        return Stream.of(PromotionType.values())
                .filter(s -> s.getStringValue().equals(promotionType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new); /* Should only happen if Enum was deleted */
        /* TODO: exception handler for deleted enum */
    }
}