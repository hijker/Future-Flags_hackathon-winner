package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.ElasticSearchPOJORepository;
import com.feature.flags.dao.repository.TagsRepository;
import com.feature.flags.model.ElasticSearchPOJO;
import com.feature.flags.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticSearchDaoService {

    @Autowired
    ElasticSearchPOJORepository repository;

    public ElasticSearchDaoService(ElasticSearchPOJORepository repository) {
        this.repository = repository;
    }

    public void insertElasticSearchPOJO(ElasticSearchPOJO pojo) {
        repository.save(pojo);
    }

    public Page<ElasticSearchPOJO> getByKey(String key) {
        return repository.findByKey(key, PageRequest.of(0, 1000));
    }

    public Page<ElasticSearchPOJO> getByPrefix(String key) {
        return repository.findByKeyStartingWith(key, PageRequest.of(0, 1000));
    }

    public Iterable<ElasticSearchPOJO> getAll() {
        return repository.findAll();
    }

}
