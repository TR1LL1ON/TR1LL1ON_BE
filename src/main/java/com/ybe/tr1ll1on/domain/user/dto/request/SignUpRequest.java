package com.ybe.tr1ll1on.domain.user.dto.request;

import com.ybe.tr1ll1on.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "test@eamil.com")
    private String email;
    @NotBlank
    @Schema(example = "테스트")
    private String name;
    @NotBlank
    @Schema(example = "1234")
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
