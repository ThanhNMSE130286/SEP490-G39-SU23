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

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "classroom")
public class Classroom {
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courseId", referencedColumnName = "id", nullable = false)
    private Course course;
    @Positive
    @Column(name = "courseId", insertable = false, updatable = false)
    private Long courseId;

    @NotBlank
    @Size(min = 1, max = 45)
    @Column(name = "className", nullable = false, length = 45)
    private String className;

    @Lob
    @Column(name = "classDesc")
    private String classDesc;

    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private List<Homework> homeworkList;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private List<Schedule> scheduleList;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private List<Test> testList;
}
