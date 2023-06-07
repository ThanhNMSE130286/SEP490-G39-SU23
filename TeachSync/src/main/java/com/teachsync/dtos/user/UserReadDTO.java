package com.teachsync.dtos.user;

import com.teachsync.entities.Request;
import com.teachsync.entities.User;
import com.teachsync.utils.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadDTO implements Serializable {
    private Long id;

    private Long roleId;

    private Long parentId;

    private String username;

    /* NEVER SHOW PASSWORD */

    private String fullName;

    private String email;

    private String phone;

    private String address;

    private Status status;

    private List<Request> requestMadeList;

    private List<User> childList;
}
