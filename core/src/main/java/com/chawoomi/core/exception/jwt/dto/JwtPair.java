package com.chawoomi.core.exception.jwt.dto;

public record JwtPair(
	String accessToken,
	String refreshToken
) {
}
