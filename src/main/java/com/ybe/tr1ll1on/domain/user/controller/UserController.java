package com.ybe.tr1ll1on.domain.user.controller;

import com.ybe.tr1ll1on.domain.user.dto.response.MyPageDetailResponse;
import com.ybe.tr1ll1on.domain.user.dto.response.MyPageListResponse;
import com.ybe.tr1ll1on.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<MyPageListResponse>> getMyPage() {
        List<MyPageListResponse> myPageListResponse = userService.getMyPage();
        return ResponseEntity.ok(myPageListResponse);
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<MyPageDetailResponse> getMyPageDetails(@PathVariable Long orderId) {
        MyPageDetailResponse myPageDetailResponse = userService.getMyPageDetails(orderId);
        return ResponseEntity.ok(myPageDetailResponse);
    }
}