package com.feature.flags.service;

import com.feature.flags.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void insertUser() {
        List<String> roles = Arrays.asList("SE", "AE", "CEO", "PM", "SM", "CFO", "COO", "EXE");

        for (int i = 1; i < 100000; i++) {
            //id, email, role, roleId, org, domain
            userService.insertUser(new Users(i + "", ((char) (('a' + i % 26))) + "dsds@" + ((char) (('a' + ((i / 10) % 26)))) + "dom.com",
                    roles.get(i % 8), i + "r", i / 10 + "", ((char) (('a' + ((i / 10) % 26)))) + "dom.com"));
        }
    }
}