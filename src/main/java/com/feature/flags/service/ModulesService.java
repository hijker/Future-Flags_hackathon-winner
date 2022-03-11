package com.feature.flags.service;

import com.feature.flags.dao.service.ModulesDaoService;
import com.feature.flags.model.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModulesService {

    @Autowired
    ModulesDaoService modulesDaoService;

    public void insertTag(Modules module) {
        modulesDaoService.insertModule(module);
    }

    public List<Modules> getAll() {
        return modulesDaoService.getByAll();
    }

}
