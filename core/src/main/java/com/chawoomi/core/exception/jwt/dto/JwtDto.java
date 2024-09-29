package com.chawoomi.core.exception.jwt.dto;

public record JwtDto(
	String accessToken,
	String refreshToken
) {
}
