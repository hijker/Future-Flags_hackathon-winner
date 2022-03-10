package com.feature.flags.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Tags {

    @Id
    String id;

    public Tags() {
    }

    public Tags(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
