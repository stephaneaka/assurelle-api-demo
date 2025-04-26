package com.devolution.assurelle_api.model.record;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuoteRequest(
    @Schema(description = "ID du Produit sélectionné pour la simulation", example = "1", required = true)
    long productId,
    @Schema(description = "Puissance fiscale du vehicule en cv", example = "1", required = true)
    int vehicleFiscalPower,
    @Schema(description = "Valeur a neuf du vehicule", example = "8000000", required = true)
    double vehicleOriginalCost,
    @Schema(description = "Valeur vénale du vehicule", example = "5000000", required = true)
    double vehicleMarketCost,
    @Schema(description = "Indique si OUI ou NON, le devis doit être enregistré apres le calcul", example = "true", required = true)
    boolean save) {}

