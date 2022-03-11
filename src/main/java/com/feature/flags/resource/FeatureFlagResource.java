package com.feature.flags.resource;

import com.feature.flags.model.FeatureFlag;
import com.feature.flags.model.FeatureFlagLevel;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.ImpactedFeatures;
import com.feature.flags.model.ImpactedModules;
import com.feature.flags.model.SearchKeywords;
import com.feature.flags.service.FeatureFlagService;
import com.feature.flags.service.FeatureFlagStatusService;
import com.feature.flags.service.ImpactedFeatureService;
import com.feature.flags.service.ImpactedModuleService;
import com.feature.flags.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.feature.flags.model.SearchObjects.FFNAME;

@RestController
@RequestMapping("/ff")
public class FeatureFlagResource {

    @Autowired
    FeatureFlagService featureFlagService;

    @Autowired
    FeatureFlagStatusService featureFlagStatusService;

    @Autowired
    ImpactedFeatureService impactedFeatureService;

    @Autowired
    ImpactedModuleService impactedModuleService;

    @Autowired
    SearchService searchService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createFeatureFlag(String name,
                                                    String summary,
                                                    String description,
                                                    String ownerModule,
                                                    String ownerFeature,
                                                    @RequestParam List<String> impactedModules,
                                                    @RequestParam List<String> impactedFeatures,
                                                    String maxGranularity,
                                                    String training,
                                                    String type,
                                                    Boolean needsConfirmation,
                                                    String deprecationFlow,
                                                    String reasonForIntroduction,
                                                    String createdById) {
        final FeatureFlag existing = featureFlagService.getFeatureFlag(name);
        if (existing != null) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Feature flag name already exist\" }");
        }
        //Todo : Add check if impacted modules and features are valid
        final FeatureFlag featureFlag = new FeatureFlag(name, summary, description, ownerModule, ownerFeature,
                maxGranularity, training, type, needsConfirmation, deprecationFlow, reasonForIntroduction,
                new Date(), new Date(), createdById,
                String.join("::", impactedModules), String.join("::", impactedFeatures));
        featureFlagService.insertFeatureFlag(featureFlag);
        FeatureFlagStatus featureFlagStatus = new FeatureFlagStatus(featureFlag, false, FeatureFlagLevel.SYSTEM,
                "SYSTEM", createdById, new Date());
        featureFlagStatusService.insertFeatureFlagStatus(featureFlagStatus);
        for (String im : impactedModules) {
            impactedModuleService.insertImpactedModule(new ImpactedModules(name, im));
        }
        for (String imf : impactedFeatures) {
            impactedFeatureService.insertImpactedFeature(new ImpactedFeatures(name, imf));
        }
        searchService.insertSearchKeyword(new SearchKeywords(name, FFNAME.name()));
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isNameAvailableFeatureFlag(String name) {
        final FeatureFlag featureFlag = featureFlagService.getFeatureFlag(name);
        if (featureFlag != null) {
            return ResponseEntity.badRequest().body(false);
        }
        return ResponseEntity.ok().body(true);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeatureFlag> getFeatureFlag(String name) {
        return ResponseEntity.ok(featureFlagService.getFeatureFlag(name));
    }

//    @GetMapping(value = "/get_all", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<FeatureFlag>> getAllFeatureFlags() {
//        return ResponseEntity.ok(featureFlagService.getAllFeatureFlags());
//    }

}
