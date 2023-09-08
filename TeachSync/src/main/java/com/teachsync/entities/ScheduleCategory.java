package com.teachsync.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "schedulecat")
public class ScheduleCategory extends BaseEntity{


    @Column(name = "name", nullable = true, length = 45)
    private String scheduleName;

    @Lob
    @Column(name = "description", nullable = true, length = -1)
    private String scheduleDesc;
}
