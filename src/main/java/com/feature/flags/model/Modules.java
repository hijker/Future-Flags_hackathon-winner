package com.feature.flags.model;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Document(indexName = "modules")
public class Modules {

    @Id
    String id;

    public Modules() {
    }

    public Modules(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
