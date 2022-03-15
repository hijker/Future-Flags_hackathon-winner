package com.feature.flags.resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class UserResourceTest {

    @Autowired
    UserResource userResource;

    @Test
    void createUser() {
        List<String> roles = Arrays.asList("SE", "AE", "CEO", "PM", "SM", "CFO", "COO", "EXE");

        for (int i = 1; i < 30; i++) {
            //id, email, role, roleId, org, domain
            userResource.createUser(i + "", ((char) (('a' + i % 26))) + "dsds@" + ((char) (('a' + ((i / 10) % 26)))) + "dom.com",
                    roles.get(i % 8), i + "r", i / 10 + "", ((char) (('a' + ((i / 10) % 26)))) + "dom.com",
                    ((char) (('a' + i % 26))) + "dsds");
        }

    }
}