//package com.ybe.tr1ll1on;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
//import com.ybe.tr1ll1on.domain.accommodation.model.Category;
//import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationFacility;
//import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationImage;
//import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationFacilityRepository;
//import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationImageRepository;
//import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
//import com.ybe.tr1ll1on.domain.accommodation.repository.CategoryRepository;
//import com.ybe.tr1ll1on.domain.product.model.Product;
//import com.ybe.tr1ll1on.domain.product.model.ProductFacility;
//import com.ybe.tr1ll1on.domain.product.model.ProductImage;
//import com.ybe.tr1ll1on.domain.product.model.ProductInfoPerNight;
//import com.ybe.tr1ll1on.domain.product.repository.ProductFacilityRepository;
//import com.ybe.tr1ll1on.domain.product.repository.ProductImageRepository;
//import com.ybe.tr1ll1on.domain.product.repository.ProductInfoPerNightRepository;
//import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.util.concurrent.ThreadLocalRandom;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.*;
//
//import static com.ybe.tr1ll1on.global.constants.ApiConstants.*;
//
//@Component
//@RequiredArgsConstructor
//public class AppStartupRunner implements ApplicationRunner {
//
//    private final CategoryRepository categoryRepository;
//
//    private final AccommodationRepository accommodationRepository;
//
//    private final AccommodationFacilityRepository accommodationFacilityRepository;
//
//    private final AccommodationImageRepository accommodationImageRepository;
//
//    private final ProductRepository productRepository;
//
//    private final ProductFacilityRepository productFacilityRepository;
//
//    private final ProductImageRepository productImageRepository;
//
//    private final ProductInfoPerNightRepository productInfoPerNightRepository;
//
//    private final int PRICE_MAX = 10, PRICE_MIN = 5;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        saveAccommodationData();
//    }
//
//    @Transactional
//    public void saveAccommodationData() throws URISyntaxException, JsonProcessingException {
//        String url = String.format("%s?numOfRows=%s&MobileOS=%s&MobileApp=%s&_type=%s&arrange=%s&serviceKey=%s",
//                LINK_STAY, numOfRows, MOBILE_OS, MOBILE_APP, TYPE, ARRANGE, SERVICE_KEY);
//        URI uri = new URI(url);
//
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(uri, String.class);
//
//        Map<String, Object> map = new ObjectMapper().readValue(response, Map.class);
//        Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
//        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
//        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
//        List<Map<String, Object>> itemMap = (List<Map<String, Object>>) itemsMap.get("item");
//
//        for (Map<String, Object> item : itemMap) {
//            // 1. Category Entity 생성 및 데이터베이스 저장
//            Category category = createAndSaveCategory(item);
//
//            // 2. 데이터베이스 저장을 위한 Accommodation Entity 생성
//            Accommodation accommodation = createAccommodation(item);
//            // 3. 데이터베이스 저장을 위한 AccommodationFacility Entity 생성
//            AccommodationFacility accommodationFacility = createAccommodationFacility(item);
//            // 4. 생성한 Accommodation 및 AccommodationFacility 정보를 데이터베이스에 저장
//            Accommodation saveAccommodation = saveAccommodationAndFacility(category, accommodation, accommodationFacility);
//
//            // 5. AccommodationImage Entity 생성 및 데이터베이스 저장
//            createAndSaveAccommodationImages(item, accommodation);
//
//            // 6. 해당 Accommodation Entity 연관 Product Entities 생성 및 데이터베이스 저장
//            saveProductData(item, saveAccommodation);
//        }
//    }
//
//    private Category createAndSaveCategory(Map<String, Object> item) {
//        String categoryCode = (String) item.get("cat3");
//
//        return categoryRepository.findByCategoryCode(categoryCode)
//                .orElseGet(() -> categoryRepository.save(new Category(categoryCode)));
//    }
//
//    private Accommodation createAccommodation(Map<String, Object> item) {
//        String name = getAccommodationName((String) item.get("title"));
//        String address = (String) item.get("addr1");
//        String phone = (String) item.get("tel");
//        String longitude = (String) item.get("mapx");
//        String latitude = (String) item.get("mapy");
//        String areaCode = (String) item.get("areacode");
//
//        return Accommodation.builder()
//                .name(name)
//                .address(address)
//                .phone(phone)
//                .longitude(longitude)
//                .latitude(latitude)
//                .areaCode(areaCode)
//                .build();
//    }
//
//    private static String getAccommodationName(String title) {
//        // 주어진 title 문자열에 "[" 문자가 포함되어 있는지 확인
//        // "[" 문자가 있으면 문자열을 "["를 기준으로 분리하고, 앞 부분만 반환. 없으면 title 그대로 반환.
//        return title.contains("[") ? title.split("\\[")[0] : title;
//    }
//
//    private AccommodationFacility createAccommodationFacility(Map<String, Object> item) throws URISyntaxException, JsonProcessingException {
//        String contentId = (String) item.get("contentid");
//        String contentTypeId = (String) item.get("contenttypeid");
//
//        String url = String.format("%s?ServiceKey=%s&contentTypeId=%s&contentId=%s&MobileOS=%s&MobileApp=%s&_type=%s",
//                LINK_INTRO, SERVICE_KEY, contentTypeId, contentId, MOBILE_OS, MOBILE_APP, TYPE);
//        URI uri = new URI(url);
//
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(uri, String.class);
//
//        AccommodationFacility accommodationFacility;
//        try {
//            Map<String, Object> map = new ObjectMapper().readValue(response, Map.class);
//            Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
//            Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
//            Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
//            Map<String, Object> itemMap = ((List<Map<String, Object>>) itemsMap.get("item")).get(0);
//
//            boolean hasCooking = String.valueOf(itemMap.get("chkcooking")).contains("가능");
//            boolean hasParking = String.valueOf(itemMap.get("parkinglodging")).contains("가능");
//            boolean hasSports = String.valueOf(itemMap.get("sports")).equals("1");
//            boolean hasSauna = String.valueOf(itemMap.get("sauna")).equals("1");
//            boolean hasBeauty = String.valueOf(itemMap.get("beauty")).equals("1");
//
//            accommodationFacility = AccommodationFacility.builder()
//                    .hasCooking(hasCooking)
//                    .hasParking(hasParking)
//                    .hasSports(hasSports)
//                    .hasSauna(hasSauna)
//                    .hasBeauty(hasBeauty)
//                    .build();
//
//        } catch (JsonParseException e) {
//            accommodationFacility = AccommodationFacility.builder()
//                    .hasBeauty(true)
//                    .hasParking(true)
//                    .hasSports(true)
//                    .hasSauna(true)
//                    .hasBeauty(true)
//                    .build();
//        }
//
//        return accommodationFacility;
//    }
//
//    private Accommodation saveAccommodationAndFacility(Category category, Accommodation accommodation, AccommodationFacility accommodationFacility) {
//        accommodation.setCategory(category);
//        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
//
//        accommodationFacility.setAccommodation(savedAccommodation);
//        accommodationFacilityRepository.save(accommodationFacility);
//
//        return savedAccommodation;
//    }
//
//    private void createAndSaveAccommodationImages(Map<String, Object> item, Accommodation accommodation) {
//        List<AccommodationImage> accommodationImages = new ArrayList<>();
//
//        for (int i = 1; i <= 2; i++) {
//            String imageUrl = (String) item.get("firstimage" + (i == 1 ? "" : i));
//
//            if (imageUrl != null) {
//                AccommodationImage accommodationImage = AccommodationImage.builder()
//                        .imageUrl(imageUrl)
//                        .build();
//                accommodationImage.setAccommodation(accommodation);
//                accommodationImages.add(accommodationImage);
//            }
//        }
//
//        accommodationImageRepository.saveAll(accommodationImages);
//    }
//
//    private void saveProductData(Map<String, Object> item, Accommodation accommodation) throws URISyntaxException, JsonProcessingException {
//        String contentId = (String) item.get("contentid");
//        String contentTypeId = (String) item.get("contenttypeid");
//
//        String url = String.format("%s?ServiceKey=%s&contentTypeId=%s&contentId=%s&MobileOS=%s&MobileApp=%s&_type=%s",
//                LINK_INFO, SERVICE_KEY, contentTypeId, contentId, MOBILE_OS, MOBILE_APP, TYPE);
//        URI uri = new URI(url);
//
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(uri, String.class);
//
//        Map<String, Object> map = new ObjectMapper().readValue(response, Map.class);
//        Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
//        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
//        Object itemsObject = bodyMap.get("items");
//
//        if (itemsObject instanceof Map) {
//            String checkInTimeValue = (String) item.get("checkintime");
//            String checkOutTimeValue = (String) item.get("checkouttime");
//            String checkInTime = (checkInTimeValue != null) ? checkInTimeValue : "15:00";
//            String checkOutTime = (checkOutTimeValue != null) ? checkOutTimeValue : "11:00";
//
//            Map<String, Object> itemsMap = (Map<String, Object>) itemsObject;
//            List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");
//
//            // 7. Product Entity 및 ProductFacility Entity 생성 및 데이터베이스 저장
//            createAndSaveProductData(itemList, checkInTime, checkOutTime, accommodation);
//
//        } else {
//            Random random = new Random();
//
//            // 9. 객실에 대한 정보를 제공 하지 않는 경우, 객실 관련 정보를 랜덤으로 생성
//            for (int i = 0; i < 3; i++) {
//                // 10. 데이터베이스 저장을 위한 Random Product Entity 생성
//                Product product = createRandomProduct(accommodation, random);
//                product.setAccommodation(accommodation);
//                productRepository.save(product);
//
//                // 10. 데이터베이스 저장을 위한 Random ProductFacility Entity 생성
//                ProductFacility productFacility = createRandomProductFacility(random);
//                productFacility.setProduct(product);
//                productFacilityRepository.save(productFacility);
//
//                List<ProductImage> productImages = createRandomProductImages(random);
//                for (ProductImage productImage : productImages) {
//                    productImage.setProduct(product);
//                    productImageRepository.save(productImage);
//                }
//            }
//        }
//    }
//
//    private void createAndSaveProductData(List<Map<String, Object>> itemList, String checkintime, String checkouttime, Accommodation accommodation) throws URISyntaxException, JsonProcessingException {
//        for (Map<String, Object> item : itemList) {
//            // 8. Product Entity 생성 및 데이터베이스 저장
//            Product product = createProduct(item, checkintime, checkouttime, accommodation);
//            product.setAccommodation(accommodation);
//            productRepository.save(product);
//
//            // 9. ProductFacility Entity 생성 및 데이터베이스 저장
//            ProductFacility productFacility = createProductFacility(item);
//            productFacility.setProduct(product);
//            productFacilityRepository.save(productFacility);
//
//            // 10. ProductImage Entities 생성 및 데이터베이스 저장
//            List<ProductImage> productImages = createProductImages(item);
//            for (ProductImage productImage : productImages) {
//                productImage.setProduct(product);
//                productImageRepository.save(productImage);
//            }
//
//            saveProductInfoPerNight(product);
//        }
//    }
//
//    private Product createProduct(Map<String, Object> item, String checkintime, String checkouttime, Accommodation accommodation) {
//        Random random = new Random();
//
//        String name = (String) item.get("roomtitle");
//        int count = item.get("roomcount") != null && !"0".equals(item.get("roomcount"))
//                ? Integer.parseInt((String) item.get("roomcount"))
//                : random.nextInt(10) + 1;
//
//        int standardNumber = item.get("roombasecount") != null && !"0".equals(item.get("roombasecount"))
//                ? Integer.parseInt((String) item.get("roombasecount"))
//                : random.nextInt(10) + 1;
//
//        int maximumNumber = item.get("roommaxcount") != null && !"0".equals(item.get("roommaxcount"))
//                ? Integer.parseInt((String) item.get("roommaxcount"))
//                : standardNumber + random.nextInt(10) + 1;
//
//        Product product = Product.builder()
//                .name(name)
//                .checkInTime(checkintime)
//                .checkOutTime(checkouttime)
//                .standardNumber(standardNumber)
//                .maximumNumber(maximumNumber)
//                .count(count)
//                .build();
//        product.setAccommodation(accommodation);
//
//        return product;
//    }
//
//    private ProductFacility createProductFacility(Map<String, Object> item) {
//        return ProductFacility.builder()
//                .hasBath("Y".equals(item.get("roombath")))
//                .hasAirCondition("Y".equals(item.get("roomaircondition")))
//                .hasTv("Y".equals(item.get("roomtv")))
//                .hasPc("Y".equals(item.get("roompc")))
//                .hasCable("Y".equals(item.get("roomcable")))
//                .hasInternet("Y".equals(item.get("roominternet")))
//                .hasRefrigerator("Y".equals(item.get("roomrefrigerator")))
//                .hasToiletries("Y".equals(item.get("roomtoiletries")))
//                .hasSofa("Y".equals(item.get("roomsofa")))
//                .canCook("Y".equals(item.get("roomcook")))
//                .hasTable("Y".equals(item.get("roomtable")))
//                .hasHairDryer("Y".equals(item.get("roomhairdryer")))
//                .build();
//    }
//
//    private List<ProductImage> createProductImages(Map<String, Object> item) {
//        List<ProductImage> productImages = new ArrayList<>();
//        for (int i = 1; i <= 4; i++) {
//            String imageUrl = (String) item.get("roomimg" + i);
//
//            if (!imageUrl.isEmpty()) {
//                ProductImage productImage = ProductImage.builder()
//                        .imageUrl(imageUrl)
//                        .build();
//                productImages.add(productImage);
//            }
//        }
//
//        return productImages;
//    }
//
//    private static Product createRandomProduct(Accommodation accommodation, Random random) {
//        String[] roomTypes = {"슈페리어 룸", "디럭스 룸", "캐릭터 룸", "그랜드 디럭스 룸", "그랜드 디럭스 패밀리 트윈 룸", "프리미어 패밀리 트윈 룸", "주니어 스위트 룸", "디럭스 스위트 룸", "로얄 스위트 룸", "주니어 스위트 룸", "프리미어 스위트 룸"};
//        String roomType = roomTypes[random.nextInt(roomTypes.length)];
//
//        return Product.builder()
//                .name(roomType)
//                .checkInTime("15:00")
//                .checkOutTime("11:00")
//                .standardNumber(random.nextInt(5) + 1)
//                .maximumNumber(random.nextInt(5) + 6)
//                .count(random.nextInt(20) + 1)
//                .build();
//    }
//
//    private static ProductFacility createRandomProductFacility(Random random) {
//        return ProductFacility.builder()
//                .hasBath(random.nextBoolean())
//                .hasAirCondition(random.nextBoolean())
//                .hasTv(random.nextBoolean())
//                .hasPc(random.nextBoolean())
//                .hasCable(random.nextBoolean())
//                .hasInternet(random.nextBoolean())
//                .hasRefrigerator(random.nextBoolean())
//                .hasToiletries(random.nextBoolean())
//                .hasSofa(random.nextBoolean())
//                .canCook(random.nextBoolean())
//                .hasTable(random.nextBoolean())
//                .hasHairDryer(random.nextBoolean())
//                .build();
//    }
//
//    private static List<ProductImage> createRandomProductImages(Random random) {
//        List<String> imageUrls = Arrays.asList(
//                "http://tong.visitkorea.or.kr/cms/resource/50/2705650_image2_1.jpg",
//                "http://tong.visitkorea.or.kr/cms/resource/51/2705651_image2_1.jpg",
//                "http://tong.visitkorea.or.kr/cms/resource/35/2705635_image2_1.jpg"
//        );
//
//        List<ProductImage> productImages = new ArrayList<>();
//        for (String imageUrl : imageUrls) {
//            ProductImage productImage = ProductImage.builder()
//                    .imageUrl(imageUrl)
//                    .build();
//            productImages.add(productImage);
//        }
//
//        return productImages;
//    }
//
//    //product의 11월 24일부터 ~ 12월 10일까지의 정보
//    private void saveProductInfoPerNight(Product product) {
//        LocalDate startDate = LocalDate.of(2023, 11, 24); // 시작일 설정 (11월 24일)
//        LocalDate endDate = LocalDate.of(2023, 12, 10);   // 종료일 설정 (12월 10일)
//        LocalDate currDate = startDate;
//
//        int randomNum = ThreadLocalRandom.current().nextInt(PRICE_MIN, PRICE_MAX);
//        int weekend = (randomNum + 2) * 10000; //주말 가격
//        int weekday = (randomNum) * 10000; //평일 가격
//
//        while (!currDate.isAfter(endDate)) {
//            int price = weekday;
//            if (isWeekend(currDate)) {
//                price = weekend;
//            }
//            ProductInfoPerNight productInfoPerNight = ProductInfoPerNight.builder()
//                    .date(currDate)
//                    .count(product.getCount())
//                    .price(price)
//                    .product(product)
//                    .build();
//            productInfoPerNightRepository.save(productInfoPerNight);
//            currDate = currDate.plusDays(1);
//        }
//
//
//    }
//
//    private static boolean isWeekend(LocalDate date) {
//        DayOfWeek day = date.getDayOfWeek();
//
//        // 토요일 또는 일요일인지 확인
//        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
//    }
//}