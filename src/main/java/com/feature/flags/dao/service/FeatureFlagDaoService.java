package com.feature.flags.dao.service;

import com.feature.flags.model.FeatureFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feature.flags.dao.repository.FeatureFlagRepository;

import java.util.List;

@Service
public class FeatureFlagDaoService {

    @Autowired
    private final FeatureFlagRepository featureFlagRepository;

    public FeatureFlagDaoService(FeatureFlagRepository featureFlagRepository) {
        this.featureFlagRepository = featureFlagRepository;
    }

    public void insertFeatureFlag(FeatureFlag flag) {
        featureFlagRepository.save(flag);
    }

    public FeatureFlag getFeatureFlag(String name) {
        return featureFlagRepository.findById(name).orElse(null);
    }

    public List<FeatureFlag> getAllFeatureFlags() {
        return featureFlagRepository.findAll();
    }

    public void deleteFeatureFlag(String name) {
        featureFlagRepository.deleteByName(name);
    }

    public void deleteAll() {
        featureFlagRepository.deleteAll();
    }
}
