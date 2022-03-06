package com.feature.flags.resource;

import com.feature.flags.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/base")
public class BasicResource {

    @Autowired
    RedisService redisService;

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> hello(final HttpServletRequest request) {
        return ResponseEntity.ok().body("{ \"message\" : \"Hello World\"}");
    }

    @PostMapping(value = "/redis_add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addToRedis(final HttpServletRequest request,
                                             String key,
                                             String value) {
        redisService.setValue(key, value);
        return ResponseEntity.ok().body("{ \"message\" : \"Success\"}");
    }

    @GetMapping(value = "/redis_add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFromRedis(final HttpServletRequest request,
                                               String key) {
        final String stringValue = redisService.getValue(key).toString();
        return ResponseEntity.ok().body("{ \"value\" : \"" + stringValue + "\"}");
    }
}
