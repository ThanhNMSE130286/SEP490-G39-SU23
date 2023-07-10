package com.teachsync.dtos.clazzMember;

import com.teachsync.dtos.BaseReadDTO;
import com.teachsync.utils.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.teachsync.entities.ClazzMember}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzMemberReadDTO extends BaseReadDTO {
    private Long clazzId;
    private Long userId;
    private MemberRole memberRole;
    private Double score;
    private Double attendant;
    private Boolean isPassed;
}