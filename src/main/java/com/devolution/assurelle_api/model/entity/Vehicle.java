package com.devolution.assurelle_api.model.entity;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long categoryId;
    @Temporal(TemporalType.DATE)
    @Schema(description = "Date de mise en service")
    private Date serviceDate;
    @Schema(description = "Numéro d'immatriculation")
    private String registryNumber;
    @Schema(description = "Couleur")
    private String color;
    @Schema(description = "Nombre de sièges")
    private int seatsCount;
    @Schema(description = "Nombre de portières")
    private int doorsCount;
    @Schema(description = "Puissance fiscale CV")
    private int fiscalPower = 0;
    @Schema(description = "Valeur à neuf")
    private double originalCost =0;
    @Schema(description = "Valeur vénale")
    private double marketCost = 0;

}
