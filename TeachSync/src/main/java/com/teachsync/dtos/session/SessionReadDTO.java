package com.teachsync.dtos.session;
import com.teachsync.dtos.BaseReadDTO;
import com.teachsync.dtos.clazzSchedule.ClazzScheduleReadDTO;
import com.teachsync.dtos.room.RoomReadDTO;
import com.teachsync.dtos.staff.StaffReadDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.teachsync.entities.Session}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionReadDTO extends BaseReadDTO {
    private Long scheduleId;
    private ClazzScheduleReadDTO schedule;

    private Long roomId;
    private String roomName;
    private RoomReadDTO room;

    private Long staffId;
    private StaffReadDTO staff;

    private Integer slot;
    private LocalDateTime sessionStart;
    private LocalDateTime sessionEnd;
}