package com.bhaawan.companionMatcher.repository;

import com.bhaawan.companionMatcher.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,String> {
    Optional<UserModel> findByNameIgnoreCase(String name);

}
