package com.ybe.tr1ll1on.domain.review.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ybe.tr1ll1on.domain.accommodation.model.QAccommodation;
import com.ybe.tr1ll1on.domain.product.model.QProduct;
import com.ybe.tr1ll1on.domain.review.model.QReview;
import com.ybe.tr1ll1on.domain.user.model.QUser;
import com.ybe.tr1ll1on.global.util.SortingInfo;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import com.ybe.tr1ll1on.domain.review.model.Review;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public ReviewRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Slice<Review> getProductAllReviews(
            Long accommodationId,
            Double cursorScore, LocalDate cursorReviewDate, Long cursorReviewId,
            Pageable pageable
    ) {

        QReview review = QReview.review;
        QUser user = QUser.user;
        QProduct product = QProduct.product;
        QAccommodation accommodation = QAccommodation.accommodation;

        SortingInfo sortingInfo = getSortingInfo(pageable);

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(review.product.accommodation.id.eq(accommodationId));

        if ("score".equals(sortingInfo.getProperty()) && cursorScore != null) {
            predicate.and(
                    compareScore(review, cursorScore, sortingInfo.isDescending())
                            .or(reviewScoreAndReviewIdCondition(review, cursorScore, cursorReviewId))
            );
        }

        if ("reviewDate".equals(sortingInfo.getProperty()) && cursorReviewDate != null) {
            predicate.and(
                    compareReviewDate(review, cursorReviewDate)
                            .or(reviewDateAndReviewIdCondition(review, cursorReviewDate, cursorReviewId))
            );
        }

        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(sortingInfo, review);

        List<Review> content = jpaQueryFactory
                .selectFrom(review)
                .leftJoin(review.user, user).fetchJoin()
                .leftJoin(review.product, product).fetchJoin()
                .leftJoin(product.accommodation, accommodation).fetchJoin()
                .where(predicate)
                .orderBy(orderSpecifiers)
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) {
            content.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression reviewScoreAndReviewIdCondition(QReview review, Double cursorScore, Long cursorReviewId) {
        return review.score.eq(cursorScore).and(review.id.gt(cursorReviewId));
    }

    private BooleanExpression reviewDateAndReviewIdCondition(QReview review, LocalDate cursorReviewDate, Long cursorReviewId) {
        return review.reviewDate.eq(cursorReviewDate).and(review.id.gt(cursorReviewId));
    }

    private BooleanExpression compareScore(QReview review, Double cursorScore, boolean isDescending) {
        return isDescending ? review.score.lt(cursorScore) : review.score.gt(cursorScore);
    }

    private BooleanExpression compareReviewDate(QReview review, LocalDate cursorReviewDate) {
        return review.reviewDate.lt(cursorReviewDate);
    }

    private OrderSpecifier[] createOrderSpecifier(SortingInfo sortingInfo, QReview review) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if ("score".equals(sortingInfo.getProperty())) {
            orderSpecifiers.add(new OrderSpecifier(sortingInfo.isDescending() ? Order.DESC : Order.ASC, review.score));
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, review.id));
        }

        if ("reviewDate".equals(sortingInfo.getProperty())) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.reviewDate));
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, review.id));
        }

        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

    public static SortingInfo getSortingInfo(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            Iterator<Sort.Order> iterator = pageable.getSort().iterator();
            if (iterator.hasNext()) {
                Sort.Order order = iterator.next();
                return new SortingInfo(order.getProperty(), order.isDescending());
            }
        }
        return new SortingInfo(null, false);
    }
}
