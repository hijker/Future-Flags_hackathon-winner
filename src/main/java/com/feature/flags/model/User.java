package com.feature.flags.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User {

    @Id
    String id;
    String email;
    String role;
    String org;
    String domain;

    public User() {
    }

    public User(String id,
                String email,
                String role,
                String org,
                String domain) {
        this.id = id;
        this.role = role;
        this.org = org;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getOrg() {
        return org;
    }

    public String getEmail() {
        return email;
    }

    public String getDomain() {
        return domain;
    }
}
