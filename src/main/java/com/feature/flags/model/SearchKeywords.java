package com.feature.flags.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "search_keys")
public class SearchKeywords {

    @Id
    String id;

    String key;

    String type;


    public SearchKeywords() {
    }

    public SearchKeywords(String key, String type) {
        this.id = key + "::" + type;
        this.key = key;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }
}
