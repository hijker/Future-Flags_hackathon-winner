package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.ImpactedFeaturesRepository;
import com.feature.flags.model.ImpactedFeatures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpactedFeatureDaoService {

    @Autowired
    ImpactedFeaturesRepository impactedFeaturesRepository;

    public ImpactedFeatureDaoService(ImpactedFeaturesRepository impactedFeaturesRepository) {
        this.impactedFeaturesRepository = impactedFeaturesRepository;
    }

    public void insertImpactedFeature(ImpactedFeatures feature) {
        impactedFeaturesRepository.save(feature);
    }

    public List<ImpactedFeatures> getByFeature(String feature) {
        return impactedFeaturesRepository.findByImpactedFeature(feature);
    }

    public void deleteByFeatureFlagName(String name) {
        impactedFeaturesRepository.deleteByFeatureFlagName(name);
    }

    public void deleteAll() {
        impactedFeaturesRepository.deleteAll();
    }
}
