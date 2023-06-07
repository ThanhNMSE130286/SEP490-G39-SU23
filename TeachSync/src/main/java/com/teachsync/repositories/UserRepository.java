package com.teachsync.repositories;

import com.teachsync.entities.User;
import com.teachsync.utils.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndPasswordAndStatusNot(String username, String pass, Status status);

    /* Check duplicate */
    boolean existsByUsernameAndStatusNot(String username, Status status);

    List<User> findAllByRoleId(Long roleId);

    List<User> findAllByUsernameContaining(String username);
}