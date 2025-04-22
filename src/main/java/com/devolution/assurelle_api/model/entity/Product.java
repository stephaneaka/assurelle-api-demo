package com.devolution.assurelle_api.model.entity;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String name;
    private String description;
    @ManyToAny(fetch = FetchType.EAGER)
    private List<Guarantee> guarantees;
    @ManyToAny(fetch = FetchType.EAGER)
    private List<VehicleCategory> vehicleCategories;
}
