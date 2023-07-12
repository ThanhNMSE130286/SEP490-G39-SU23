package com.teachsync.dtos.clazz;

import com.teachsync.dtos.BaseCreateDTO;
import com.teachsync.utils.enums.ScheduleType;
import com.teachsync.utils.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.teachsync.entities.Clazz}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzCreateDTO extends BaseCreateDTO {
    private Long staffId;

    private Long courseId;
    private Long semesterId;
    private Long centerId;

    private String clazzName;

    private String clazzDesc;

    private Integer clazzSize;

    private ScheduleType scheduleType;

    private Integer slot;
}