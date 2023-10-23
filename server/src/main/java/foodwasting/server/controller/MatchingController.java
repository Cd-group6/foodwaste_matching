package foodwasting.server.controller;

import foodwasting.server.dto.CreateMatchingRequest;
import foodwasting.server.dto.CreateMatchingResponse;
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
    private final KDTreeService kdTreeService;
    private NodeService root = null;


    @PostMapping("/api/matching")
    public CreateMatchingResponse saveMatching(@RequestBody CreateMatchingRequest request) {
        Logger logger = LoggerFactory.getLogger(MatchingController.class);

        Long matchingId = matchingService.matching(request);

        Long[][] axes = {{3l, 6l}, {17l, 15l}, {13l, 15l}, {6l, 12l},
                {9l, 1l}, {2l, 7l}, {10l, 19l}};
        Long[] uid = {1l, 2l, 3l, 4l, 5l, 6l, 7l};

        for (int i = 0; i < 7; i++) {
            root = kdTreeService.insert(root, axes[i], uid[i]);
        }

        Long[] u1 = {10l, 11l};
        Long uid1 = 1l;
        UsrNodeService user1 = new UsrNodeService(uid1, u1);

        Long[] u2 = {8l, 9l};
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
        }

        return new CreateMatchingResponse(matchingId);
    }
}
