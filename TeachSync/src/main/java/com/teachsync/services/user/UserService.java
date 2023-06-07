package com.teachsync.services.user;

import com.teachsync.dtos.user.UserCreateDTO;
import com.teachsync.dtos.user.UserReadDTO;
import com.teachsync.entities.User;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

import java.util.List;

public interface UserService {
    /* =================================================== CREATE =================================================== */
    User createUser(User user) throws Exception;
    UserReadDTO signupDTO(UserCreateDTO dto) throws Exception;



    /* =================================================== READ ===================================================== */
    User login(String username, String password) throws Exception;
    UserReadDTO loginDTO(String username, String password) throws Exception;



    /* =================================================== UPDATE =================================================== */



    /* =================================================== DELETE =================================================== */

    List<User> getListUserByType(Long type);
    List<User> getListUserByUserName(String username);



    /* =================================================== WRAPPER ================================================== */
    UserReadDTO wrapDTO(User user) throws Exception;

    List<UserReadDTO> wrapListDTO(Collection<User> userCollection) throws Exception;

    Page<UserReadDTO> wrapPageDTO(Page<User> userPage) throws Exception;
}