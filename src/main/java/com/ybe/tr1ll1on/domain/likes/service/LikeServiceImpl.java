package com.ybe.tr1ll1on.domain.likes.service;

import static com.ybe.tr1ll1on.domain.accommodation.exception.AccommodationExceptionCode.ACCOMMODATION_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.user.exception.InValidUserExceptionCode.USER_NOT_FOUND;

import com.ybe.tr1ll1on.domain.accommodation.exception.AccommodationException;
import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
import com.ybe.tr1ll1on.domain.likes.dto.response.LikeMessageResponse;
import com.ybe.tr1ll1on.domain.likes.exception.LikeException;
import com.ybe.tr1ll1on.domain.likes.exception.LikeExceptionCode;
import com.ybe.tr1ll1on.domain.likes.model.Likes;
import com.ybe.tr1ll1on.domain.likes.repository.LikeRepository;
import com.ybe.tr1ll1on.domain.user.exception.InValidUserException;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final AccommodationRepository accommodationRepository;

    @Transactional
    public LikeMessageResponse toggleLike(Long accommodationId) {
        User user = getUser();

        Optional<Likes> optionalLikes = likeRepository.findByUserIdAndAccommodationId(
                user.getId(), accommodationId
        );

        Likes likes;
        if (optionalLikes.isEmpty()) {  // 좋아요가 없다 => 새로운 좋아요 생성

            likes = Likes.builder()
                    .user(user)
                    .accommodation(getAccommodation(accommodationId))
                    .createdAt(LocalDateTime.now())
                    .build();

            Likes savedLike = likeRepository.save(likes);
            if (savedLike == null) {
                throw new LikeException(LikeExceptionCode.FAILED_LIKE);
            }

//            log.info("찜 목록 추가! 숙소 ID : {}, LikeId : {}", accommodationId, savedLike.getId());
            return LikeMessageResponse.fromEntity(savedLike, "찜 목록에 추가하였습니다.");

        } else {  // 좋아요가 있다 => 좋아요 취소

            likes = optionalLikes.get();
            likeRepository.delete(likes);

//            log.info("찜 목록 제거! 숙소 ID : {}, LikeId : {}", accommodationId, likes.getId());
            return LikeMessageResponse.fromEntity(likes, "찜 목록에 제거하였습니다.");
        }

    }

    private Accommodation getAccommodation(Long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new AccommodationException(ACCOMMODATION_NOT_FOUND));
    }



    private User getUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new InValidUserException(USER_NOT_FOUND));
    }
}
