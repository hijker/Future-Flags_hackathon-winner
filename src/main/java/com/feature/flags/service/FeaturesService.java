package com.feature.flags.service;

import com.feature.flags.dao.service.FeaturesDaoService;
import com.feature.flags.model.Features;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeaturesService {

    @Autowired
    FeaturesDaoService featuresDaoService;

    public void insertFeature(Features features) {
        featuresDaoService.insertTag(features);
    }

    public List<Features> getAll() {
        return featuresDaoService.getByAll();
    }

    public void deleteAll() {
        featuresDaoService.deleteAll();
    }
}
