package com.feature.flags.service;

import com.feature.flags.dao.service.SearchKeysDaoService;
import com.feature.flags.model.SearchKeywords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

    public Page<SearchKeywords> getByFFPrefix(String key) {
        return daoService.getFFByPrefix(key);
    }

    public void deleteKeyWord(SearchKeywords searchKeywords) {
        daoService.deleteKeyWord(searchKeywords);
    }

    public Page<SearchKeywords> getByPrefixAndType(String key, String name) {
        return daoService.getByPrefixAndType(key, name);
    }
}
