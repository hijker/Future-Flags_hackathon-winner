package com.feature.flags.dao.repository;

import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.FeatureFlagStatusResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeatureFlagStatusRepository extends JpaRepository<FeatureFlagStatus, String> {

    @Query(value = "select feature_flag.name, value, summary, feature_flag_status.updated_at, owner_module, level " +
            "from feature_flag inner join feature_flag_status " +
            "on feature_flag.name = feature_flag_status.feature_flag_name", nativeQuery = true)
    List<FeatureFlagStatusResponse> getAllFeatureFlagStatusResponse();

    @Query(value = "select feature_flag.name, value, summary, feature_flag_status.updated_at, owner_module, level " +
            "from feature_flag inner join feature_flag_status " +
            "on feature_flag.name = feature_flag_status.feature_flag_name " +
            "where feature_flag_status.level = :level and feature_flag_status.level_value = :levelValue", nativeQuery = true)
    List<FeatureFlagStatusResponse> getAllFeatureFlagStatusByLevelAndLevelValue(String level, String levelValue);

    @Query(value = "select feature_flag.name, value, summary, feature_flag_status.updated_at, owner_module, level " +
            "from feature_flag inner join feature_flag_status " +
            "on feature_flag.name = feature_flag_status.feature_flag_name " +
            "where feature_flag_status.level = :level and " +
            "feature_flag_status.level_value = :levelValue and " +
            "feature_flag.name = :name", nativeQuery = true)
    FeatureFlagStatusResponse getFeatureFlagStatusByNameAndLevelAndLevelValue(String name, String level, String levelValue);

    void deleteAllByFlag_Name(String name);
}
