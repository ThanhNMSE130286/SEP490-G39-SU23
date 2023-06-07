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
@Table(name = "course")
public class Course {
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 45)
    @Column(name = "courseName", nullable = false, length = 45)
    private String courseName;

    @Lob
    @Column(name = "courseDesc")
    private String courseDesc;

    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Classroom> classroomList;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Material> materialList;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<PriceLog> priceLogList;
}
