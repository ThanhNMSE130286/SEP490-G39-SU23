package com.teachsync.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class City extends BaseEntity {
    @Column(name = "provinceId", nullable = false)
    private Long provinceId;
    
    @Column(name = "cityName", nullable = false, length = 255)
    private String cityName;
    
    @Column(name = "cityAlias", nullable = false, length = 45)
    private String cityAlias;
}