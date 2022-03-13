package com.feature.flags.model;

import java.util.Date;

public class StatusResponse {

    private String name;

    private Boolean value;

    private String summary;

    private Date updatedAt;

    private String ownerModule;

    private FeatureFlagLevel level;

    private String impactedModules;

    private String impactedFeatures;

    private Boolean needsConfirmation;

    public StatusResponse() {
    }

    public StatusResponse(String name,
                          Boolean value,
                          String summary,
                          Date updatedAt,
                          String ownerModule,
                          FeatureFlagLevel level,
                          String impactedModules,
                          String impactedFeatures,
                          Boolean needsConfirmation) {
        this.name = name;
        this.value = value;
        this.summary = summary;
        this.updatedAt = updatedAt;
        this.ownerModule = ownerModule;
        this.level = level;
        this.impactedModules = impactedModules;
        this.impactedFeatures = impactedFeatures;
        this.needsConfirmation = needsConfirmation;
    }

    public String getName() {
        return name;
    }

    public Boolean getValue() {
        return value;
    }

    public String getSummary() {
        return summary;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getOwnerModule() {
        return ownerModule;
    }

    public FeatureFlagLevel getLevel() {
        return level;
    }

    public String getImpactedFeatures() {
        return impactedFeatures;
    }

    public String getImpactedModules() {
        return impactedModules;
    }

    public Boolean getNeedsConfirmation() {
        return needsConfirmation;
    }
}
