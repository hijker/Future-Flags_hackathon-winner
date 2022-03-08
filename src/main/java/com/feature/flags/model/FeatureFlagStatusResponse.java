package com.feature.flags.model;

import java.util.Date;

public interface FeatureFlagStatusResponse {

    String getName();
    Boolean getValue();
    String getSummary();
    Date  getLastUpdated();
    String getOwnerModule();
    FeatureFlagLevel getLevel();
}
