package com.ybe.tr1ll1on.security.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenDto {
   private String grantType;
   private String accessToken;
   private Long accessTokenExpiresIn;
   private String refreshToken;
}
