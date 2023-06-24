package com.teachsync.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "test_question", schema = "teachsync")
public class TestQuestion extends BaseEntity {
    @Column(name = "testId", nullable = false)
    private Long testId;
    
    @Column(name = "questionId", nullable = false)
    private Long questionId;
    
    @Column(name = "score", nullable = false, precision = 0)
    private Double score;
}