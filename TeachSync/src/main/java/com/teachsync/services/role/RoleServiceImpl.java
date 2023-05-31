package com.teachsync.services.role;

import com.teachsync.entities.Role;
import com.teachsync.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getById(Long id) throws Exception {
        Optional<Role> role = roleRepository.findByIdAndStatusNot(id, "DELETED");

        return role.orElse(null);
    }

}
