package com.feature.flags.dao.repository;

import com.feature.flags.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {

    Optional<Users> findByRole(String role);

    Optional<Users> findByOrg(String org);
}
