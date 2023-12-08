package com.ybe.tr1ll1on.domain.likes.dto.response;

import com.ybe.tr1ll1on.domain.likes.model.Likes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class LikeMessageResponse {
    @Schema(example = "1")
    private Long likeId;

    @Schema(example = "좋아요 성공")
    private String message;

    @Schema(example = "18")
    private Long accommodationId;

    public static LikeMessageResponse fromEntity(Likes likes, String message) {
        return LikeMessageResponse.builder()
                .likeId(likes.getId())
                .message(message)
                .accommodationId(likes.getAccommodation().getId())
                .build();
    }
}
