package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.ModulesRepository;
import com.feature.flags.model.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModulesDaoService {

    @Autowired
    ModulesRepository modulesRepository;

    public ModulesDaoService(ModulesRepository modulesRepository) {
        this.modulesRepository = modulesRepository;
    }

    public void insertModule(Modules module) {
        modulesRepository.save(module);
    }

    public List<Modules> getByAll() {
        List<Modules> list = new ArrayList<>();
        modulesRepository.findAll().forEach(list::add);
        return list;
    }

    public void deleteAll() {
        modulesRepository.deleteAll();
    }
}
