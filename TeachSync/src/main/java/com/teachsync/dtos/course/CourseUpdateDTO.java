package com.teachsync.dtos.course;

import com.teachsync.utils.enums.Status;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseUpdateDTO implements Serializable {
    @NotNull
    @Positive
    private Long id;

    @NotBlank
    @Size(min = 1, max = 45)
    private String courseName;

    @Lob
    private String courseDesc;

    private Status status = Status.UPDATED;
}
