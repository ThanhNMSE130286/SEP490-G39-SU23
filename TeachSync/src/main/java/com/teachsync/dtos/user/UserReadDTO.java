package com.teachsync.dtos.user;

import com.teachsync.dtos.BaseReadDTO;
import com.teachsync.dtos.address.AddressReadDTO;
import com.teachsync.dtos.role.RoleReadDTO;
import com.teachsync.entities.Request;
import com.teachsync.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for {@link User}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDTO extends BaseReadDTO {
    private Long roleId;
    private RoleReadDTO role;

    private String userAvatar;

    private Long parentId;

    private String username;

    /* NEVER SHOW PASSWORD */

    private String fullName;

    private String email;

    private String phone;

    private AddressReadDTO address;

    private List<Request> requestMadeList;

    private List<User> childList;
}
