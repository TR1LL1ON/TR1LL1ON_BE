package com.ybe.tr1ll1on.domain.likes.controller;

import com.ybe.tr1ll1on.domain.likes.dto.response.LikeMessageResponse;
import com.ybe.tr1ll1on.domain.likes.dto.response.LikeResponse;
import com.ybe.tr1ll1on.domain.likes.service.LikeService;
import com.ybe.tr1ll1on.domain.order.dto.request.OrderRequest;
import com.ybe.tr1ll1on.domain.order.dto.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "좋아요(찜) API", description = "좋아요(찜) 관련 API 모음입니다.")

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "찜 누르기 API", description = "찜 누르기 API 입니다.")
    @ApiResponse(responseCode = "201", description = "찜 성공시",
            content = @Content(schema = @Schema(implementation = LikeResponse.class)))
    @SecurityRequirement(name = "jwt")
    @PostMapping("/{accommodationId}")
    public ResponseEntity<LikeMessageResponse> toggleLike(
            @PathVariable Long accommodationId
    ) {
        return ResponseEntity.ok(likeService.toggleLike(accommodationId));
    }

}

