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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "request")
public class Request {
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requesterId", referencedColumnName = "id", nullable = false)
    private User requester;
    @Positive
    @Column(name = "requesterId", insertable = false, updatable = false)
    private Long requesterId;

    @NotBlank
    @Size(min = 1, max = 45)
    @Column(name = "requestName", nullable = false, length = 45)
    private String requestName;

    @Column(name = "requestType", length = 45)
    private String requestType;

    @Lob
    @Column(name = "requestDesc")
    private String requestDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolverId", referencedColumnName = "id")
    private User resolver;
    @Positive
    @Column(name = "resolverId", insertable = false, updatable = false)
    private Long resolverId;

    @Column(name = "status")
    private Status status;
}
