package com.feature.flags.dao.repository;

import com.feature.flags.model.ImpactedModules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImpactedModulesRepository extends JpaRepository<ImpactedModules, String> {

    List<ImpactedModules> findByImpactedModule(String module);
}
