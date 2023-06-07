package com.teachsync.utils.converters;

import com.teachsync.utils.enums.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null; }

        return status.getStringValue();
    }

    @Override
    public Status convertToEntityAttribute(String status) {
         if (status == null) {
             return null; }

        return Stream.of(Status.values())
                .filter(s -> s.getStringValue().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new); /* Should only happen if Enum was deleted */
        /* TODO: exception handler for deleted enum */
    }
}