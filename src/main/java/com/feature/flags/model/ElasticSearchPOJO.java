package com.feature.flags.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "elastic_index")
public class ElasticSearchPOJO {

    @Id
    String key;

    String value;

    String type;


    public ElasticSearchPOJO() {
    }

    public ElasticSearchPOJO(String key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
