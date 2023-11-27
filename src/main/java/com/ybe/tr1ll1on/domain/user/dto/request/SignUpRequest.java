package com.ybe.tr1ll1on.domain.user.dto.request;

import com.ybe.tr1ll1on.domain.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ybe.tr1ll1on.security.common.Authority.ROLE_USER;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .authority(ROLE_USER)
                .build();
    }
}
