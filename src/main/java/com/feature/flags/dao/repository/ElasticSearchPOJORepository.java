package com.feature.flags.dao.repository;

import com.feature.flags.model.ElasticSearchPOJO;
import com.feature.flags.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElasticSearchPOJORepository extends ElasticsearchRepository<ElasticSearchPOJO, String> {

    Page<ElasticSearchPOJO> findByKey(String key, Pageable pageable);

    Page<ElasticSearchPOJO> findByKeyStartingWith(String key, Pageable pageable);


}
