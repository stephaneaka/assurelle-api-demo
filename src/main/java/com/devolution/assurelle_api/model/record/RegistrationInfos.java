package com.devolution.assurelle_api.model.record;

public record RegistrationInfos(
    String email, 
    boolean isAdmin,
    String password) {
    }