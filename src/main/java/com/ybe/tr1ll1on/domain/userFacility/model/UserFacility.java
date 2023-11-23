package com.ybe.tr1ll1on.domain.userFacility.model;

import com.ybe.tr1ll1on.domain.user.model.User;
import jakarta.persistence.*;

@Entity
public class UserFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_facility_id")
    private Long id;
    @Column(name = "swimming_pool")
    private Boolean isSwimmingPool;

    @Column(name = "breakfast")
    private Boolean isBreakfast;

    @Column(name = "party")
    private Boolean isParty;

    @Column(name = "parking")
    private Boolean isParking;
    @Column(name = "cooking")
    private Boolean isCooking;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
