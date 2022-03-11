package com.feature.flags.dao.repository;

import com.feature.flags.model.Modules;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ModulesRepository extends ElasticsearchRepository<Modules, String> {

}
