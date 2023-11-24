package com.ybe.tr1ll1on.domain.user.model;

import com.ybe.tr1ll1on.domain.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
