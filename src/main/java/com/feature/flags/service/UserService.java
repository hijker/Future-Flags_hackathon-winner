package com.feature.flags.service;

import com.feature.flags.dao.service.UserDaoService;
import com.feature.flags.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDaoService userDaoService;

    public void insertUser(User user) {
        userDaoService.insertUser(user);
    }

    public List<User> getAll() {
        return userDaoService.getByAll();
    }

    public User getById(String id) {
        return userDaoService.getById(id);
    }

    public User getByRole(String role) {
        return userDaoService.getByRole(role);
    }

    public User getByOrg(String org) {
        return userDaoService.getByOrg(org);
    }
}
