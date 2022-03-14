package com.feature.flags.resource;

import com.feature.flags.service.FeatureFlagService;
import com.feature.flags.service.FeatureFlagStatusService;
import com.feature.flags.service.FeaturesService;
import com.feature.flags.service.ImpactedFeatureService;
import com.feature.flags.service.ImpactedModuleService;
import com.feature.flags.service.ModulesService;
import com.feature.flags.service.RedisService;
import com.feature.flags.service.SearchService;
import com.feature.flags.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    
    @Autowired
    FeaturesService featuresService;

    @Autowired
    ModulesService modulesService;

    @Autowired
    FeatureFlagService featureFlagService;

    @Autowired
    FeatureFlagStatusService featureFlagStatusService;

    @Autowired
    UserService userService;

    @Autowired
    ImpactedFeatureService impactedFeatureService;

    @Autowired
    ImpactedModuleService impactedModuleService;

    @Autowired
    SearchService searchService;

    @Transactional
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete() {
        redisService.deleteAllKey();
        featuresService.deleteAll();
        modulesService.deleteAll();
        featureFlagStatusService.deleteAll();
        featureFlagService.deleteAll();
        userService.deleteAll();
        impactedFeatureService.deleteAll();
        impactedModuleService.deleteAll();
        searchService.deleteAll();

        return ResponseEntity.ok().body("{ \"message\" : \"Hello new World\"}");
    }

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
