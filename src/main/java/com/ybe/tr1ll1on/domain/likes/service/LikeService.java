package com.ybe.tr1ll1on.domain.likes.service;

import com.ybe.tr1ll1on.domain.likes.dto.response.LikeMessageResponse;
import com.ybe.tr1ll1on.domain.likes.dto.response.LikeResponse;
import com.ybe.tr1ll1on.domain.likes.model.Likes;
import com.ybe.tr1ll1on.domain.user.model.User;
import java.util.List;

public interface LikeService {

    LikeMessageResponse toggleLike(Long accommodationId);

}

