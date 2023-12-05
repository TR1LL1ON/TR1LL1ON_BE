package com.ybe.tr1ll1on.domain.accommodation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_code")
    private String categoryCode;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Accommodation> accommodationList = new ArrayList<>();

    public Category(String categoryCode) {
        this.categoryCode = categoryCode;
    }
}
