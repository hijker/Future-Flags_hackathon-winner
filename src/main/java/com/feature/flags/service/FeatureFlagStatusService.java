package com.feature.flags.service;

import com.feature.flags.dao.service.FeatureFlagStatusDaoService;
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

    @Autowired
    RedisService redisService;

    public void insertFeatureFlagStatus(FeatureFlagStatus status) {
        redisService.deleteAllKeyFormRedis();
        featureFlagStatusDaoService.insertFeatureFlagStatus(status);
    }

    public List<FeatureFlagStatusResponse> getAllFeatureFlagStatuses() {
        List<FeatureFlagStatusResponse> allFeatureFlagStatuses = (List<FeatureFlagStatusResponse>) redisService
                .getValue("AllFeatureFlagStatuses");
        if (allFeatureFlagStatuses != null) {
            System.out.println("Got value from redis for key : " + "AllFeatureFlagStatuses");
            return allFeatureFlagStatuses;
        }
        allFeatureFlagStatuses = featureFlagStatusDaoService.getAllFeatureFlagStatus();
        redisService.setValue("AllFeatureFlagStatuses", allFeatureFlagStatuses);
        return allFeatureFlagStatuses;
    }

    public List<FeatureFlagStatusResponse> getAllFeatureFlagStatusByLevelAndLevelValue(FeatureFlagLevel level, String levelValue) {
        List<FeatureFlagStatusResponse> allFeatureFlagStatusByLevelAndLevelValue = (List<FeatureFlagStatusResponse>) redisService
                .getValue(level.name() + ":" + levelValue);
        if (allFeatureFlagStatusByLevelAndLevelValue != null) {
            System.out.println("Got value from redis for key : " + level.name() + ":" + levelValue);
            return allFeatureFlagStatusByLevelAndLevelValue;
        }
        allFeatureFlagStatusByLevelAndLevelValue = this.featureFlagStatusDaoService.getAllFeatureFlagStatusByLevelAndLevelValue(level, levelValue);
        redisService.setValue(level.name() + ":" + levelValue, allFeatureFlagStatusByLevelAndLevelValue);
        return allFeatureFlagStatusByLevelAndLevelValue;
    }

    public FeatureFlagStatusResponse getFeatureFlagStatusByLevelAndLevelValueAndName(String name, FeatureFlagLevel level, String levelValue) {
        FeatureFlagStatusResponse featureFlagStatusByLevelAndLevelValueAndName = (FeatureFlagStatusResponse) redisService
                .getValue(name + ":" + level.name() + ":" + levelValue);
        System.out.println("Got value from redis for key : " + name + ":" + level.name() + ":" + levelValue);
        if (featureFlagStatusByLevelAndLevelValueAndName != null) {
            return featureFlagStatusByLevelAndLevelValueAndName;
        }
        featureFlagStatusByLevelAndLevelValueAndName = this.featureFlagStatusDaoService.getFeatureFlagStatusByLevelAndLevelValueAndName(name, level, levelValue);
        redisService.setValue(name + ":" + level.name() + ":" + levelValue, featureFlagStatusByLevelAndLevelValueAndName);
        return featureFlagStatusByLevelAndLevelValueAndName;
    }
}
