package com.feature.flags.service;

import com.feature.flags.dao.service.FeatureFlagStatusDaoService;
import com.feature.flags.model.FeatureFlagLevel;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.StatusResponse;
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
        redisService.deleteKey(status.getLevel().name() + ":" + status.getLevelValue());
        redisService.deleteKey(status.getFlag().getName() + ":" + status.getLevel().name() + ":" + status.getLevelValue());
        featureFlagStatusDaoService.insertFeatureFlagStatus(status);
    }

    public List<StatusResponse> getAllFeatureFlagStatusByLevelAndLevelValue(FeatureFlagLevel level, String levelValue) {
        List<StatusResponse> allFeatureFlagStatusByLevelAndLevelValue = (List<StatusResponse>) redisService
                .getValue(level.name() + ":" + levelValue);
        if (allFeatureFlagStatusByLevelAndLevelValue != null) {
            System.out.println("Got value from redis for key : " + level.name() + ":" + levelValue);
            return allFeatureFlagStatusByLevelAndLevelValue;
        }
        allFeatureFlagStatusByLevelAndLevelValue = this.featureFlagStatusDaoService.getAllFeatureFlagStatusByLevelAndLevelValue(level, levelValue);
        redisService.setValue(level.name() + ":" + levelValue, allFeatureFlagStatusByLevelAndLevelValue);
        return allFeatureFlagStatusByLevelAndLevelValue;
    }

    public StatusResponse getFeatureFlagStatusByLevelAndLevelValueAndName(String name, FeatureFlagLevel level, String levelValue) {
        StatusResponse featureFlagStatusByLevelAndLevelValueAndName = (StatusResponse) redisService
                .getValue(name + ":" + level.name() + ":" + levelValue);
        System.out.println("Got value from redis for key : " + name + ":" + level.name() + ":" + levelValue);
        if (featureFlagStatusByLevelAndLevelValueAndName != null) {
            return featureFlagStatusByLevelAndLevelValueAndName;
        }
        featureFlagStatusByLevelAndLevelValueAndName = this.featureFlagStatusDaoService.getFeatureFlagStatusByLevelAndLevelValueAndName(name, level, levelValue);
        redisService.setValue(name + ":" + level.name() + ":" + levelValue, featureFlagStatusByLevelAndLevelValueAndName);
        return featureFlagStatusByLevelAndLevelValueAndName;
    }

    public void deleteStatus(String name, FeatureFlagLevel level, String levelValue) {
        redisService.deleteKey(name + ":" + level + ":" + levelValue);
        this.featureFlagStatusDaoService.deleteStatus(name, level, levelValue);
    }

    public void deleteAllStatus(String name) {
        redisService.deleteAllKey();
        featureFlagStatusDaoService.deleteAllStatus(name);
    }
}
