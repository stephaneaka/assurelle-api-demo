package com.devolution.assurelle_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devolution.assurelle_api.model.entity.Guarantee;

public interface GuaranteeRepository extends JpaRepository<Guarantee,Long> {
    
}
