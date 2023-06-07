package com.teachsync.dtos.user;

import com.teachsync.utils.enums.Status;
import jakarta.validation.constraints.*;
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
public class UserUpdateDTO implements Serializable {
    @NotNull
    @Positive
    private Long id;

    @NotNull
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

    private Status status = Status.UPDATED;

    @NotNull
    @Size(min = 1)
    private List<Long> childList;
}
