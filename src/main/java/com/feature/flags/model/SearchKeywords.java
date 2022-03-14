package com.feature.flags.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "search_keys")
public class SearchKeywords {

    @Id
    String id;

    String key;

    String value;

    String type;

    public SearchKeywords() {
    }

    public SearchKeywords(String key, String type, String value) {
        this.id = key + "::" + type;
        this.key = key;
        this.type = type;
        this.value = value;
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

    public String getValue() {
        return value;
    }
}
