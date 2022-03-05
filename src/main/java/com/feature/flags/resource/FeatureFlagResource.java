package com.feature.flags.resource;

import com.feature.flags.model.FeatureFlag;
import com.feature.flags.model.FeatureFlagStatus;
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
@RequestMapping("/ff")
public class FeatureFlagResource {

    @Autowired
    FeatureFlagService featureFlagService;

    @Autowired
    FeatureFlagStatusService featureFlagStatusService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createFeatureFlag(String name,
                                                    String summary,
                                                    String description,
                                                    String ownerModule,
                                                    String maxGranularity,
                                                    String training,
                                                    String type,
                                                    Boolean needsConfirmation,
                                                    String deprecationFlow,
                                                    String reasonForIntroduction,
                                                    Long createdById) {
        final FeatureFlag existing = featureFlagService.getFeatureFlag(name);
        if (existing != null) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Feature flag name already exist\" }");
        }
        final FeatureFlag featureFlag = new FeatureFlag(name, summary, description, ownerModule, maxGranularity, training,
                type, needsConfirmation, deprecationFlow, reasonForIntroduction, new Date(), new Date(), createdById);
        featureFlagService.insertFeatureFlag(featureFlag);
        FeatureFlagStatus featureFlagStatus = new FeatureFlagStatus(featureFlag, false, null, null, null);
        featureFlagStatusService.insertFeatureFlagStatus(featureFlagStatus);
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeatureFlag> getFeatureFlag(String name) {
        return ResponseEntity.ok(featureFlagService.getFeatureFlag(name));
    }

    @GetMapping(value = "/get_all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FeatureFlag>> getAllFeatureFlags() {
        return ResponseEntity.ok(featureFlagService.getAllFeatureFlags());
    }

}
