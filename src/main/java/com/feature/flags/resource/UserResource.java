package com.feature.flags.resource;

import com.feature.flags.model.SearchKeywords;
import com.feature.flags.model.Users;
import com.feature.flags.service.SearchService;
import com.feature.flags.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.feature.flags.model.SearchObjects.ORG;
import static com.feature.flags.model.SearchObjects.ROLE;
import static com.feature.flags.model.SearchObjects.USER;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    UserService userService;

    @Autowired
    SearchService searchService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(String id,
                                             String email,
                                             String role,
                                             String roleId,
                                             String org,
                                             String domain) {
        id = id.trim();
        email = email.trim();
        role = role.trim();
        roleId = roleId.trim();
        org = org.trim();
        domain = domain.trim();
        Users users = new Users(id, email, role, roleId, org, domain);
        userService.insertUser(users);
        searchService.insertSearchKeyword(new SearchKeywords(id, USER.name(), id));
        searchService.insertSearchKeyword(new SearchKeywords(email, USER.name(), id));
        searchService.insertSearchKeyword(new SearchKeywords(org + " " + role, ROLE.name(), role));
        searchService.insertSearchKeyword(new SearchKeywords(domain + " " + role, ROLE.name(), role));
        searchService.insertSearchKeyword(new SearchKeywords(org + " " + roleId, ROLE.name(), role));
        searchService.insertSearchKeyword(new SearchKeywords(domain + " " + roleId, ROLE.name(), role));
        searchService.insertSearchKeyword(new SearchKeywords(org, ORG.name(), org));
        searchService.insertSearchKeyword(new SearchKeywords(domain, ORG.name(), org));
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

}
