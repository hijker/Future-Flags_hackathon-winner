package com.feature.flags.service;

import com.feature.flags.dao.service.ImpactedModuleDaoService;
import com.feature.flags.model.ImpactedModules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpactedModuleService {

    @Autowired
    ImpactedModuleDaoService impactedModuleDaoService;

    public void insertImpactedModule(ImpactedModules module) {
        impactedModuleDaoService.insertImpactedModule(module);
    }

    public List<ImpactedModules> getByModule(String module) {
        return impactedModuleDaoService.getByModule(module);
    }
}
