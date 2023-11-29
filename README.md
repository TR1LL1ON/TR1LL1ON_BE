# 개요

■ 프로젝트명

- TR1ll1on

■ 기간, 참여인원

- 11/20(월) ~ 12/01(금)

■ 목적

- 숙박 예약 서비스를 완성 및 협업

<br/>

# 아키텍처 패턴

- MVC

<br/>

# 구현 환경

- Java 17
- Spring Boot
- Spring security
- gradle
- aws

<br/>

# Git

- Git-Flow 사용

<br/>

# Commit log

```
Feat : 새로운 기능 추가
Fix : 버그 수정
Style : 코드 스타일 수정 (세미 콜론, 인덴트 등의 스타일적인 부분만)
Refactor : 코드 리팩토링 (더 효율적인 코드로 변경 등)
Design : CSS 등 디자인 추가/수정
Comment : 주석 추가/수정
Docs : 내부 문서 추가/수정
Test : 테스트 추가/수정
Chore : 빌드 관련 코드 수정, 개발 환경 관련 설정(과거 Env)
Move : 파일 및 폴더명 수정(과거 Rename)
Remove : 파일 삭제
```
<br/>

# Branch Naming
feature/#이슈번호_간단 설명(주제)

<br/>

# ERD

![트릴리언](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/d3b5082e-9ab3-46c4-90da-fb3db2d181a9)

<br/>

# API

■ User

- 회원가입

```
http://localhost:8080/auth/signup
```

- 로그인

```
http://localhost:8080/auth/login
```

- 로그아웃

```
http://localhost:8080/auth/logout
```

- 마이페이지

```
http://localhost:8080/user
```

- 마이페이지 - 주문내역 상세

```
http://localhost:8080/user/details/{orderId}
```

■ 숙소

- 전체 숙소 조회

```
http://localhost:8080/products
```

- 개인화 용 숙소 조회

```
http://localhost:8080/products
```

- 카테고리 별 숙소 조회

```
http://localhost:8080/products?category={카테고리 코드}
```

- 지역별 숙소 조회

```
http://localhost:8080/products?areacode={지역코드}
```

- 지역별 & 카테고리 별 숙소 조회

```
http://localhost:8080/products?category={카테고리 코드}&region={지역코드}
```

■ 상품

- 개별 상품 조회

```
http://localhost:8080/products/{accommodation_id}
```

- 개별 상품 상세페이지 조회

```
http://localhost:8080/products/{accommodation_id}/{product_id}
```

- 상품 주문하기

```
http://localhost:8080/orders
```

■ 장바구니

- 장바구니 상품 전체 조회

```
http://localhost:8080/carts
```

- 장바구니에 상품 추가

```
http://localhost:8080/carts/{product_id}
```

- 장바구니에 상품 삭제

```
http://localhost:8080/carts/{cartItem_id}
```

■ 리뷰

- 내 리뷰 조회

```
http://localhost:8080/reviews
```

- 리뷰 작성

```
http://localhost:8080/reviews
```

- 리뷰 수정

```
http://localhost:8080/reviews/{review_id}
```

- 리뷰 삭제

```
http://localhost:8080/reviews/{review_id}
```




