package com.feature.flags.model;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Document(indexName = "tags")
public class Features {

    @Id
    String id;

    public Features() {
    }

    public Features(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
