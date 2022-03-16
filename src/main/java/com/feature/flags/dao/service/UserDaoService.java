package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.UserRepository;
import com.feature.flags.model.Users;
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

    public void insertUser(Users users) {
        userRepository.save(users);
    }

    public List<Users> getByAll() {
        return userRepository.findAll();
    }

    public Users getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public Users getByRole(String role) {
        return userRepository.findByRole(role).get(0);
    }

    public Users getByOrg(String org) {
        return userRepository.findByOrg(org).get(0);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
