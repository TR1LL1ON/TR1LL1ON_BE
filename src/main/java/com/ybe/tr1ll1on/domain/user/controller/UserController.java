package com.ybe.tr1ll1on.domain.user.controller;

import com.ybe.tr1ll1on.domain.likes.dto.response.LikeResponse;
import com.ybe.tr1ll1on.domain.user.dto.response.MyPageDetailResponse;
import com.ybe.tr1ll1on.domain.user.dto.response.MyPageResponse;
import com.ybe.tr1ll1on.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "마이페이지 API", description = "마이페이지 관련 API 모음입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "마이페이지 API", description = "마이페이지 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시",
            content = @Content(schema = @Schema(implementation = MyPageResponse.class)))
    @SecurityRequirement(name = "jwt")
    @GetMapping
    public ResponseEntity<List<MyPageResponse>> getMyPage() {
        List<MyPageResponse> myPageResponse = userService.getMyPage();
        return ResponseEntity.ok(myPageResponse);
    }

    @Operation(summary = "마이페이지 - 주문내역 상세 API", description = "마이페이지에서 주문내역 상세 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시",
            content = @Content(schema = @Schema(implementation = MyPageDetailResponse.class)))
    @SecurityRequirement(name = "jwt")
    @GetMapping("/details/{orderId}")
    public ResponseEntity<MyPageDetailResponse> getMyPageDetails(@PathVariable Long orderId) {
        MyPageDetailResponse myPageDetailResponse = userService.getMyPageDetails(orderId);
        return ResponseEntity.ok(myPageDetailResponse);
    }

    @Operation(summary = "마이페이지 - 숙소 찜 목록", description = "마이페이지에서 찜 목록 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시",
            content = @Content(schema = @Schema(implementation = LikeResponse.class)))
    @SecurityRequirement(name = "jwt")
    @GetMapping("/likes")
    public ResponseEntity<List<LikeResponse>> getLikeList() {
        List<LikeResponse> likeResponseList = userService.getMyLikeList();
        return ResponseEntity.ok(likeResponseList);
    }

}