package com.devolution.assurelle_api.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private long productId ;
    private String productName;
    private LocalDateTime quoteDate ;
    private String quoteReference;
    private LocalDateTime subscriptDate;
    @ManyToOne(fetch =FetchType.EAGER, cascade = CascadeType.ALL)
    private Subscriber subscriber;
    @OneToOne(fetch =FetchType.EAGER, cascade = CascadeType.ALL)
    private Vehicle vehicle;

    private long creatorId ;
    private String creatorName ;
    private int status = 0;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Charge> charges = new ArrayList<>();
}
