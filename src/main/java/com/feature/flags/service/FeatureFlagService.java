package com.feature.flags.service;

import com.feature.flags.dao.service.FeatureFlagDaoService;
import com.feature.flags.model.FeatureFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureFlagService {

    @Autowired
    FeatureFlagDaoService featureFlagDaoService;

    public void insertFeatureFlag(FeatureFlag flag) {
        featureFlagDaoService.insertFeatureFlag(flag);
    }

    public FeatureFlag getFeatureFlag(String name) {
        return featureFlagDaoService.getFeatureFlag(name);
    }

    public List<FeatureFlag> getAllFeatureFlags() {
        return featureFlagDaoService.getAllFeatureFlags();
    }

    public void deleteFeatureFlag(String name) {
        featureFlagDaoService.deleteFeatureFlag(name);
    }
}
