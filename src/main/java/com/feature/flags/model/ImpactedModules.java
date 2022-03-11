package com.feature.flags.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ImpactedModules {

    @Id
    String id;
    String featureFlagName;
    String impactedModule;

    public ImpactedModules() {
    }

    public ImpactedModules(String featureFlagName,
                           String impactedModule) {
        this.id = featureFlagName + "::" + impactedModule;
        this.featureFlagName = featureFlagName;
        this.impactedModule = impactedModule;
    }

    public String getId() {
        return id;
    }

    public String getFeatureFlagName() {
        return featureFlagName;
    }

    public String getImpactedModule() {
        return impactedModule;
    }
}
