package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.TagsRepository;
import com.feature.flags.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsDaoService {

    @Autowired
    TagsRepository tagsRepository;

    public TagsDaoService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public void insertTag(Tags tag) {
        tagsRepository.save(tag);
    }

    public List<Tags> getByAll() {
        return tagsRepository.findAll();
    }

}
