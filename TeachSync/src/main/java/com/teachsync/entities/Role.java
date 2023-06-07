package com.teachsync.entities;

import com.teachsync.utils.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 45)
    @Column(name = "roleName", length = 45)
    private String roleName;

    @Lob
    @Column(name = "roleDesc")
    private String roleDesc;

    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> userList;
}
