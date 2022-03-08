package com.feature.flags.resource;

import com.feature.flags.model.Users;
import com.feature.flags.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(String id,
                                             String email,
                                             String role,
                                             String org,
                                             String domain) {
        Users users = new Users(id, email, role, org, domain);
        userService.insertUser(users);
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(value = "/suggestions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Users>> getSuggestions(String prefix) {
        if (Character.isDigit(prefix.charAt(0))) {
            //userId or orgId
        } else {
            //email or domain
        }
        return ResponseEntity.ok(userService.getAll());
    }

}
