package com.teachsync.entities;

import com.teachsync.utils.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "homework")
public class Homework {
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classId", referencedColumnName = "id", nullable = false)
    private Classroom classroom;
    @Positive
    @Column(name = "classId", insertable = false, updatable = false)
    private Long classId;

    @NotBlank
    @Size(min = 1, max = 45)
    @Column(name = "homeworkName", nullable = false, length = 45)
    private String homeworkName;

    @Lob
    @Column(name = "homeworkDesc")
    private String homeworkDesc;

    @Column(name = "status")
    private Status status;
}
