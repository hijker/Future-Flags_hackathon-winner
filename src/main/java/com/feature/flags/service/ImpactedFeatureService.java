package com.feature.flags.service;

import com.feature.flags.dao.service.ImpactedFeatureDaoService;
import com.feature.flags.model.ImpactedFeatures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpactedFeatureService {

    @Autowired
    ImpactedFeatureDaoService impactedFeatureDaoService;

    public void insertImpactedFeature(ImpactedFeatures feature) {
        impactedFeatureDaoService.insertImpactedFeature(feature);
    }

    public List<ImpactedFeatures> getByFeature(String feature) {
        return impactedFeatureDaoService.getByFeature(feature);
    }

    public void deleteImpactedFeatureByName(String name) {
        impactedFeatureDaoService.deleteByFeatureFlagName(name);
    }
}
