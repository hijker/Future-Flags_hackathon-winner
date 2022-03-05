package com.feature.flags.dao.repository;

import com.feature.flags.model.FeatureFlagStatus;
import com.feature.flags.model.FeatureFlagStatusResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeatureFlagStatusRepository extends JpaRepository<FeatureFlagStatus, String> {

    @Query(value = "select feature_flag.name, value, summary from feature_flag inner join feature_flag_status on feature_flag.name = feature_flag_status.feature_flag_name", nativeQuery = true)
//    @Query(value = "select new com.feature.flags.model.FeatureFlagStatusResponse(flag.name, status.value, flag.summary) from feature_flag flag, feature_flag_status status", nativeQuery = true)
    List<FeatureFlagStatusResponse> getFeatureFlagStatusResponse();

}
