package foodwasting.server.controller;

import foodwasting.server.dto.CreateMatchingRequest;
import foodwasting.server.dto.CreateMatchingResponse;
import foodwasting.server.repository.MemberRepository;
import foodwasting.server.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;
    private final MatchedService matchedService;
    private final MemberService memberService;
    private final KDTreeService kdTreeService;
    private NodeService root = null;


    @PostMapping("/api/matching")
    public CreateMatchingResponse saveMatching(@RequestBody CreateMatchingRequest request) {
        Logger logger = LoggerFactory.getLogger(MatchingController.class);

        Long matchingId = matchingService.matching(request);

        Double longitude = matchingService.findLongitude(1L);
        Double latitude = matchingService.findLatitude(1L);

        System.out.println("latitude = " + latitude);

        Double[][] axes = {{3.0, 6.0}, {17.0, 15.0}, {13.0, 15.0}, {6.0, 12.0},
                {9.0, 1.0}, {2.0, 7.0}, {10.0, 19.0}};
        Long[] uid = {1l, 2l, 3l, 4l, 5l, 6l, 7l};

        for (int i = 0; i < 7; i++) {
            root = kdTreeService.insert(root, axes[i], uid[i]);
        }

        Double[] u1 = {10.0, 11.0};
        Long uid1 = 1l;
        UsrNodeService user1 = new UsrNodeService(uid1, u1);

        Double[] u2 = {8.0, 9.0};
        Long uid2 = 3l;
        UsrNodeService user2 = new UsrNodeService(uid2, u2);


        NodeService best1 = kdTreeService.nearest(root, user1.getAxes());
        ArrayList<UsrNodeService> result = kdTreeService.findGroup(root, best1, user1);

        NodeService best2 = kdTreeService.nearest(root, user2.getAxes());
        result = kdTreeService.findGroup(root, best2, user2);

        if (result != null) {
            logger.info(String.valueOf(result.get(0).getUId()));
            logger.info(String.valueOf(result.get(1).getUId()));
            logger.info(String.valueOf(best1.getUId()));

//            matchedService.matched(best1.getUId(), result.get(0).getUId(), result.get(1).getUId());
            // 저장
        }

        return null;
    }
}