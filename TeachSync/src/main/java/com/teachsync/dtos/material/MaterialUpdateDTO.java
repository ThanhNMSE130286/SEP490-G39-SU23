package com.teachsync.dtos.material;

import com.teachsync.dtos.BaseUpdateDTO;
import com.teachsync.utils.enums.Status;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialUpdateDTO extends BaseUpdateDTO {
    @NotNull
    @Positive
    private Long courseId;

    @NotBlank
    @Size(min = 1, max = 45)
    private String materialName;

    @Lob
    private String materialLink;

    private byte[] materialContent;

    @Lob
    private String materialImg;

    private String materialType;

    private Status status = Status.UPDATED;
}
