package com.feature.flags.dao.repository;

import com.feature.flags.model.SearchKeywords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchKeysRepository extends ElasticsearchRepository<SearchKeywords, String> {

    Page<SearchKeywords> findByKey(String key, Pageable pageable);

    Page<SearchKeywords> findByKeyStartingWith(String key, Pageable pageable);

    Page<SearchKeywords> findByKeyStartingWithAndAndType(String key, Pageable pageable, String type);


}
