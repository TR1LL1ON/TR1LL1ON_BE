# KPT 기간 보완사항

- 무한 스크롤
- 찜 기능
- 마이페이지 조회
- 리뷰 조회
  - 성능개선
- 페이지네이션




![트릴리언 로그인](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/59862752/7ccf0ded-277d-4465-ae71-c7d4268770f6)

![트릴리언 2](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/59862752/eb4333dc-4b25-434d-81e1-5c95aaa0f6c6)

![트릴리언 3](https://github.com/TR1LL1ON/TR1LL1ON_BE/assets/59862752/1ed7b251-cc4a-4ddd-aea1-adc9dd61219a)


# 개요

■ 프로젝트명

- TR1ll1on
- https://www.tr1ll1on.site/

■ 기간, 참여인원

- 11/20(월) ~ 12/01(금)

  
### ✨ 팀 소개

|                                             서은 (BE) <br> 팀장                                             |                                                김수빈 (BE) <br> 팀원                                                 |                     성지운 (BE) <br> 팀원                     |                              전유림 (BE) <br> 팀원                                                           
| :-----------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------: | :-----------------------------------------------------------------------------: 
|                            ![WestSilver99](https://avatars.githubusercontent.com/WestSilver99)                            |                             ![Su-daa](https://avatars.githubusercontent.com/Su-daa)                              | ![sungjiwoon](https://avatars.githubusercontent.com/sungjiwoon) |    ![yurim0628](https://avatars.githubusercontent.com/yurim0628)    | 
|                                      [WestSilver99](https://github.com/WestSilver99)                                      |                                       [Su-daa](https://github.com/Su-daa)                                        |           [sungjiwoon](https://github.com/sungjiwoon)           |              [yurim0628](https://github.com/yurim0628)              |
| <ul><li>초기설정(깃허브)</li><li>장바구니</li></ul> | <ul><li>초기설정(깃허브)</li><li>숙소전체조회(무한스크롤)</li></ul> | <ul><li>상품 상세 조회</li><li>상품 결제</li><li>CI/CD 서버 배포</li></ul>   | <li>OPEN API</li><ul><li>시큐리티</li><li>리뷰</li><li>마이페이지</li></ul> |   

<br>

■ 목적

- 숙박 예약 서비스를 완성 및 협업

## 📚 주요 기능

<div align="center">
  <table>
    <tr align="center">
      <th>로그인/회원가입</th>
      <th>숙소/객실</th>
    </tr>
    <tr>      
      <td><img src="https://github.com/TR1LL1ON/TR1LL1ON_FE/assets/134940630/496bb4d4-d79b-4a4f-ba55-cc5037d8aa28"
height="400"></td>
      <td><img src="https://github.com/TR1LL1ON/TR1LL1ON_FE/assets/134940630/032d7699-af9b-4082-a861-70d5704d7851" height="400"></td>      
    </tr>    
        <tr align="center">
      <th>예약/장바구니</th>
      <th>결제</th>         
    </tr>
    <tr>      
      <td><img src="https://github.com/TR1LL1ON/TR1LL1ON_FE/assets/134940630/6aabc597-ccef-4555-bb57-87f981ec947b"
height="400"></td>
      <td><img src="https://github.com/TR1LL1ON/TR1LL1ON_FE/assets/134940630/efe32c44-6dc7-43f8-8b8c-e6a56176477a" height="400"></td>      
    </tr>    
            <tr align="center">
      <th>예약내역/리뷰작성</th>
      <th>주변숙소 검색</th>
    </tr>
    <tr>      
      <td><img src="https://github.com/TR1LL1ON/TR1LL1ON_FE/assets/134940630/fd931cda-1bb2-4c1d-b742-bf8e8995a618"
 alt="chatting/painting" height="400"></td>
      <td><img src="https://github.com/TR1LL1ON/TR1LL1ON_FE/assets/134940630/bea1014c-7ae7-4c71-8043-d43a9e3ab314" height="400"></td>      
    </tr> 
                <tr align="center">
      <th>카테고리</th>    
    </tr>
    <tr>      
      <td><img src="https://github.com/TR1LL1ON/TR1LL1ON_FE/assets/134940630/fa80c45c-6170-44b8-a589-eaceb9bcc701"
 alt="category" height="400"></td>     
    </tr> 
  </table>
</div>

</br>
</br>

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




