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

    @Schema(example = "1")
    private Long id;

    @Schema(example = "유저 이메일")
    private String email;

    @Schema(example = "유저 이름")
    private String name;
}
