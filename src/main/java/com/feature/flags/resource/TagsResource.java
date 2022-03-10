package com.feature.flags.resource;

import com.feature.flags.model.Tags;
import com.feature.flags.model.Users;
import com.feature.flags.service.TagsService;
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
@RequestMapping("/tag")
public class TagsResource {

    @Autowired
    TagsService tagsService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(String id) {
        Tags tag = new Tags(id);
        tagsService.insertTag(tag);
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tags>> getAllUsers() {
        return ResponseEntity.ok(tagsService.getAll());
    }

}
