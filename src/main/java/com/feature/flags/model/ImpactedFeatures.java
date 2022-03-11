package com.feature.flags.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ImpactedFeatures {

    @Id
    String id;
    String featureFlagName;
    String impactedFeature;

    public ImpactedFeatures() {
    }

    public ImpactedFeatures(String featureFlagName,
                            String impactedFeature) {
        this.id = featureFlagName + "::" + impactedFeature;
        this.featureFlagName = featureFlagName;
        this.impactedFeature = impactedFeature;
    }

    public String getId() {
        return id;
    }

    public String getFeatureFlagName() {
        return featureFlagName;
    }

    public String getImpactedFeature() {
        return impactedFeature;
    }
}
