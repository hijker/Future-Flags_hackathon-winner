package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.FeatureFlagStatusRepository;
import com.feature.flags.model.FeatureFlagLevel;
import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.FeatureFlagStatusResponse;
import com.feature.flags.model.StatusResponse;
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

    public List<StatusResponse> getAllFeatureFlagStatus() {
        return this.featureFlagStatusRepository.getAllFeatureFlagStatusResponse()
                .stream()
                .map(this::getSRP)
                .collect(Collectors.toList());
    }

    public List<StatusResponse> getAllFeatureFlagStatusByLevelAndLevelValue(FeatureFlagLevel level, String levelValue) {
        return this.featureFlagStatusRepository.getAllFeatureFlagStatusByLevelAndLevelValue(level.name(), levelValue)
                .stream()
                .map(this::getSRP)
                .collect(Collectors.toList());
    }

    public StatusResponse getFeatureFlagStatusByLevelAndLevelValueAndName(String name, FeatureFlagLevel level, String levelValue) {
        return getSRP(this.featureFlagStatusRepository.getFeatureFlagStatusByNameAndLevelAndLevelValue(name, level.name(), levelValue));
    }

    StatusResponse getSRP(FeatureFlagStatusResponse response) {
        return new StatusResponse(response.getName(),
                response.getValue(),
                response.getSummary(),
                response.getUpdated_At(),
                response.getOwner_module(),
                response.getLevel());
    }
}
