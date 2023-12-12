package com.ybe.tr1ll1on.domain.likes.dto.response;

import com.ybe.tr1ll1on.domain.likes.model.Likes;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class LikeResponse {
    @Schema(example = "1")
    private Long likeId;

    @Schema(example = "18")
    private Long accommodationId;

    @Schema(example = "해와달수상낚시빌리지")
    private String accommodationName;

    @Schema(example = "경기도 포천시 관인면 냉정리")
    private String accommodationAddress;

    @Schema(example = "B02010700")
    private String accommodationCategory;

    @Schema(example = "http://tong.visitkorea.or.kr/cms/resource/50/2705650_image2_1.jpg")
    private String imageUrl;

    @Schema(example = "2023년 12월 09일 01시 23분")
    private String createdAt;

    public static LikeResponse fromEntity(Likes likes) {
        return LikeResponse.builder()
                .likeId(likes.getId())
                .accommodationId(likes.getAccommodation().getId())
                .accommodationName(likes.getAccommodation().getName())
                .accommodationAddress(likes.getAccommodation().getAddress())
                .accommodationCategory(likes.getAccommodation().getCategory().getCategoryCode())
                .imageUrl(likes.getAccommodation().getAccommodationImageList().get(0).getImageUrl())
                .createdAt(likes.getCreatedAt().format(
                        DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")
                ))
                .build();
    }
}

