package com.feature.flags.service;

import com.feature.flags.dao.service.FeatureFlagStatusDaoService;
import com.feature.flags.model.FeatureFlagLevel;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.FeatureFlagStatusResponse;
import com.feature.flags.model.StatusResponsePOJO;
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

    public List<StatusResponsePOJO> getAllFeatureFlagStatuses() {
        List<StatusResponsePOJO> allFeatureFlagStatuses = (List<StatusResponsePOJO>) redisService
                .getValue("AllFeatureFlagStatuses");
        if (allFeatureFlagStatuses != null) {
            System.out.println("Got value from redis for key : " + "AllFeatureFlagStatuses");
            return allFeatureFlagStatuses;
        }
        allFeatureFlagStatuses = featureFlagStatusDaoService.getAllFeatureFlagStatus();
        redisService.setValue("AllFeatureFlagStatuses", allFeatureFlagStatuses);
        return allFeatureFlagStatuses;
    }

    public List<StatusResponsePOJO> getAllFeatureFlagStatusByLevelAndLevelValue(FeatureFlagLevel level, String levelValue) {
        List<StatusResponsePOJO> allFeatureFlagStatusByLevelAndLevelValue = (List<StatusResponsePOJO>) redisService
                .getValue(level.name() + ":" + levelValue);
        if (allFeatureFlagStatusByLevelAndLevelValue != null) {
            System.out.println("Got value from redis for key : " + level.name() + ":" + levelValue);
            return allFeatureFlagStatusByLevelAndLevelValue;
        }
        allFeatureFlagStatusByLevelAndLevelValue = this.featureFlagStatusDaoService.getAllFeatureFlagStatusByLevelAndLevelValue(level, levelValue);
        redisService.setValue(level.name() + ":" + levelValue, allFeatureFlagStatusByLevelAndLevelValue);
        return allFeatureFlagStatusByLevelAndLevelValue;
    }

    public StatusResponsePOJO getFeatureFlagStatusByLevelAndLevelValueAndName(String name, FeatureFlagLevel level, String levelValue) {
        StatusResponsePOJO featureFlagStatusByLevelAndLevelValueAndName = (StatusResponsePOJO) redisService
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
