package com.feature.flags.service;

import com.feature.flags.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    @Autowired
    TagsService tagsService;

    public void insertTag(Tags tag) {
        tagsService.insertTag(tag);
    }

    public List<Tags> getAll() {
        return tagsService.getAll();
    }

}
