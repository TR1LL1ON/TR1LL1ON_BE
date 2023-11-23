package com.ybe.tr1ll1on.domain.accommodation.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_code")
    private String categoryCode;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Accommodation> accommodations;

    @Builder
    public Category(String categoryCode){
        this.categoryCode = categoryCode;
    }
}
