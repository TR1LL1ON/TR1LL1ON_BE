package com.ybe.tr1ll1on.domain.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_facility")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_facility_id")

    private Long id;
    private Boolean cooking;
    private Boolean parking;
    private Boolean sports;
    private Boolean sauna;
    private Boolean beauty;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
