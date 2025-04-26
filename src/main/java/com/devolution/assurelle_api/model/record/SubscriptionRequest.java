package com.devolution.assurelle_api.model.record;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

public record SubscriptionRequest(
    @Schema(example = "1",description = "ID du devis préalablement calculé et qui sera transformé en souscription", required = true)
    long quoteId,
    @Schema( description = "Datee de mise en service du vehicule", required = true)
    Date vehicleDate,
    @Schema(example = "ROUGE", description = "Couleur du vehicule", required = true)
    String vehicleColor,
    @Schema(example = "4567CI09", description = "Nunero d'immatriculation du vehicule", required = true)
    String vehicleRegNumber,
    @Schema(example = "5", description = "Nombre de siège du vehicule", required = true)
    int vehicleSeatsCount,
    @Schema(example = "4", description = "Nombre de portières du vehicule", required = true)
    int vehicleDoorsCount,
    @Schema(example = "201", description = "ID de la catégorrie du vehicule", required = true)
    long vehicleCategoryId,
    @Schema(example = "COCODY ABIDJAN", description = "Adresse du souscripteur", required = true)
    String subscriberAddress,
    @Schema(example = "00 00 90 89 78", description = "Telephone du souscripteur", required = true)
    String subscriberPhone,
    @Schema(example = "AKA", description = "Nom du souscripteur", required = true)
    String subscriberLastName,
    @Schema(example = "STEPHANE ELVIS", description = "Prénoms du souscripteur", required = true)
    String subscriberFirstName,
    @Schema(example = "CNI000001", description = "Numéro de la pièce d'identite du souscripteur", required = true)
    String subscriberIdCardNumber,
    @Schema(example = "ABIDJAN", description = "Ville du souscripteur", required = true)
    String subscriberCity
    ) {}