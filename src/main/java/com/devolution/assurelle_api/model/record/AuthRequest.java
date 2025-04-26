package com.devolution.assurelle_api.model.record;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthRequest(
    @Schema(example = "user@devolution.com", required = true)
    String email, 
    @Schema(example = "password", required = true)
    String password) {
}
