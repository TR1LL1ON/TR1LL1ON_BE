package com.ybe.tr1ll1on.domain.user.dto.response;

import com.ybe.tr1ll1on.security.dto.TokenInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private UserDetailsResponse userDetails;

    private TokenInfo tokenInfo;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserDetailsResponse {
        private Long userId;
        private String userEmail;
        private String userName;
    }
}