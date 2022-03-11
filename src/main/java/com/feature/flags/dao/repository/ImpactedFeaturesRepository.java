package com.feature.flags.dao.repository;

import com.feature.flags.model.ImpactedFeatures;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImpactedFeaturesRepository extends JpaRepository<ImpactedFeatures, String> {

    List<ImpactedFeatures> findByImpactedFeature(String feature);

    void deleteByFeatureFlagName(String name);
}
