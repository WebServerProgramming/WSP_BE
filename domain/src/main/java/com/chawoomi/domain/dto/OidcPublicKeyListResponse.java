package com.chawoomi.domain.dto;

import java.util.List;

public record OidcPublicKeyListResponse(
        List<OidcPublicKeyResponse> keys
) {
}
