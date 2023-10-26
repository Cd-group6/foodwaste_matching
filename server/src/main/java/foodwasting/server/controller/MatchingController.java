package foodwasting.server.controller;

import foodwasting.server.domain.Matching;
import foodwasting.server.domain.Member;
import foodwasting.server.dto.CreateMatchingRequest;
import foodwasting.server.dto.CreateMatchingResponse;
import foodwasting.server.repository.MatchingRepository;
import foodwasting.server.repository.MemberRepository;
import foodwasting.server.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;
    private final MatchedService matchedService;
    private final ChatRoomService chatRoomService;
    private final KDTreeService kdTreeService;
    private final MatchingRepository matchingRepository;
    private NodeService root = null;


    @PostMapping("/api/matching")
    public CreateMatchingResponse saveMatching(@RequestBody CreateMatchingRequest request) {
        Logger logger = LoggerFactory.getLogger(MatchingController.class);

        Long matchingId = matchingService.matching(request);

        Matching matching = matchingRepository.findById(matchingId).get();
        Double latitude = matching.getLatitude();
        Double longitude = matching.getLongitude();

        System.out.println("longitude = " + longitude);

        Double[] axes = {latitude, longitude};
        Long uid = request.getMemberId();

        ArrayList<UsrNodeService> result = null;
        NodeService best1 = null;

        if (request.getTrashOwn()) {
            root = kdTreeService.insert(root, axes, uid);
            logger.info(root.toString());
        } else if(!request.getTrashOwn()) {
            UsrNodeService user1 = new UsrNodeService(uid, axes);
            best1 = kdTreeService.nearest(root, user1.getAxes());
            result = kdTreeService.findGroup(root, best1, user1);
        }

        if (result != null) {
            Matching owner = matchingRepository.findByMemberId(best1.getUId()).get();

            matchedService.matched(best1.getUId(), result.get(0).getUId(), result.get(1).getUId(), owner.getAddress());
            chatRoomService.createChatRoom(best1.getUId().toString(), result.get(0).getUId().toString(), result.get(1).getUId().toString());
            // 저장
            return new CreateMatchingResponse(request.getMemberId());

        }

        return null;
    }
}