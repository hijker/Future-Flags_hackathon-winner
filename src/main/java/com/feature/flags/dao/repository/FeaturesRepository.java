package com.feature.flags.dao.repository;

import com.feature.flags.model.Features;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FeaturesRepository extends ElasticsearchRepository<Features, String> {

}
