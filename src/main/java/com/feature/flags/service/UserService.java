package com.feature.flags.service;

import com.feature.flags.dao.service.UserDaoService;
import com.feature.flags.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDaoService userDaoService;

    public void insertUser(Users users) {
        userDaoService.insertUser(users);
    }

    public List<Users> getAll() {
        return userDaoService.getByAll();
    }

    public Users getById(String id) {
        return userDaoService.getById(id);
    }

    public Users getByRole(String role) {
        return userDaoService.getByRole(role);
    }

    public Users getByOrg(String org) {
        return userDaoService.getByOrg(org);
    }
}
