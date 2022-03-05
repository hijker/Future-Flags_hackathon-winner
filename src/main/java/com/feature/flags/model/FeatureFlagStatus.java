package com.feature.flags.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class FeatureFlagStatus {

    @Id
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_flag.name")
    FeatureFlag flag;
    Boolean value;
    @Enumerated(EnumType.STRING)
    FeatureFlagLevel level;
    String levelValue;

    public FeatureFlagStatus() {
    }

    public FeatureFlagStatus(FeatureFlag flag,
                             Boolean value,
                             FeatureFlagLevel level,
                             String levelValue) {
        this.id = String.join(":", flag.getName(), level.name(), levelValue);
        this.flag = flag;
        this.value = value;
        this.level = level;
        this.levelValue = levelValue;
    }

    public FeatureFlag getFlag() {
        return flag;
    }

    public Boolean getValue() {
        return value;
    }

    public FeatureFlagLevel getLevel() {
        return level;
    }

    public String getLevelValue() {
        return levelValue;
    }
}
