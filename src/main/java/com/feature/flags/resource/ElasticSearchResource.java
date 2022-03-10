package com.feature.flags.resource;

import com.feature.flags.model.ElasticSearchPOJO;
import com.feature.flags.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class ElasticSearchResource {

    @Autowired
    ElasticSearchService elasticSearchService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createEntry(String key,
                                              String value,
                                              String type) {
        ElasticSearchPOJO pojo = new ElasticSearchPOJO(key, value, type);
        elasticSearchService.insertElasticSearchPOJO(pojo);
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ElasticSearchPOJO>> getAllUsers() {
        List<ElasticSearchPOJO> list = new ArrayList<>();
        elasticSearchService.getAll().forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ElasticSearchPOJO>> getSuggestions(String key) {
        return ResponseEntity.ok(elasticSearchService.getByKey(key).toList());
    }

    @GetMapping(value = "/prefix", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ElasticSearchPOJO>> getPrefixSuggestions(String key) {
        return ResponseEntity.ok(elasticSearchService.getByPrefix(key).toList());
    }
}
