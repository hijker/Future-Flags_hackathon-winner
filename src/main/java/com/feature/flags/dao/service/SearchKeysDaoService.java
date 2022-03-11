package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.SearchKeysRepository;
import com.feature.flags.model.SearchKeywords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.feature.flags.model.SearchObjects.FFNAME;

@Service
public class SearchKeysDaoService {

    @Autowired
    SearchKeysRepository repository;

    public SearchKeysDaoService(SearchKeysRepository repository) {
        this.repository = repository;
    }

    public void insertElasticSearchPOJO(SearchKeywords pojo) {
        repository.save(pojo);
    }

    public Page<SearchKeywords> getByKey(String key) {
        return repository.findByKey(key, PageRequest.of(0, 1000));
    }

    public Page<SearchKeywords> getByPrefix(String key) {
        return repository.findByKeyStartingWith(key, PageRequest.of(0, 10));
    }

    public Iterable<SearchKeywords> getAll() {
        return repository.findAll();
    }

    public Page<SearchKeywords> getFFByPrefix(String key) {
        return repository.findByKeyStartingWithAndAndType(key, PageRequest.of(0, 10), FFNAME.name());
    }
}
