package com.devolution.assurelle_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.devolution.assurelle_api.model.entity.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    List<Subscription> findByStatus(int status);
    
    @Query("SELECT s FROM Subscription s WHERE s.status = 0")
    List<Subscription> simulationOnly();


    @Query("SELECT s FROM Subscription s WHERE s.status > 0")
    List<Subscription> subscriptionsOnly();


}
