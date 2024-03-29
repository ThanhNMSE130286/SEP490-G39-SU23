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
@Table(name = "clazz")
public class Clazz extends BaseEntity {
//    @Column(name = "courseSemesterId", nullable = false)
//    private Long courseSemesterId;
    @Column(name = "courseId", nullable = false)
    private Long courseId;

    @Column(name = "centerId", nullable = false)
    private Long centerId;

    @Column(name = "staffId", nullable = false)
    private Long staffId;

    @Column(name = "clazzAlias", nullable = false, length = 10)
    private String clazzAlias;

    @Column(name = "clazzName", nullable = false, length = 45)
    private String clazzName;

    @Lob
    @Column(name = "clazzDesc", nullable = true, length = -1)
    private String clazzDesc;

//    @Column(name = "statusClazz", nullable = true, length = -1)
//    private String statusClazz;

//    @Column(name = "clazzSize", nullable = false)
//    private Integer clazzSize;

    /** Số học sinh tối thiểu mở lớp */
    @Column(name = "minCapacity", nullable = false)
    private Integer minCapacity;

    /** Số học sinh tối đa */
    @Column(name = "maxCapacity", nullable = false)
    private Integer maxCapacity;
}