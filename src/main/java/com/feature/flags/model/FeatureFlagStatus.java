package com.feature.flags.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class FeatureFlagStatus {

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="feature_flag.name")
    FeatureFlag flag;
    Boolean value;
    Long userId;
    Integer orgId;
    String role;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    public FeatureFlagStatus() {
    }

    public FeatureFlagStatus(FeatureFlag flag,
                             Boolean value,
                             Long userId,
                             Integer orgId,
                             String role) {
        this.flag = flag;
        this.value = value;
        this.userId = userId;
        this.orgId = orgId;
        this.role = role;
    }

    public FeatureFlag getFlag() {
        return flag;
    }

    public Boolean getValue() {
        return value;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public String getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
