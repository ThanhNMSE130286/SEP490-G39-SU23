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
@Table(name = "course")
public class Course extends BaseEntity {
    @Column(name = "courseAlias", nullable = false, length = 10)
    private String courseAlias;

    @Column(name = "courseName", nullable = false, length = 45)
    private String courseName;

    @Lob
    @Column(name = "courseImg", nullable = false, length = -1)
    private String courseImg;

    @Column(name = "courseDesc", nullable = true, length = -1)
    private String courseDesc;

    @Column(name = "numSession", nullable = false)
    private Integer numSession;

    @Column(name = "minScore", nullable = false, precision = 0)
    private Double minScore;

    @Column(name = "minAttendant", nullable = false, precision = 0)
    private Double minAttendant;
}