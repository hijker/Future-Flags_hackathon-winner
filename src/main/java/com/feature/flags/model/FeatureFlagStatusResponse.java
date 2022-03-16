package com.feature.flags.model;

import java.util.Date;

public interface FeatureFlagStatusResponse {

    String getName();
    Boolean getValue();
    String getSummary();
    Date getUpdated_At();
    String getOwner_module();
    FeatureFlagLevel getLevel();
    String getImpacted_modules();
    String getImpacted_features();
    String getUpdated_by();
    Boolean getNeeds_confirmation();
}
