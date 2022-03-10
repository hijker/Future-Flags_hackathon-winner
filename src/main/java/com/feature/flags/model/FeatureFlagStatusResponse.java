package com.feature.flags.model;

import java.util.Date;

public interface FeatureFlagStatusResponse {

    String getName();
    Boolean getValue();
    String getSummary();
    Date getUpdated_At();
    String getOwner_module();
    FeatureFlagLevel getLevel();
}
