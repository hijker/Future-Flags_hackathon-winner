package com.feature.flags.resource;

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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchResource {

    @Autowired
    SearchService searchService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createEntry(String key,
                                              String type) {
        SearchKeywords pojo = new SearchKeywords(key, type, key);
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
    public ResponseEntity<Map<String, List<SearchKeywords>>> getPrefixSuggestions(String key) {
        if (key == null || "".equals(key) || " ".equals(key)) {
            return ResponseEntity.ok().build();
        }
        key = key.replace(" ", "");
        final Map<String, List<SearchKeywords>> collect = new HashMap<>();
        for (SearchObjects s : SearchObjects.values()) {
            final Page<SearchKeywords> byPrefixAndType = searchService.getByPrefixAndType(key, s.name());
            if (!byPrefixAndType.isEmpty()) {
                collect.put(s.name(), byPrefixAndType.stream()
                        .map(sw -> new SearchKeywords(sw.getKey().replace("::", " "), sw.getType(), sw.getValue()))
                        .collect(Collectors.toList()));
            }
        }
        return ResponseEntity.ok(collect);
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
