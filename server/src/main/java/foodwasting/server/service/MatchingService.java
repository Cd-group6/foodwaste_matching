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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MemberRepository memberRepository;
    private final MatchingRepository matchingRepository;
    private final  MatchedRepository matchedRepository;
    private Matching matching;

    @Transactional
    public Long matching(CreateMatchingRequest request) {

        Member member = memberRepository.findById(request.getMemberId()).get();

        findGeoPoint("경기도 성남시 분당구 삼평동");


        if(request.getTrashOwn()) {

            if (request.getTrashSize()) {
                matching = Matching.builder()
                        .member(member)
                        .address(request.getAddress())
                        .size(3)
                        .trashOwn(true)
                        .build();

            } else {
                matching = Matching.builder()
                        .member(member)
                        .address(request.getAddress())
                        .size(5)
                        .trashOwn(true)
                        .build();
            }

        } else {
            if (request.getTrashSize()) {
                matching = Matching.builder()
                        .member(member)
                        .address(request.getAddress())
                        .size(5)
                        .trashOwn(false)
                        .build();
            } else {
                matching = Matching.builder()
                        .member(member)
                        .address(request.getAddress())
                        .size(5)
                        .trashOwn(false)
                        .build();
            }
        }

        matchingRepository.save(matching);

        return matching.getId();
    }

    private void findGeoPoint(String address) {

        RestTemplate restTemplate = new RestTemplate();

        String kakaoApiKey = "fd681885d9947caf05cfb3b9911024c8";

        try {
            String encodedQuery = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" + encodedQuery;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoApiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<LocationData> response = restTemplate.exchange(apiUrl,
                    HttpMethod.GET, entity, LocationData.class);

            System.out.println("apiUrl = " + apiUrl);

            System.out.println("response = " + response);

            System.out.println("response.getBody() = " + response.getBody());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }




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
