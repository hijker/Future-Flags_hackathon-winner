package com.feature.flags.resource;

import com.feature.flags.model.Features;
import com.feature.flags.model.SearchKeywords;
import com.feature.flags.service.FeaturesService;
import com.feature.flags.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.feature.flags.model.SearchObjects.FEATURE;

@RestController
@RequestMapping("/feature")
public class FeaturesResource {

    @Autowired
    FeaturesService featuresService;

    @Autowired
    SearchService searchService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createFeature(String id) {
        id = id.trim();
        Features features = new Features(id);
        featuresService.insertFeature(features);
        searchService.insertSearchKeyword(new SearchKeywords(id, FEATURE.name()));
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllFeatures() {
        return ResponseEntity.ok(featuresService.getAll().stream().map(Features::getId).collect(Collectors.toList()));
    }

}
