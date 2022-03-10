package com.feature.flags.service;

import com.feature.flags.dao.service.TagsDaoService;
import com.feature.flags.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    @Autowired
    TagsDaoService tagsDaoService;

    public void insertTag(Tags tag) {
        tagsDaoService.insertTag(tag);
    }

    public List<Tags> getAll() {
        return tagsDaoService.getByAll();
    }

}
