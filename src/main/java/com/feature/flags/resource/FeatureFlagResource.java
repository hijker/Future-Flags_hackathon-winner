package com.feature.flags.resource;

import com.feature.flags.model.FeatureFlag;
import com.feature.flags.model.FeatureFlagLevel;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.Features;
import com.feature.flags.model.ImpactedFeatures;
import com.feature.flags.model.ImpactedModules;
import com.feature.flags.model.Modules;
import com.feature.flags.model.SearchKeywords;
import com.feature.flags.service.FeatureFlagService;
import com.feature.flags.service.FeatureFlagStatusService;
import com.feature.flags.service.FeaturesService;
import com.feature.flags.service.ImpactedFeatureService;
import com.feature.flags.service.ImpactedModuleService;
import com.feature.flags.service.ModulesService;
import com.feature.flags.service.RedisService;
import com.feature.flags.service.SearchService;
import org.elasticsearch.core.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    ModulesService modulesService;

    @Autowired
    FeaturesService featuresService;

    @Autowired
    RedisService redisService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createFeatureFlag(String name,
                                                    String summary,
                                                    String description,
                                                    String ownerModule,
                                                    String ownerFeature,
                                                    @Nullable @RequestParam List<String> impactedModules,
                                                    @Nullable @RequestParam List<String> impactedFeatures,
                                                    String maxGranularity,
                                                    String training,
                                                    String type,
                                                    Boolean needsConfirmation,
                                                    String deprecationFlow,
                                                    String reasonForIntroduction,
                                                    String createdById,
                                                    @Nullable @RequestParam List<String> preRequisiteFlags) {
        if (impactedModules == null) {
            impactedModules = new ArrayList<>();
        }
        if (impactedFeatures == null) {
            impactedFeatures = new ArrayList<>();
        }
        if (preRequisiteFlags == null) {
            preRequisiteFlags = new ArrayList<>();
        }
        name = name.trim();
        summary = summary.trim();
        description = description.trim();
        ownerModule = ownerModule.trim();
        ownerFeature = ownerFeature.trim();
        maxGranularity = maxGranularity.trim();
        training = training.trim();
        type = type.trim();
        deprecationFlow = deprecationFlow.trim();
        reasonForIntroduction = reasonForIntroduction.trim();
        createdById = createdById.trim();
        final FeatureFlag existing = featureFlagService.getFeatureFlag(name);
        if (existing != null) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Feature flag name already exist\" }");
        }
        final Set<String> modules = modulesService.getAll().stream().map(Modules::getId).collect(Collectors.toSet());
        if (!modules.contains(ownerModule)) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Invalid owner module\" }");
        }
        for (String im : impactedModules) {
            if (!modules.contains(im)) {
                return ResponseEntity.badRequest().body("{ \"message\" : \"Invalid impacted module : " + im + "\" }");
            }
        }
        final Set<String> features = featuresService.getAll().stream().map(Features::getId).collect(Collectors.toSet());
        if (!features.contains(ownerFeature)) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Invalid owner feature\" }");
        }
        for (String im : impactedFeatures) {
            if (!features.contains(im)) {
                return ResponseEntity.badRequest().body("{ \"message\" : \"Invalid impacted feature : " + im + "\" }");
            }
        }
        for (String p : preRequisiteFlags) {
            final FeatureFlag featureFlag = featureFlagService.getFeatureFlag(p);
            if (featureFlag == null) {
                return ResponseEntity.badRequest().body("{ \"message\" : \"Invalid pre-requisite feature : " + p + "\" }");
            }
        }
        final FeatureFlag featureFlag = new FeatureFlag(name, summary, description, ownerModule, ownerFeature,
                maxGranularity, training, type, needsConfirmation, deprecationFlow, reasonForIntroduction,
                new Date(), new Date(), createdById,
                String.join("::", impactedModules), String.join("::", impactedFeatures),
                String.join("::", preRequisiteFlags));
        featureFlagService.insertFeatureFlag(featureFlag);
        FeatureFlagStatus featureFlagStatus = new FeatureFlagStatus(featureFlag, false, FeatureFlagLevel.SYSTEM,
                "SYSTEM", createdById, new Date());
        featureFlagStatusService.insertFeatureFlagStatus(featureFlagStatus);
        impactedModuleService.insertImpactedModule(new ImpactedModules(name, ownerModule));
        for (String im : impactedModules) {
            impactedModuleService.insertImpactedModule(new ImpactedModules(name, im));
        }
        impactedFeatureService.insertImpactedFeature(new ImpactedFeatures(name, ownerFeature));
        for (String imf : impactedFeatures) {
            impactedFeatureService.insertImpactedFeature(new ImpactedFeatures(name, imf));
        }
        searchService.insertSearchKeyword(new SearchKeywords(name, FFNAME.name(), name));
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @Transactional
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateFeatureFlag(String name,
                                                    String summary,
                                                    String description,
                                                    String ownerModule,
                                                    String ownerFeature,
                                                    @Nullable @RequestParam List<String> impactedModules,
                                                    @Nullable @RequestParam List<String> impactedFeatures,
                                                    String maxGranularity,
                                                    String training,
                                                    String type,
                                                    Boolean needsConfirmation,
                                                    String deprecationFlow,
                                                    String reasonForIntroduction,
                                                    String createdById,
                                                    @Nullable @RequestParam List<String> preRequisiteFlags) {
        if (impactedModules == null) {
            impactedModules = new ArrayList<>();
        }
        if (impactedFeatures == null) {
            impactedFeatures = new ArrayList<>();
        }
        if (preRequisiteFlags == null) {
            preRequisiteFlags = new ArrayList<>();
        }
        name = name.trim();
        summary = summary.trim();
        description = description.trim();
        ownerModule = ownerModule.trim();
        ownerFeature = ownerFeature.trim();
        maxGranularity = maxGranularity.trim();
        training = training.trim();
        type = type.trim();
        deprecationFlow = deprecationFlow.trim();
        reasonForIntroduction = reasonForIntroduction.trim();
        createdById = createdById.trim();
        final FeatureFlag existing = featureFlagService.getFeatureFlag(name);
        if (existing == null) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Feature flag name does not exist\" }");
        }
        final Set<String> modules = modulesService.getAll().stream().map(Modules::getId).collect(Collectors.toSet());
        if (!modules.contains(ownerModule)) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Invalid owner module\" }");
        }
        for (String im : impactedModules) {
            if (!modules.contains(im)) {
                return ResponseEntity.badRequest().body("{ \"message\" : \"Invalid impacted module : " + im + "\" }");
            }
        }
        final Set<String> features = featuresService.getAll().stream().map(Features::getId).collect(Collectors.toSet());
        if (!features.contains(ownerFeature)) {
            return ResponseEntity.badRequest().body("{ \"message\" : \"Invalid owner feature\" }");
        }
        for (String im : impactedFeatures) {
            if (!features.contains(im)) {
                return ResponseEntity.badRequest().body("{ \"message\" : \"Invalid impacted feature : " + im + "\" }");
            }
        }
        for (String p : preRequisiteFlags) {
            final FeatureFlag featureFlag = featureFlagService.getFeatureFlag(p);
            if (featureFlag == null) {
                return ResponseEntity.badRequest().body("{ \"message\" : \"Invalid pre-requisite feature : " + p + "\" }");
            }
        }
        final FeatureFlag featureFlag = new FeatureFlag(name, summary, description, ownerModule, ownerFeature,
                maxGranularity, training, type, needsConfirmation, deprecationFlow, reasonForIntroduction,
                new Date(), new Date(), createdById,
                String.join("::", impactedModules), String.join("::", impactedFeatures),
                String.join("::", preRequisiteFlags));
        featureFlagService.insertFeatureFlag(featureFlag);
        impactedModuleService.deleteImpactedModuleByName(name);
        impactedModuleService.insertImpactedModule(new ImpactedModules(name, ownerModule));
        for (String im : impactedModules) {
            impactedModuleService.insertImpactedModule(new ImpactedModules(name, im));
        }
        impactedFeatureService.deleteImpactedFeatureByName(name);
        impactedFeatureService.insertImpactedFeature(new ImpactedFeatures(name, ownerFeature));
        for (String imf : impactedFeatures) {
            impactedFeatureService.insertImpactedFeature(new ImpactedFeatures(name, imf));
        }
        redisService.deleteAllKey();
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @Transactional
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFeatureFlag(String name) {
        name = name.trim();
        featureFlagStatusService.deleteAllStatus(name);
        featureFlagService.deleteFeatureFlag(name);
        searchService.deleteKeyWord(new SearchKeywords(name, FFNAME.name(), name));
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isNameAvailableFeatureFlag(String name) {
        name = name.trim();
        final FeatureFlag featureFlag = featureFlagService.getFeatureFlag(name);
        if (featureFlag != null) {
            return ResponseEntity.badRequest().body(false);
        }
        return ResponseEntity.ok().body(true);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeatureFlag> getFeatureFlag(String name) {
        name = name.trim();
        return ResponseEntity.ok(featureFlagService.getFeatureFlag(name));
    }

//    @GetMapping(value = "/get_all", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<FeatureFlag>> getAllFeatureFlags() {
//        return ResponseEntity.ok(featureFlagService.getAllFeatureFlags());
//    }

}
