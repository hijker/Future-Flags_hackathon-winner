package com.feature.flags.resource;

import com.feature.flags.model.FeatureFlag;
import com.feature.flags.model.FeatureFlagLevel;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.ImpactedFeatures;
import com.feature.flags.model.ImpactedModules;
import com.feature.flags.model.StatusResponse;
import com.feature.flags.model.Users;
import com.feature.flags.service.FeatureFlagService;
import com.feature.flags.service.FeatureFlagStatusService;
import com.feature.flags.service.ImpactedFeatureService;
import com.feature.flags.service.ImpactedModuleService;
import com.feature.flags.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    ImpactedFeatureService impactedFeatureService;

    @Autowired
    ImpactedModuleService impactedModuleService;

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateFeatureFlagStatus(String name,
                                                          Boolean value,
                                                          FeatureFlagLevel level,
                                                          String levelValue,
                                                          String updatedById,
                                                          Boolean force) {
        if (force == null) {
            force = false;
        }
        if (level == null) {
            return ResponseEntity.badRequest().body("No level provided");
        }
        name = name.trim();
        final FeatureFlag existing = featureFlagService.getFeatureFlag(name);
        if (existing == null) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Feature flag name does not exist\" }");
        }
        levelValue = levelValue.trim();
        updatedById = updatedById.trim();
        if (!force && existing.getPreRequisiteFlags() != null && !"".equals(existing.getPreRequisiteFlags())) {
            final String[] split = existing.getPreRequisiteFlags().split("::");
            for (String s : split) {
                final ResponseEntity<Boolean> fallbackFeatureFlags = getFallbackFeatureFlags(s, level, levelValue);
                if (Boolean.FALSE.equals(fallbackFeatureFlags.getBody())) {
                    return ResponseEntity.badRequest().body("{ \"message\" : \"Prerequisite Feature flag " + s + " is not enabled\" }");
                }
            }
        }
        FeatureFlagStatus featureFlagStatus = new FeatureFlagStatus(existing, value, level, levelValue, updatedById, new Date());
        featureFlagStatusService.insertFeatureFlagStatus(featureFlagStatus);
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @Transactional
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFeatureFlagStatus(String name,
                                                          FeatureFlagLevel level,
                                                          String levelValue) {
        name = name.trim();
        levelValue = levelValue.trim();
        if (level == null) {
            return ResponseEntity.badRequest().body("No level provided");
        }
        final FeatureFlag existing = featureFlagService.getFeatureFlag(name);
        if (existing == null) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Feature flag name does not exist\" }");
        }
        if (level == FeatureFlagLevel.SYSTEM) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Feature flag at system level cannot be deleted\" }");
        }
        featureFlagStatusService.deleteStatus(name, level, levelValue);
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

//    @GetMapping(value = "/get_all", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String, List<StatusResponse>>> getAllFeatureFlags() {
//        return ResponseEntity.ok(getGroupedResponse(featureFlagStatusService.getAllFeatureFlagStatuses()));
//    }

    @GetMapping(value = "/get_all_global", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<StatusResponse>>> getAllGlobalFeatureFlags() {
        return ResponseEntity.ok(getGroupedResponse(featureFlagStatusService
                .getAllFeatureFlagStatusByLevelAndLevelValue(FeatureFlagLevel.SYSTEM, "SYSTEM")));
    }

//    @GetMapping(value = "/get_all_specific", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String, List<StatusResponse>>> getAllSpecificFeatureFlags(FeatureFlagLevel level,
//                                                                                        String levelValue) {
//        if (level == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(getGroupedResponse(featureFlagStatusService
//                .getAllFeatureFlagStatusByLevelAndLevelValue(level, levelValue)));
//    }

    @GetMapping(value = "/get_all_fallback", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<StatusResponse>>> getAllFallbackFeatureFlags(FeatureFlagLevel level,
                                                                                        String levelValue,
                                                                                        String impactedModule,
                                                                                        String impactedFeature) {
        if (level == null) {
            level = FeatureFlagLevel.SYSTEM;
            levelValue = "SYSTEM";
        }
        levelValue = levelValue.trim();
        boolean filterOn = false;
        Set<String> allowedFLags = new HashSet<>();
        if (impactedModule != null) {
            impactedModule = impactedModule.trim();
            filterOn = true;
            allowedFLags.addAll(impactedModuleService.getByModule(impactedModule)
                    .stream().map(ImpactedModules::getFeatureFlagName)
                    .collect(Collectors.toList()));
        }
        if (impactedFeature != null) {
            impactedFeature = impactedFeature.trim();
            final Set<String> collect = impactedFeatureService.getByFeature(impactedFeature)
                    .stream().map(ImpactedFeatures::getFeatureFlagName)
                    .collect(Collectors.toSet());
            if (filterOn) {
                allowedFLags.retainAll(collect);
            } else {
                allowedFLags = collect;
            }
            filterOn = true;
        }

        Map<String, List<StatusResponse>> response = new HashMap<>();
        Set<String> setFlags = new HashSet<>();

        final FeatureFlagLevel[] values = FeatureFlagLevel.values();
        Arrays.sort(values, Comparator.comparingInt(Enum::ordinal));

        for (FeatureFlagLevel l : values) {
            if (l.ordinal() >= level.ordinal()) {
                for (StatusResponse r : featureFlagStatusService
                        .getAllFeatureFlagStatusByLevelAndLevelValue(l, getLevelValueForLevelFromLevel(level, levelValue, l))) {
                    if (!setFlags.contains(r.getName()) && (!filterOn || allowedFLags.contains(r.getName()))) {
                        final List<StatusResponse> existing = response.getOrDefault(r.getOwnerModule(), new ArrayList<>());
                        existing.add(r);
                        response.put(r.getOwnerModule(), existing);
                        setFlags.add(r.getName());
                    }
                }
            }
        }
        return ResponseEntity.ok().body(response);
    }

//    @GetMapping(value = "/get_specific", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Boolean> getSpecificFeatureFlags(String name,
//                                                           FeatureFlagLevel level,
//                                                           String levelValue) {
//        if (level == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        if (name == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        final StatusResponse status = featureFlagStatusService.getFeatureFlagStatusByLevelAndLevelValueAndName(name, level, levelValue);
//        return ResponseEntity.ok(status != null && status.getValue());
//    }


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
        name = name.trim();
        final FeatureFlag existing = featureFlagService.getFeatureFlag(name);
        if (existing == null) {
            return ResponseEntity.badRequest().build();
        }
        final FeatureFlagLevel[] values = FeatureFlagLevel.values();
        Arrays.sort(values, Comparator.comparingInt(Enum::ordinal));
        for (FeatureFlagLevel l : values) {
            if (l.ordinal() >= level.ordinal()) {
                final StatusResponse status = featureFlagStatusService.getFeatureFlagStatusByLevelAndLevelValueAndName(name, level, levelValue);
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

    Map<String, List<StatusResponse>> getGroupedResponse(List<StatusResponse> response) {
        return response.stream()
                .collect(Collectors.groupingBy(StatusResponse::getOwnerModule));
    }
}
