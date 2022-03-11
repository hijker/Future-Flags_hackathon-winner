package com.feature.flags.dao.service;

import com.feature.flags.dao.repository.ImpactedModulesRepository;
import com.feature.flags.model.ImpactedModules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpactedModuleDaoService {

    @Autowired
    ImpactedModulesRepository impactedModulesRepository;

    public ImpactedModuleDaoService(ImpactedModulesRepository impactedModulesRepository) {
        this.impactedModulesRepository = impactedModulesRepository;
    }

    public void insertImpactedModule(ImpactedModules module) {
        impactedModulesRepository.save(module);
    }

    public List<ImpactedModules> getByModule(String module) {
        return impactedModulesRepository.findByImpactedModule(module);
    }

    public void deleteByName(String name) {
        impactedModulesRepository.deleteByFeatureFlagName(name);
    }
}
