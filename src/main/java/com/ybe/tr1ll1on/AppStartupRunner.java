package com.ybe.tr1ll1on;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.tr1ll1on.domain.accommodation.model.*;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationFacilityRepository;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationImageRepository;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationCategoryRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.model.ProductFacility;
import com.ybe.tr1ll1on.domain.product.model.ProductImage;
import com.ybe.tr1ll1on.domain.product.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Component
public class AppStartupRunner implements ApplicationRunner {

    @Autowired
    private AccommodationCategoryRepository accommodationCategoryRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private AccommodationFacilityRepository accommodationFacilityRepository;
    @Autowired
    private AccommodationImageRepository accommodationImageRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductFacilityRepository productFacilityRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        saveAccommodationData();
    }

    @Transactional
    public void saveAccommodationData() throws URISyntaxException, JsonProcessingException {
        String link = "https://apis.data.go.kr/B551011/KorService1/searchStay1";
        String MobileOS = "ETC";
        String MobileApp = "AppTest";
        String _type = "json";
        String arrange = "R";
        String serviceKey = "%2BDa2lT5XIGAE29yuGKFRC1c0iRzTfjBEYyD3W%2B16MAbFz%2FdH3O%2B9RWS2b2huHvLKfkThYs3qdbCYxjuDBlhGsA%3D%3D";

        String url = String.format("%s?MobileOS=%s&MobileApp=%s&_type=%s&arrange=%s&serviceKey=%s",
                link, MobileOS, MobileApp, _type, arrange, serviceKey);
        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(
                uri,
                String.class
        );

        Map<String, Object> map = new ObjectMapper().readValue(response, Map.class);
        Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
        List<Map<String, Object>> itemMap = (List<Map<String, Object>>) itemsMap.get("item");

        for (Map<String, Object> item : itemMap) {
            AccommodationCategory category = saveCategory(item);
            Accommodation accommodation = saveAccommodation(item);
            AccommodationFacility accommodationFacility = saveAccommodationFacility(item);

            // Accommodation 및 Facility 관련 정보를 저장
            saveAccommodationAndFacility(accommodation, category, accommodationFacility);
            //  Accommodation 이미지 정보 저장
            saveAccommodationImages(accommodation, item);

            //  Product 정보 설정
            setProductData(accommodation, item);
        }
    }

    private Accommodation saveAccommodation(Map<String, Object> item) {
        String name = (String) item.get("title");
        String address = (String) item.get("addr1");
        String phone = (String) item.get("tel");
        String longitude =(String) item.get("mapx");
        String latitude = (String) item.get("mapy");
        String areaCode = (String) item.get("areacode");

        return Accommodation.builder()
                .name(name)
                .address(address)
                .phone(phone)
                .longitude(longitude)
                .latitude(latitude)
                .areaCode(areaCode)
                .build();
    }

    private AccommodationCategory saveCategory(Map<String, Object> item) {
        String categoryCode = (String) item.get("cat3");

        return accommodationCategoryRepository.findByCategoryCode(categoryCode)
                .orElseGet(() -> accommodationCategoryRepository.save(new AccommodationCategory(categoryCode)));
    }

    private AccommodationFacility saveAccommodationFacility(Map<String, Object> item) throws URISyntaxException, JsonProcessingException {
        String contentId = (String) item.get("contentid");
        String contentTypeId = (String) item.get("contenttypeid");

        String link = "http://apis.data.go.kr/B551011/KorService1/detailIntro1";
        String MobileOS = "ETC";
        String MobileApp = "AppTest";
        String _type = "json";
        String serviceKey = "%2BDa2lT5XIGAE29yuGKFRC1c0iRzTfjBEYyD3W%2B16MAbFz%2FdH3O%2B9RWS2b2huHvLKfkThYs3qdbCYxjuDBlhGsA%3D%3D";

        String url = String.format("%s?ServiceKey=%s&contentTypeId=%s&contentId=%s&MobileOS=%s&MobileApp=%s&_type=%s",
                link, serviceKey, contentTypeId, contentId, MobileOS, MobileApp, _type);
        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(
                uri,
                String.class
        );

        Map<String, Object> map = new ObjectMapper().readValue(response, Map.class);
        Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");

        if (itemList != null && !itemList.isEmpty()) {
            Map<String, Object> itemMap = itemList.isEmpty() ? Collections.emptyMap() : itemList.get(0);

            boolean hasCooking = extractBooleanValue(itemMap, "chkcooking", "가능");
            boolean hasParking = extractBooleanValue(itemMap, "parkinglodging", "가능");
            boolean hasSports = extractBooleanValue(itemMap, "sports", "1");
            boolean hasSauna = extractBooleanValue(itemMap, "sauna", "1");
            boolean hasBeauty = extractBooleanValue(itemMap, "beauty", "1");

            AccommodationFacility accommodationFacility = AccommodationFacility.builder()
                    .hasCooking(hasCooking)
                    .hasParking(hasParking)
                    .hasSports(hasSports)
                    .hasSauna(hasSauna)
                    .hasBeauty(hasBeauty)
                    .build();

            return accommodationFacility;
        }
        return null;
    }

    private void saveAccommodationAndFacility(Accommodation accommodation, AccommodationCategory category, AccommodationFacility accommodationFacility) {
        accommodation.setCategory(category);
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        accommodationFacility.setAccommodation(savedAccommodation);
        accommodationFacilityRepository.save(accommodationFacility);
    }

    private void saveAccommodationImages(Accommodation accommodation, Map<String, Object> item) {
        List<AccommodationImage> accommodationImages = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            String imageUrl = (String) item.get("firstimage" + (i == 1 ? "" : i));

            if (imageUrl != null && !imageUrl.isEmpty()) {
                AccommodationImage accommodationImage = AccommodationImage.builder()
                        .imageUrl(imageUrl)
                        .build();
                accommodationImage.setAccommodation(accommodation);
                accommodationImages.add(accommodationImage);
            }
        }

        accommodationImageRepository.saveAll(accommodationImages);
    }

    private void setProductData(Accommodation accommodation, Map<String, Object> item) throws URISyntaxException, JsonProcessingException {
        String contentId = (String) item.get("contentid");
        String contentTypeId = (String) item.get("contenttypeid");

        String link = "http://apis.data.go.kr/B551011/KorService1/detailInfo1";
        String MobileOS = "ETC";
        String MobileApp = "AppTest";
        String _type = "json";
        String serviceKey = "%2BDa2lT5XIGAE29yuGKFRC1c0iRzTfjBEYyD3W%2B16MAbFz%2FdH3O%2B9RWS2b2huHvLKfkThYs3qdbCYxjuDBlhGsA%3D%3D";

        String url = String.format("%s?ServiceKey=%s&contentTypeId=%s&contentId=%s&MobileOS=%s&MobileApp=%s&_type=%s",
                link, serviceKey, contentTypeId, contentId, MobileOS, MobileApp, _type);
        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri, String.class);

        Map<String, Object> map = new ObjectMapper().readValue(response, Map.class);
        Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Object itemsObject = bodyMap.get("items");

        if (itemsObject instanceof Map) {
            String checkInTimeValue = (String) item.get("checkintime");
            String checkOutTimeValue = (String) item.get("checkouttime");
            String checkInTime = (checkInTimeValue != null) ? checkInTimeValue : "15:00";
            String checkOutTime = (checkOutTimeValue != null) ? checkOutTimeValue : "11:00";

            Map<String, Object> itemsMap = (Map<String, Object>) itemsObject;
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");

            // 상품 및 상품 편의 관련 저장
            saveProductAndFacilit(itemList, checkInTime, checkOutTime, accommodation);

        } else {
            // 정보가 존재하지 않을 경우 임의로 생성
            Random random = new Random();

            for (int i = 0; i < 3; i++) {
                ProductFacility facility = ProductFacility.builder()
                        .hasBath(random.nextBoolean())
                        .hasAirCondition(random.nextBoolean())
                        .hasTV(random.nextBoolean())
                        .hasPC(random.nextBoolean())
                        .hasCable(random.nextBoolean())
                        .hasInternet(random.nextBoolean())
                        .hasRefrigerator(random.nextBoolean())
                        .hasToiletries(random.nextBoolean())
                        .hasSofa(random.nextBoolean())
                        .canCook(random.nextBoolean())
                        .hasTable(random.nextBoolean())
                        .hasHairDryer(random.nextBoolean())
                        .build();

                List<String> imageUrls = Arrays.asList(
                        "http://tong.visitkorea.or.kr/cms/resource/50/2705650_image2_1.jpg",
                        "http://tong.visitkorea.or.kr/cms/resource/51/2705651_image2_1.jpg",
                        "http://tong.visitkorea.or.kr/cms/resource/35/2705635_image2_1.jpg"
                );

                List<ProductImage> productImages = new ArrayList<>();
                for (String imageUrl : imageUrls) {
                    ProductImage productImage = ProductImage.builder()
                            .imageUrl(imageUrl)
                            .build();
                    productImages.add(productImage);
                }

                String[] roomTypes = {"스탠다드", "디럭스", "스위트"};
                String roomType = roomTypes[random.nextInt(roomTypes.length)];

                Product product = Product.builder()
                        .name(roomType)
                        .checkInTime("15:00")
                        .checkOutTime("11:00")
                        .standardNumber(random.nextInt(5) + 1)
                        .maximumNumber(random.nextInt(5) + 6)
                        .count(random.nextInt(20) + 1)
                        .build();

                product.setAccommodation(accommodation);
                productRepository.save(product);

                facility.setProduct(product);
                productFacilityRepository.save(facility);

                for (ProductImage productImage : productImages) {
                    productImage.setProduct(product);
                    productImageRepository.save(productImage);
                }
            }
        }
    }
    private void saveProductAndFacilit (List<Map<String, Object>> itemList, String checkintime, String checkouttime, Accommodation accommodation) throws URISyntaxException, JsonProcessingException {

        for (Map<String, Object> item : itemList) {
            Product product = createProduct(item, checkintime, checkouttime, accommodation);
            product.setAccommodation(accommodation);
            productRepository.save(product);

            ProductFacility productFacility = createProductFacility(item);
            productFacility.setProduct(product);
            productFacilityRepository.save(productFacility);

            List<ProductImage> productImages = createProductImages(item);
            for (ProductImage productImage : productImages) {
                productImage.setProduct(product);
                productImageRepository.save(productImage);
            }

        }

    }

    private ProductFacility createProductFacility(Map<String, Object> item) {
        return ProductFacility.builder()
                .hasBath("Y".equals(item.get("roombath")))
                .hasAirCondition("Y".equals(item.get("roomaircondition")))
                .hasTV("Y".equals(item.get("roomtv")))
                .hasPC("Y".equals(item.get("roompc")))
                .hasCable("Y".equals(item.get("roomcable")))
                .hasInternet("Y".equals(item.get("roominternet")))
                .hasRefrigerator("Y".equals(item.get("roomrefrigerator")))
                .hasToiletries("Y".equals(item.get("roomtoiletries")))
                .hasSofa("Y".equals(item.get("roomsofa")))
                .canCook("Y".equals(item.get("roomcook")))
                .hasTable("Y".equals(item.get("roomtable")))
                .hasHairDryer("Y".equals(item.get("roomhairdryer")))
                .build();
    }

    private List<ProductImage> createProductImages(Map<String, Object> item) {
        List<ProductImage> productImages = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String imageUrl = (String) item.get("roomimg" + i);
            if (imageUrl != null && !imageUrl.isEmpty()) {
                ProductImage productImage = ProductImage.builder()
                        .imageUrl(imageUrl)
                        .build();
                productImages.add(productImage);
            }
        }
        return productImages;
    }

    private Product createProduct(Map<String, Object> item, String checkintime, String checkouttime, Accommodation accommodation) {
        String name = (String) item.get("roomtitle");
        int count = item.get("roomcount") != null ? Integer.parseInt((String) item.get("roomcount")) : 3; // 기본값 3
        int standardNumber = item.get("roombasecount") != null ? Integer.parseInt((String) item.get("roombasecount")) : 3; // 기본값 3
        int maximumNumber = item.get("roommaxcount") != null ? Integer.parseInt((String) item.get("roommaxcount")) : 3; // 기본값 3

        Product product = Product.builder()
                .name(name)
                .checkInTime(checkintime)
                .checkOutTime(checkouttime)
                .standardNumber(standardNumber)
                .maximumNumber(maximumNumber)
                .count(count)
                .build();
        product.setAccommodation(accommodation);

        return product;
    }

    private boolean extractBooleanValue(Map<String, Object> itemMap, String key, String trueValue) {
        return Optional.ofNullable((String) itemMap.get(key))
                .map(value -> value.contains(trueValue))
                .orElse(false);
    }
}

