package com.teachsync.dtos.course;

import com.teachsync.utils.enums.Status;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseCreateDTO implements Serializable {
    @NotBlank
    @Size(min = 1, max = 45)
    private String courseName;

    @Lob
    private String courseDesc;

    private Status status = Status.CREATED;

    @NotNull
    @Min(1)
    @Positive
    private Double price;
}
