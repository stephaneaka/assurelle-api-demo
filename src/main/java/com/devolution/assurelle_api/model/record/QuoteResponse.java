package com.devolution.assurelle_api.model.record;

import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuoteResponse(
    @Schema(description = "La reférence du devis calculé")
    String quoteReference,
    @Schema(description = "La date du calcul du devis")
    LocalDateTime quoteDate,
    @Schema(description = "La date d'expiration du devis calculé")
    LocalDateTime expireDate,
    @Schema(description  = "Le nom du produit sélectionné")
    String productNane,
    @Schema(description = "Le montant total des primes")
    double price,
    @Schema(description = "Les détails des primes")
    Map<String, Double> quoteDetails,
    @Schema(description = "Les données de calcul")
    QuoteRequest inputValues) {}