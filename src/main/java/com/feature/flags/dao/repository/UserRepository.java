package com.feature.flags.dao.repository;

import com.feature.flags.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByRole(String role);

    Optional<User> findByOrg(String org);
}
