package com.feature.flags.resource;

import com.feature.flags.model.Modules;
import com.feature.flags.model.SearchKeywords;
import com.feature.flags.service.ModulesService;
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

import static com.feature.flags.model.SearchObjects.MODULE;

@RestController
@RequestMapping("/module")
public class ModulesResource {

    @Autowired
    ModulesService modulesService;

    @Autowired
    SearchService searchService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createModule(String id) {
        id = id.trim();
        Modules module = new Modules(id);
        modulesService.insertTag(module);
        searchService.insertSearchKeyword(new SearchKeywords(id, MODULE.name(), id, id));
        return ResponseEntity.ok("{ \"message\" : \"Success\" }");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllModules() {
        return ResponseEntity.ok(modulesService.getAll().stream().map(Modules::getId).collect(Collectors.toList()));
    }

}
