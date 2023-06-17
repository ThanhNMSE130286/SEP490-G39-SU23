package com.teachsync.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classId", referencedColumnName = "id", nullable = false)
    private Clazz clazz;
    @Positive
    @Column(name = "classId", insertable = false, updatable = false)
    private Long classId;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Range(min = 0, max = 12)
    @PositiveOrZero
    @Column(name = "slot", nullable = false)
    private Integer slot;

    @Lob
    @Column(name = "scheduleDesc")
    private String scheduleDesc;
}
