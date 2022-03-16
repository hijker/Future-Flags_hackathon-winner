package com.feature.flags.resource;

import com.feature.flags.model.FeatureFlagLevel;
import com.feature.flags.model.SearchKeywords;
import com.feature.flags.model.SearchObjects;
import com.feature.flags.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchResource {

    @Autowired
    SearchService searchService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createEntry(String key,
                                              String type) {
        SearchKeywords pojo = new SearchKeywords(key, type, key, key);
        searchService.insertSearchKeyword(pojo);
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchKeywords>> getAllUsers() {
        List<SearchKeywords> list = new ArrayList<>();
        searchService.getAll().forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchKeywords>> getSuggestions(String key) {
        return ResponseEntity.ok(searchService.getByKey(key).toList());
    }

    @GetMapping(value = "/prefix", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<SearchKeywords>>> getPrefixSuggestions(String key) throws ExecutionException, InterruptedException {
        ExecutorService executor
                = Executors.newFixedThreadPool(SearchObjects.values().length);
        if (key == null || "".equals(key) || " ".equals(key)) {
            return ResponseEntity.ok().build();
        }
        key = key.replace(" ", "::");
        final Map<String, List<SearchKeywords>> collect = new HashMap<>();

        List<Future<Page<SearchKeywords>>> futures = new ArrayList<>();
        for (SearchObjects s : SearchObjects.values()) {
            String finalKey = key;
            futures.add(executor.submit(() -> searchService.getByPrefixAndType(finalKey, s.name())));
        }

        for (Future<Page<SearchKeywords>> f : futures) {
            final Page<SearchKeywords> byPrefixAndType = f.get();
            if (!byPrefixAndType.isEmpty()) {
                final List<SearchKeywords> content = byPrefixAndType.getContent();
                collect.put(content.get(0).getType(), content);
            }
        }
        return ResponseEntity.ok(collect);
    }

    @GetMapping(value = "/specific_prefix", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchKeywords>> getPrefixSuggestions(String key,
                                                                     FeatureFlagLevel level) {
        if (key == null || "".equals(key) || " ".equals(key)) {
            return ResponseEntity.ok().build();
        }
        if (level == null) {
            return ResponseEntity.badRequest().build();
        }
        key = key.replace(" ", "::");
        return ResponseEntity.ok(searchService.getByPrefixAndType(key, level.name()).toList());
    }

    @GetMapping(value = "/ffprefix", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchKeywords>> getFFPrefixSuggestions(String key) {
        if (key == null || "".equals(key) || " ".equals(key)) {
            return ResponseEntity.ok().build();
        }
        key = key.trim();
        final List<SearchKeywords> collect = searchService
                .getByFFPrefix(key).stream().collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }
}
