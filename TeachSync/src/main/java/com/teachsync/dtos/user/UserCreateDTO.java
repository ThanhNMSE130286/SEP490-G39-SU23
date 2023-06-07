package com.teachsync.dtos.user;

import com.teachsync.utils.enums.Status;
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
public class UserCreateDTO implements Serializable {
    @Positive
    private Long roleId;

    @Positive
    private Long parentId;

    @NotBlank
    @Size(min = 4, max = 45)
    private String username;

    @NotBlank
    @Size(min = 1, max = 255)
    private String password;

    @NotBlank
    @Size(min = 1, max = 255)
    private String fullName;

    @Email
    @Size(min = 5, max = 255)
    private String email;

    @Pattern(regexp = "^\\\\d{10}$")
    @Size(min = 10, max = 10)
    private String phone;

    @Size(min = 5, max = 255)
    private String address;

    private Status status = Status.CREATED;

    @Email
    @Size(min = 5, max = 255)
    private String parentEmail;


    /** For Student signup */
    public UserCreateDTO(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.roleId = 1L;
    }
}
