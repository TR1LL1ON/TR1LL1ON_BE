package com.ybe.tr1ll1on.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "test@mail.com")
    private String email;

    @Schema(example = "test1")
    private String name;

    @Schema(example = "a12345679123456789")
    private String accessToken;
}
