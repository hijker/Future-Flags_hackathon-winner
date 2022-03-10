package com.feature.flags.resource;

import com.feature.flags.model.FeatureFlag;
import com.feature.flags.model.FeatureFlagLevel;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.FeatureFlagStatusResponse;
import com.feature.flags.model.Users;
import com.feature.flags.service.FeatureFlagService;
import com.feature.flags.service.FeatureFlagStatusService;
import com.feature.flags.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ffs")
public class FeatureFlagStatusResource {

    @Autowired
    FeatureFlagService featureFlagService;

    @Autowired
    FeatureFlagStatusService featureFlagStatusService;

    @Autowired
    UserService userService;

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateFeatureFlagStatus(String name,
                                                          Boolean value,
                                                          FeatureFlagLevel level,
                                                          String levelValue,
                                                          String updatedById) {
        if (level == null) {
            return ResponseEntity.badRequest().body("No level provided");
        }
        final FeatureFlag existing = featureFlagService.getFeatureFlag(name);
        if (existing == null) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Feature flag name does not exist\" }");
        }
        FeatureFlagStatus featureFlagStatus = new FeatureFlagStatus(existing, value, level, levelValue, updatedById, new Date());
        featureFlagStatusService.insertFeatureFlagStatus(featureFlagStatus);
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/get_all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<FeatureFlagStatusResponse>>> getAllFeatureFlags() {
        return ResponseEntity.ok(getGroupedResponse(featureFlagStatusService.getAllFeatureFlagStatuses()));
    }

    @GetMapping(value = "/get_all_global", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<FeatureFlagStatusResponse>>> getAllGlobalFeatureFlags() {
        return ResponseEntity.ok(getGroupedResponse(featureFlagStatusService
                .getAllFeatureFlagStatusByLevelAndLevelValue(FeatureFlagLevel.SYSTEM, "SYSTEM")));
    }

    @GetMapping(value = "/get_all_specific", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<FeatureFlagStatusResponse>>> getAllSpecificFeatureFlags(FeatureFlagLevel level,
                                                                                                   String levelValue) {
        if (level == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(getGroupedResponse(featureFlagStatusService
                .getAllFeatureFlagStatusByLevelAndLevelValue(level, levelValue)));
    }

    @GetMapping(value = "/get_all_fallback", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<FeatureFlagStatusResponse>>> getAllFallbackFeatureFlags(FeatureFlagLevel level,
                                                                                                   String levelValue) {
        if (level == null) {
            return ResponseEntity.badRequest().build();
        }
        Map<String, List<FeatureFlagStatusResponse>> response = new HashMap<>();
        Set<String> setFlags = new HashSet<>();

        final FeatureFlagLevel[] values = FeatureFlagLevel.values();
        Arrays.sort(values, Comparator.comparingInt(Enum::ordinal));

        for (FeatureFlagLevel l : values) {
            if (l.ordinal() >= level.ordinal()) {
                for (FeatureFlagStatusResponse r : featureFlagStatusService
                        .getAllFeatureFlagStatusByLevelAndLevelValue(l, getLevelValueForLevelFromLevel(level, levelValue, l))) {
                    if (!setFlags.contains(r.getName())) {
                        final List<FeatureFlagStatusResponse> existing = response.getOrDefault(r.getOwner_module(), new ArrayList<>());
                        existing.add(r);
                        response.put(r.getOwner_module(), existing);
                        setFlags.add(r.getName());
                    }
                }
            }
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/get_specific", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getSpecificFeatureFlags(String name,
                                                           FeatureFlagLevel level,
                                                           String levelValue) {
        if (level == null) {
            return ResponseEntity.badRequest().build();
        }
        if (name == null) {
            return ResponseEntity.badRequest().build();
        }
        final FeatureFlagStatusResponse status = featureFlagStatusService.getFeatureFlagStatusByLevelAndLevelValueAndName(name, level, levelValue);
        return ResponseEntity.ok(status != null && status.getValue());
    }


    @GetMapping(value = "/get_fallback", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getFallbackFeatureFlags(String name,
                                                           FeatureFlagLevel level,
                                                           String levelValue) {
        if (level == null) {
            return ResponseEntity.badRequest().build();
        }
        if (name == null) {
            return ResponseEntity.badRequest().build();
        }
        final FeatureFlagLevel[] values = FeatureFlagLevel.values();
        Arrays.sort(values, Comparator.comparingInt(Enum::ordinal));
        for (FeatureFlagLevel l : values) {
            if (l.ordinal() >= level.ordinal()) {
                final FeatureFlagStatusResponse status = featureFlagStatusService.getFeatureFlagStatusByLevelAndLevelValueAndName(name, level, levelValue);
                if (status != null) {
                    return ResponseEntity.ok(status.getValue());
                }
            }
        }
        return ResponseEntity.ok(false);    //Will never reach here.
    }

    private String getLevelValueForLevelFromLevel(FeatureFlagLevel sourceLevel,
                                                  String levelValue,
                                                  FeatureFlagLevel destinationLevel) {
        if (destinationLevel == FeatureFlagLevel.SYSTEM) {
            return "SYSTEM";
        }
        if (sourceLevel == destinationLevel) {
            return levelValue;
        }
        Users users;
        switch (sourceLevel) {
            case USER:
                users = userService.getById(levelValue);
                break;
            case ROLE:
                users = userService.getByRole(levelValue);
                break;
            default:
                return "SYSTEM";
        }
        switch (destinationLevel) {
            case ROLE:
                return users.getRole();
            case ORG:
                return users.getOrg();
        }
        return "SYSTEM";
    }

    Map<String, List<FeatureFlagStatusResponse>> getGroupedResponse(List<FeatureFlagStatusResponse> response) {
        return response.stream()
                .collect(Collectors.groupingBy(FeatureFlagStatusResponse::getOwner_module));
    }
}
