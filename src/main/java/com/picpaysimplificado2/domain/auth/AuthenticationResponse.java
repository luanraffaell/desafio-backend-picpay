package com.picpaysimplificado2.domain.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
