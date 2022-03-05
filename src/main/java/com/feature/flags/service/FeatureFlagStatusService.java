package com.feature.flags.service;

import com.feature.flags.dao.service.FeatureFlagStatusDaoService;
import com.feature.flags.model.FeatureFlag;
import com.feature.flags.model.FeatureFlagLevel;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.FeatureFlagStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureFlagStatusService {

    @Autowired
    FeatureFlagStatusDaoService featureFlagStatusDaoService;

    public void insertFeatureFlagStatus(FeatureFlagStatus status) {
        featureFlagStatusDaoService.insertFeatureFlagStatus(status);
    }

    public List<FeatureFlagStatusResponse> getAllFeatureFlagStatuses() {
        return featureFlagStatusDaoService.getAllFeatureFlagStatus();
    }

    public List<FeatureFlagStatusResponse> getFeatureFlagStatusByLevelAndLevelValue(FeatureFlagLevel level, String levelValue) {
        return this.featureFlagStatusDaoService.getFeatureFlagStatusByLevelAndLevelValue(level, levelValue);
    }
}
