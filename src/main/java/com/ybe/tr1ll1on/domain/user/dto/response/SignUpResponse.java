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
public class SignUpResponse {

    @Schema(description = "유저 id", defaultValue = "1")
    private Long id;

    @Schema(description = "유저 이메일", defaultValue = "유저 이메일")
    private String email;

    @Schema(description = "유저 이름", defaultValue = "유저 이름")
    private String name;
}
