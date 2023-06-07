package com.teachsync.dtos.role;

import com.teachsync.utils.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleReadDTO implements Serializable {
    private Long id;

    private String roleName;

    private String roleDesc;

    private Status status;
}
