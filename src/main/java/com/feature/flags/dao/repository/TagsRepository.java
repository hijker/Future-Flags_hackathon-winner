package com.feature.flags.dao.repository;

import com.feature.flags.model.Tags;
import com.feature.flags.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagsRepository extends JpaRepository<Tags, String> {

}
