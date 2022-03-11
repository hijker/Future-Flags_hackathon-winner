package com.feature.flags.service;

import com.feature.flags.dao.service.SearchKeysDaoService;
import com.feature.flags.model.SearchKeywords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    SearchKeysDaoService daoService;

    public void insertSearchKeyword(SearchKeywords pojo) {
        daoService.insertElasticSearchPOJO(pojo);
    }

    public Page<SearchKeywords> getByKey(String key) {
        return daoService.getByKey(key);
    }

    public Page<SearchKeywords> getByPrefix(String key) {
        return daoService.getByPrefix(key);
    }

    public Iterable<SearchKeywords> getAll() {
        return daoService.getAll();
    }

}
