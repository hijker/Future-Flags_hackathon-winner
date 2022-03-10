package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.FeatureFlagStatusRepository;
import com.feature.flags.model.FeatureFlagLevel;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.FeatureFlagStatusResponse;
import com.feature.flags.model.StatusResponsePOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<StatusResponsePOJO> getAllFeatureFlagStatus() {
        return this.featureFlagStatusRepository.getAllFeatureFlagStatusResponse()
                .stream()
                .map(this::getSRP)
                .collect(Collectors.toList());
    }

    public List<StatusResponsePOJO> getAllFeatureFlagStatusByLevelAndLevelValue(FeatureFlagLevel level, String levelValue) {
        return this.featureFlagStatusRepository.getAllFeatureFlagStatusByLevelAndLevelValue(level.name(), levelValue)
                .stream()
                .map(this::getSRP)
                .collect(Collectors.toList());
    }

    public StatusResponsePOJO getFeatureFlagStatusByLevelAndLevelValueAndName(String name, FeatureFlagLevel level, String levelValue) {
        return getSRP(this.featureFlagStatusRepository.getFeatureFlagStatusByNameAndLevelAndLevelValue(name, level.name(), levelValue));
    }

    StatusResponsePOJO getSRP(FeatureFlagStatusResponse response) {
        return new StatusResponsePOJO(response.getName(),
                response.getValue(),
                response.getSummary(),
                response.getUpdated_At(),
                response.getOwner_module(),
                response.getLevel());
    }
}
