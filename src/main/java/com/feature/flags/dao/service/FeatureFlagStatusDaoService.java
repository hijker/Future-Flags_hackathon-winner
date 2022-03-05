package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.FeatureFlagStatusRepository;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.FeatureFlagStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureFlagStatusDaoService {

    @Autowired
    private final FeatureFlagStatusRepository featureFlagStatusRepository;


    public FeatureFlagStatusDaoService(FeatureFlagStatusRepository featureFlagStatusRepository) {
        this.featureFlagStatusRepository = featureFlagStatusRepository;
    }

    public void insertFeatureFlagStatus(FeatureFlagStatus featureFlagStatus) {
        this.featureFlagStatusRepository.save(featureFlagStatus);
    }

    public List<FeatureFlagStatusResponse> getAllFeatureFlagStatus() {
        return this.featureFlagStatusRepository.getFeatureFlagStatusResponse();
    }
}
