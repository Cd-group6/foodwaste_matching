package foodwasting.server.service;

import foodwasting.server.domain.*;
import foodwasting.server.dto.CreateMatchingRequest;
import foodwasting.server.dto.LocationData;
import foodwasting.server.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MemberRepository memberRepository;
    private final MatchingRepository matchingRepository;
    private final  MatchedRepository matchedRepository;
    private Matching matching;


    public Double findLatitude(Long id) {
        return matchingRepository.findLatitudeById(id);
    }

    public Double findLongitude(Long id) {
        return matchingRepository.findLongitudeById(id);
    }

    @Transactional
    public Long matching(CreateMatchingRequest request) {

        Member member = memberRepository.findById(request.getMemberId()).get();

        List<Double> coords = findGeoPoint(request.getAddress());

        System.out.println("coords.get(0) + coords.get(1)  = " + coords.get(0) + coords.get(1) );

        if(request.getTrashOwn()) {

            if (request.getTrashSize()) {
                matching = Matching.builder()
                        .member(member)
                        .longitude(coords.get(0))
                        .latitude(coords.get(1))
                        .address(request.getAddress())
                        .size(3)
                        .trashOwn(true)
                        .build();

            } else {
                matching = Matching.builder()
                        .member(member)
                        .longitude(coords.get(0))
                        .latitude(coords.get(1))
                        .address(request.getAddress())
                        .size(5)
                        .trashOwn(true)
                        .build();
            }

        } else {
            if (request.getTrashSize()) {
                matching = Matching.builder()
                        .member(member)
                        .longitude(coords.get(0))
                        .latitude(coords.get(1))
                        .address(request.getAddress())
                        .size(5)
                        .trashOwn(false)
                        .build();
            } else {
                matching = Matching.builder()
                        .member(member)
                        .longitude(coords.get(0))
                        .latitude(coords.get(1))
                        .address(request.getAddress())
                        .size(5)
                        .trashOwn(false)
                        .build();
            }
        }

        matchingRepository.save(matching);

        return matching.getId();
    }

    private List<Double> findGeoPoint(String address) {

        String kakaoApiKey = "fd681885d9947caf05cfb3b9911024c8";


        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        URI targetUrl = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("query", address)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        System.out.println("targetUrl = " + targetUrl);

        ResponseEntity<LocationData> response = restTemplate.exchange(targetUrl,
                HttpMethod.GET, entity, LocationData.class);

        LocationData locationData = response.getBody();

        Double longitude = Double.parseDouble((locationData.getDocuments().get(0).getX()));

        Double latitude = Double.parseDouble((locationData.getDocuments().get(0).getY()));

        List<Double> coords = Arrays.asList(longitude, latitude);

        return coords;
    }


    @Transactional
    public void matched(Member member1, Member member2, Member member3) {

        Matched matched = Matched.builder()
                .owner(member1)
                .user1(member2)
                .user2(member3)
                .build();

        matchedRepository.save(matched);

    }

}
