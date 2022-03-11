package com.feature.flags.model;

import java.util.Date;

public class StatusResponse {

    private String name;

    private Boolean value;

    private String summary;

    private Date updated_At;

    private String owner_module;

    private FeatureFlagLevel level;

    public StatusResponse() {
    }

    public StatusResponse(String name, Boolean value, String summary, Date updated_At, String owner_module, FeatureFlagLevel level) {
        this.name = name;
        this.value = value;
        this.summary = summary;
        this.updated_At = updated_At;
        this.owner_module = owner_module;
        this.level = level;
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
        return updated_At;
    }

    public String getOwnerModule() {
        return owner_module;
    }

    public FeatureFlagLevel getLevel() {
        return level;
    }
}
