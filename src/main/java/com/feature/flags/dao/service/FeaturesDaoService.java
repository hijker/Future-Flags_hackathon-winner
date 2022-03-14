package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.FeaturesRepository;
import com.feature.flags.model.Features;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeaturesDaoService {

    @Autowired
    FeaturesRepository featuresRepository;

    public FeaturesDaoService(FeaturesRepository featuresRepository) {
        this.featuresRepository = featuresRepository;
    }

    public void insertTag(Features tag) {
        featuresRepository.save(tag);
    }

    public List<Features> getByAll() {
        List<Features> list = new ArrayList<>();
        featuresRepository.findAll().forEach(list::add);
        return list;
    }

    public void deleteAll() {
        featuresRepository.deleteAll();
    }
}
