package com.feature.flags.service;

import com.feature.flags.dao.service.ElasticSearchDaoService;
import com.feature.flags.model.ElasticSearchPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {

    @Autowired
    ElasticSearchDaoService daoService;

    public void insertElasticSearchPOJO(ElasticSearchPOJO pojo) {
        daoService.insertElasticSearchPOJO(pojo);
    }

    public Page<ElasticSearchPOJO> getByKey(String key) {
        return daoService.getByKey(key);
    }

    public Page<ElasticSearchPOJO> getByPrefix(String key) {
        return daoService.getByPrefix(key);
    }

    public Iterable<ElasticSearchPOJO> getAll() {
        return daoService.getAll();
    }

}
