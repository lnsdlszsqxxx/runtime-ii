package com.ascending.training.service;

import com.ascending.training.model.Role;
import com.ascending.training.repository.RoleDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDaoImpl roleDao;

    public Role getRoleByName(String name){
        return roleDao.getRoleByName(name);
    }

}
