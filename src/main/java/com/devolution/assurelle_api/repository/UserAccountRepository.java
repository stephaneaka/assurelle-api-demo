package com.devolution.assurelle_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devolution.assurelle_api.model.entity.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
    Optional<UserAccount> findByName(String name);
}
