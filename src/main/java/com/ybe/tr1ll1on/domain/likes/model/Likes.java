package com.ybe.tr1ll1on.domain.likes.model;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.user.model.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes {

    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

