package com.ybe.tr1ll1on.domain.cart.model;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class CartItem {
    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("체크인 날짜")
    @Column(name = "start_date")
    private String startDate;

    @Comment("체크아웃 날짜")
    @Column(name = "end_date")
    private String endDate;

    @Column(name = "person_number")
    private Integer personNumber;

    private Integer price;

    @Comment("장바구니id")
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product_id")
    private Product product;


}
