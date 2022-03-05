package com.feature.flags.resource;

import com.feature.flags.model.FeatureFlag;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.FeatureFlagStatusResponse;
import com.feature.flags.service.FeatureFlagService;
import com.feature.flags.service.FeatureFlagStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ffs")
public class FeatureFlagStatusResource {

    @Autowired
    FeatureFlagService featureFlagService;

    @Autowired
    FeatureFlagStatusService featureFlagStatusService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateFeatureFlagStatus(String name,
                                                          Boolean value,
                                                          Long userId,
                                                          Integer orgId,
                                                          String roleId,
                                                          Long createdById) {
        final FeatureFlag existing = featureFlagService.getFeatureFlag(name);
        if (existing == null) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Feature flag name does not exist\" }");
        }
        int nonNullCount = 0;
        if(userId != null) {
            nonNullCount++;
        }
        if(roleId != null) {
            nonNullCount++;
        }
        if(orgId != null) {
            nonNullCount++;
        }
        if(nonNullCount > 1) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Keep only userId or orgId or roleId as non null\" }");
        }
        FeatureFlagStatus featureFlagStatus = new FeatureFlagStatus(existing, value, userId, orgId, null);
        featureFlagStatusService.insertFeatureFlagStatus(featureFlagStatus);
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/get_all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FeatureFlagStatusResponse>> getAllFeatureFlags() {
        return ResponseEntity.ok(featureFlagStatusService.getAllFeatureFlagStatuses());
    }

}
