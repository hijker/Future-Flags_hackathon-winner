package com.feature.flags.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
public class FeatureFlag {

    @Id
    private String name;                    //Name of the flag
    private String summary;                 //Summary- 1 liner
    private String description;             //Description
    private String ownerModule;             //Owner modules-
    private String maxGranularity;          //Applicable on level from
    private String training;                //link to enablement training
    private String type;                    //What kind of ff is this? Partial roll out due to monetization of a feature | Slow roll out for the risk/impact it can cause/meant for smaller audience
    private Boolean needsConfirmation;      //isHighImpact: needs confirmation eg: useActivity(like system level might need approval)
    private String deprecationFlow;         //Deprecation flow: system level or post code change
    private String reasonForIntroduction;   //Reason for creating ff
    private Date createdAt;
    private Date updatedAt;
    private String createdById;

    //To be moved to new table
//    List<String> parentFlags; //Pre-requisite ff- give a warning, one level | no option to enable block going further(disable the toggle)
//    List<String> childFlags; //FF that depends on this
//    List<String> modulesImpacted; //Modules impacted as tags
//    List<String> featuresImpacted; //Impacts features- as tags

    //To be moved to new table
//    Date releasedOn; //Launched on: released to production
//    Date firstEnabledOn; //First enabled on
//    Float rollOutPercentage; //Roll-out percentage
//    List<Integer> orgsUsing; //Which all customers are using it


    public FeatureFlag() {
    }

    public FeatureFlag(String name,
                       String summary,
                       String description,
                       String ownerModule,
                       String maxGranularity,
                       String training,
                       String type,
                       Boolean needsConfirmation,
                       String deprecationFlow,
                       String reasonForIntroduction,
                       Date createdAt,
                       Date updatedAt,
                       String createdById) {
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.ownerModule = ownerModule;
        this.maxGranularity = maxGranularity;
        this.training = training;
        this.type = type;
        this.needsConfirmation = needsConfirmation;
        this.deprecationFlow = deprecationFlow;
        this.reasonForIntroduction = reasonForIntroduction;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdById = createdById;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerModule() {
        return ownerModule;
    }

    public String getMaxGranularity() {
        return maxGranularity;
    }

    public String getTraining() {
        return training;
    }

    public String getType() {
        return type;
    }

    public Boolean getNeedsConfirmation() {
        return needsConfirmation;
    }

    public String getDeprecationFlow() {
        return deprecationFlow;
    }

    public String getReasonForIntroduction() {
        return reasonForIntroduction;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedById() {
        return createdById;
    }

    @Override
    public String toString() {
        return "FeatureFlag{" +
                "name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", ownerModule='" + ownerModule + '\'' +
                ", maxGranularity='" + maxGranularity + '\'' +
                ", training='" + training + '\'' +
                ", type='" + type + '\'' +
                ", needsConfirmation=" + needsConfirmation +
                ", deprecationFlow='" + deprecationFlow + '\'' +
                ", reasonForIntroduction='" + reasonForIntroduction + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdById=" + createdById +
                '}';
    }
}
