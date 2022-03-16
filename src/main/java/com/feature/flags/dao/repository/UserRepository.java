package com.feature.flags.dao.repository;

import com.feature.flags.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {

    List<Users> findByRole(String role);

    List<Users> findByOrg(String org);
}
