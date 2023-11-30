# 개요

■ 프로젝트명

- TR1ll1on
- https://www.tr1ll1on.site/

■ 기간, 참여인원

- 11/20(월) ~ 12/01(금)

  
- 🐨 김수빈
    - 숙소 조회
- 🦁 서은
    - 장바구니
- 🐰 성지운
    - 상품(주문)
    - 배포
- 😸 전유림
    - 리뷰
    - 로그인
    - 배포

■ 목적

- 숙박 예약 서비스를 완성 및 협업

<br/>

# 아키텍처

![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/8c1c0e5a-f315-4feb-8363-ad5ffbd78be1)


<br/>

# 구현 환경


<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
<img src="https://img.shields.io/badge/gradle-007396?style=for-the-badge&logo=gradle&logoColor=#02303A"> 

<br/>


![Spring](https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![springboot](https://img.shields.io/badge/springboot-6DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![springsecurity](https://img.shields.io/badge/springsecurity-6DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)


<br/>

![mysql](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

<br/>

![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/d6259d55-5688-4b38-a2cc-ba6234cfd052)

<br/>





# 패키지 구조

```bash
├── main
│   ├── java
│   │   └── com
│   │       └── ybe
│   │           └── tr1ll1on
│   │               ├── AppStartupRunner.java
│   │               ├── Tr1ll1onApplication.java
│   │               ├── domain
│   │               │   ├── accommodation
│   │               │   │   ├── controller
│   │               │   │   │   └── AccommodationController.java
│   │               │   │   ├── dto
│   │               │   │   │   ├── request
│   │               │   │   │   │   └── AccommodationRequest.java
│   │               │   │   │   └── response
│   │               │   │   │       └── AccommodationResponse.java
│   │               │   │   ├── model
│   │               │   │   │   ├── Accommodation.java
│   │               │   │   │   ├── AccommodationFacility.java
│   │               │   │   │   ├── AccommodationImage.java
│   │               │   │   │   └── Category.java
│   │               │   │   ├── repository
│   │               │   │   │   ├── AccommodationFacilityRepository.java
│   │               │   │   │   ├── AccommodationImageRepository.java
│   │               │   │   │   ├── AccommodationMapper.java
│   │               │   │   │   ├── AccommodationRepository.java
│   │               │   │   │   └── CategoryRepository.java
│   │               │   │   └── service
│   │               │   │       └── AccommodationService.java
│   │               │   ├── cart
│   │               │   │   ├── controller
│   │               │   │   │   └── CartController.java
│   │               │   │   ├── dto
│   │               │   │   │   ├── request
│   │               │   │   │   │   ├── AddCartItemRequest.java
│   │               │   │   │   │   └── CartRequest.java
│   │               │   │   │   └── response
│   │               │   │   │       ├── AddCartItemResponse.java
│   │               │   │   │       └── CartResponse.java
│   │               │   │   ├── exception
│   │               │   │   │   ├── CartIdNotFoundException.java
│   │               │   │   │   ├── CartIdNotFoundExceptionCode.java
│   │               │   │   │   ├── CartItemIdNotFoundException.java
│   │               │   │   │   ├── CartItemIdNotFoundExceptionCode.java
│   │               │   │   │   ├── ProductNotExistException.java
│   │               │   │   │   ├── ProductNotExsitExceptionCode.java
│   │               │   │   │   ├── UserAlreadyHasCartException.java
│   │               │   │   │   ├── UserAlreadyHasCartExceptionCode.java
│   │               │   │   │   ├── UserNotFoundException.java
│   │               │   │   │   └── UserNotFoundExceptionCode.java
│   │               │   │   ├── model
│   │               │   │   │   ├── Cart.java
│   │               │   │   │   └── CartItem.java
│   │               │   │   ├── repository
│   │               │   │   │   ├── CartItemRepository.java
│   │               │   │   │   └── CartRepository.java
│   │               │   │   └── service
│   │               │   │       ├── CartService.java
│   │               │   │       └── CartServiceImpl.java
│   │               │   ├── likes
│   │               │   │   ├── controller
│   │               │   │   │   └── LikeController.java
│   │               │   │   ├── dto
│   │               │   │   │   └── LikeDTO.java
│   │               │   │   ├── error
│   │               │   │   │   └── LikeError.java
│   │               │   │   ├── model
│   │               │   │   │   └── Likes.java
│   │               │   │   ├── repository
│   │               │   │   │   └── LikeRepository.java
│   │               │   │   └── service
│   │               │   │       └── LikeService.java
│   │               │   ├── order
│   │               │   │   ├── controller
│   │               │   │   │   └── OrderController.java
│   │               │   │   ├── dto
│   │               │   │   │   ├── request
│   │               │   │   │   │   ├── OrderItemRequest.java
│   │               │   │   │   │   └── OrderRequest.java
│   │               │   │   │   └── response
│   │               │   │   │       ├── OrderItemResponse.java
│   │               │   │   │       └── OrderResponse.java
│   │               │   │   ├── exception
│   │               │   │   │   ├── OrderException.java
│   │               │   │   │   ├── OrderExceptionCode.java
│   │               │   │   │   ├── OrderItemNotFoundException.java
│   │               │   │   │   └── OrderNotFoundException.java
│   │               │   │   ├── model
│   │               │   │   │   ├── OrderItem.java
│   │               │   │   │   └── Orders.java
│   │               │   │   ├── repository
│   │               │   │   │   ├── OrderItemRepository.java
│   │               │   │   │   └── OrderRepository.java
│   │               │   │   └── service
│   │               │   │       └── OrderService.java
│   │               │   ├── product
│   │               │   │   ├── controller
│   │               │   │   │   └── ProductController.java
│   │               │   │   ├── dto
│   │               │   │   │   ├── request
│   │               │   │   │   │   └── AccommodationRequest.java
│   │               │   │   │   └── response
│   │               │   │   │       ├── AccommodationDetailResponse.java
│   │               │   │   │       ├── AccommodationFacilityResponse.java
│   │               │   │   │       ├── AccommodationImageResponse.java
│   │               │   │   │       ├── ProductFacilityResponse.java
│   │               │   │   │       ├── ProductImageResponse.java
│   │               │   │   │       ├── ProductResponse.java
│   │               │   │   │       └── ProductSummaryListResponse.java
│   │               │   │   ├── exception
│   │               │   │   │   ├── ProductException.java
│   │               │   │   │   └── ProductExceptionCode.java
│   │               │   │   ├── model
│   │               │   │   │   ├── Product.java
│   │               │   │   │   ├── ProductFacility.java
│   │               │   │   │   ├── ProductImage.java
│   │               │   │   │   └── ProductInfoPerNight.java
│   │               │   │   ├── repository
│   │               │   │   │   ├── ProductFacilityRepository.java
│   │               │   │   │   ├── ProductImageRepository.java
│   │               │   │   │   ├── ProductInfoPerNightRepository.java
│   │               │   │   │   └── ProductRepository.java
│   │               │   │   └── service
│   │               │   │       └── ProductService.java
│   │               │   ├── review
│   │               │   │   ├── controller
│   │               │   │   │   └── ReviewController.java
│   │               │   │   ├── dto
│   │               │   │   │   ├── request
│   │               │   │   │   │   ├── ReviewCreateRequest.java
│   │               │   │   │   │   └── ReviewUpdateRequest.java
│   │               │   │   │   └── response
│   │               │   │   │       ├── ProductReviewListResponse.java
│   │               │   │   │       ├── ReviewCreateResponse.java
│   │               │   │   │       ├── ReviewDeleteResponse.java
│   │               │   │   │       ├── ReviewUpdateResponse.java
│   │               │   │   │       └── UserReviewListResponse.java
│   │               │   │   ├── exception
│   │               │   │   │   ├── AccommodationNotFoundException.java
│   │               │   │   │   ├── ReviewAlreadyWrittenException.java
│   │               │   │   │   ├── ReviewExceptionCode.java
│   │               │   │   │   └── ReviewNotFoundException.java
│   │               │   │   ├── model
│   │               │   │   │   └── Review.java
│   │               │   │   ├── repository
│   │               │   │   │   └── ReviewRepository.java
│   │               │   │   └── service
│   │               │   │       └── ReviewService.java
│   │               │   └── user
│   │               │       ├── controller
│   │               │       │   ├── AuthController.java
│   │               │       │   └── UserController.java
│   │               │       ├── dto
│   │               │       │   ├── request
│   │               │       │   │   ├── LoginRequest.java
│   │               │       │   │   └── SignUpRequest.java
│   │               │       │   └── response
│   │               │       │       ├── LoginResponse.java
│   │               │       │       ├── MyPageDetailResponse.java
│   │               │       │       ├── MyPageListResponse.java
│   │               │       │       └── SignUpResponse.java
│   │               │       ├── exception
│   │               │       │   ├── EmailAlreadyExistsException.java
│   │               │       │   ├── InValidUserException.java
│   │               │       │   └── InValidUserExceptionCode.java
│   │               │       ├── model
│   │               │       │   ├── User.java
│   │               │       │   └── UserFacility.java
│   │               │       ├── repository
│   │               │       │   └── UserRepository.java
│   │               │       └── service
│   │               │           ├── AuthService.java
│   │               │           └── UserService.java
│   │               ├── global
│   │               │   ├── common
│   │               │   │   ├── AreaCode.java
│   │               │   │   └── Payment.java
│   │               │   ├── config
│   │               │   │   ├── AuthConfig.java
│   │               │   │   ├── MybatisConfig.java
│   │               │   │   └── OpenApiConfig.java
│   │               │   ├── constants
│   │               │   │   └── ApiConstants.java
│   │               │   ├── date
│   │               │   │   ├── exception
│   │               │   │   │   ├── InValidDateException.java
│   │               │   │   │   └── InValidDateExceptionCode.java
│   │               │   │   └── util
│   │               │   │       └── DateUtil.java
│   │               │   └── exception
│   │               │       ├── ExceptionCode.java
│   │               │       ├── ExceptionResponseDTO.java
│   │               │       ├── TrillionException.java
│   │               │       ├── TrillionExceptionCode.java
│   │               │       └── TrillionExceptionHandler.java
│   │               └── security
│   │                   ├── common
│   │                   │   └── Authority.java
│   │                   ├── config
│   │                   │   ├── JwtSecurityConfig.java
│   │                   │   └── SecurityConfig.java
│   │                   ├── constants
│   │                   │   └── JwtConstants.java
│   │                   ├── dto
│   │                   │   └── TokenDto.java
│   │                   ├── exception
│   │                   │   ├── InvalidTokenException.java
│   │                   │   ├── NotTokenException.java
│   │                   │   ├── SecurityExceptionCode.java
│   │                   │   └── UserNotFoundException.java
│   │                   ├── jwt
│   │                   │   ├── JwtAccessDeniedHandler.java
│   │                   │   ├── JwtAuthenticationEntryPoint.java
│   │                   │   ├── JwtAuthenticationProvider.java
│   │                   │   ├── JwtAuthenticationSuccessHandler.java
│   │                   │   ├── JwtFilter.java
│   │                   │   └── JwtTokenProvider.java
│   │                   ├── model
│   │                   │   └── UserPrincipal.java
│   │                   ├── service
│   │                   │   └── CustomUserDetailsService.java
│   │                   └── util
│   │                       └── SecurityUtil.java
│   └── resources
│       ├── application.yml
│       └── mapper
│           └── AccommodationMapper.xml


```

<br/>

# 협업

- Discord
- Agile - Scrum

<br/>

# Git

- Git-Flow 사용
  
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/7ab6a8d8-5bad-41f3-9ecf-e6391c80f3f5)


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

![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/945cc061-b6e6-4d98-8dac-33c8be0e70f3)


<br/>

# ERD

![트릴리언](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/d3b5082e-9ab3-46c4-90da-fb3db2d181a9)

<br/>

# API

⭐아래 API는 스웨거로 작성 되었으며 해당 내용은 프로젝트를 실행시킨 뒤 <br/>
http://localhost:8080/swagger-ui/index.html#/ 해당 링크를 통하여 자세히 보실 수 있습니다!⭐

## ■ 로그인 API

- 회원가입

```
http://localhost:8080/auth/signup
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/6bb88b5f-8630-4a32-8426-5bf813522d41)

- 로그인

```
http://localhost:8080/auth/login
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/b2ca0d2e-208d-45ee-87fa-3f71d09c2574)




- 로그아웃

```
http://localhost:8080/auth/logout
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/ecc77fa0-09de-449e-a3e2-f25b2ce2d8b6)


- 리프레쉬 토큰

```
http://localhost:8080/auth/refreshAccessToken
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/dea92e67-49c5-43de-9e73-e63e5b2617ed)


## ■ 마이페이지 API

- 마이페이지

```
http://localhost:8080/user
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/67f89bf8-11da-474b-b276-b9928bc5f0d8)


- 마이페이지 - 주문내역 상세

```
http://localhost:8080/user/details/{orderId}
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/117db384-4fdf-4500-979a-1456c70cc2c9)


## ■ 숙소 API

- 전체 숙소 조회

```
http://localhost:8080/products
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/c98325ac-7e16-4175-b8a3-cf4dbced9712)

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

## ■ 상품 API

- 개별 상품 요약 조회

```
http://localhost:8080/products/{accommodation_id}
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/297880c3-9cfa-4730-afe8-fc299c189d3a)


- 상품 요약 조회
```
http://localhost:8080/products/summary
```

![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/375bf332-f5ee-4dfd-8dc8-964b6c9ea941)


- 개별 상품 상세페이지 조회

```
http://localhost:8080/products/{accommodation_id}/{product_id}
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/c0e183e4-dd37-402d-a399-4188af091ff1)



## ■ 주문 API

- 상품 주문하기

```
http://localhost:8080/orders
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/7ff05fec-2cb9-4293-b5a9-e49597f416bb)


## ■ 장바구니 API

- 장바구니 상품 전체 조회

```
http://localhost:8080/carts
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/e2c9ca0e-0a91-49ba-ae17-f1bf126f2d9e)


- 장바구니에 상품 추가

```
http://localhost:8080/carts/{product_id}
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/b97e04bf-d13a-4007-ac1e-4db7dd801960)

- 장바구니에 상품 삭제

```
http://localhost:8080/carts/{cartItem_id}
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/d39611e8-c071-45f6-8668-072645cb9d03)



## ■ 리뷰 API

- 내 리뷰 조회

```
http://localhost:8080/reviews
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/bbcd616e-04b0-44fb-aac8-b2b064ca5713)

- 숙소 리뷰 조회
```
http://localhost:8080/reviews/{accommodationId}
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/8c61dfe9-f853-4793-bd46-34c15d3946a3)


- 리뷰 작성

```
http://localhost:8080/reviews
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/302030e2-1d7a-4655-bba0-57e85ac33c60)

- 리뷰 수정

```
http://localhost:8080/reviews/{review_id}
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/aaa09328-e1d2-4cb2-b516-980d93cefb46)


- 리뷰 삭제

```
http://localhost:8080/reviews/{review_id}
```
![image](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/108813475/34dce937-20d3-4967-be47-3c28f29a19d1)




