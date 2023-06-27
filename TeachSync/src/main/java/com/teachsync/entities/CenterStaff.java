package com.teachsync.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "center_staff", schema = "teachsync")
public class CenterStaff extends BaseEntity {
    @Column(name = "centerId", nullable = false)
    private Long centerId;
    
    @Column(name = "userId", nullable = false)
    private Long userId;
    
    @Column(name = "staffType", nullable = false, length = 45)
    private String staffType;
}