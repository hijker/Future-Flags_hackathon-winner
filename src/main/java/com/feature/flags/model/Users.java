package com.feature.flags.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Users {

    @Id
    String id;
    String email;
    String role;
    String roleId;
    String org;
    String domain;
    String name;

    public Users() {
    }

    public Users(String id,
                 String email,
                 String role,
                 String roleId,
                 String org,
                 String domain,
                 String name) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.roleId = roleId;
        this.org = org;
        this.domain = domain;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getRoleId() {
        return roleId;
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

    public String getName() {
        return name;
    }
}
