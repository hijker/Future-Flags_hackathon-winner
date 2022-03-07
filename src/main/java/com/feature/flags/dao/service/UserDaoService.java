package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.UserRepository;
import com.feature.flags.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDaoService {

    @Autowired
    UserRepository userRepository;

    public UserDaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insertUser(User user) {
        userRepository.save(user);
    }

    public List<User> getByAll() {
        return userRepository.findAll();
    }

    public User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getByRole(String role) {
        return userRepository.findByRole(role).orElse(null);
    }
    public User getByOrg(String org) {
        return userRepository.findByOrg(org).orElse(null);
    }

}
