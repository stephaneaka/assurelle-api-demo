package com.devolution.assurelle_api.model.record;

import java.util.Date;

public record AuthResponse(
    int code,
    String message,
    String token, 
    Date expire) {
}
