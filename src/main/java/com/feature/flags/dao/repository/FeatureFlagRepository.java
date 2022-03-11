package com.feature.flags.dao.repository;

import com.feature.flags.model.FeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, String> {

    void deleteByName(String name);
}
