package com.teachsync.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "schedule_user_pair")
public class ScheduleUserPair extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "scheduleId", referencedColumnName = "id", nullable = false)
    private Schedule schedule;
    @Positive
    @Column(name = "scheduleId", insertable = false, updatable = false)
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private User user;
    @Positive
    @Column(name = "userId", insertable = false, updatable = false)
    private Long userId;
}
