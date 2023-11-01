package foodwasting.server.controller;

import foodwasting.server.domain.Matching;
import foodwasting.server.domain.Member;
import foodwasting.server.dto.CreateMatchingRequest;
import foodwasting.server.dto.CreateMatchingResponse;
import foodwasting.server.repository.MatchingRepository;
import foodwasting.server.repository.MemberRepository;
import foodwasting.server.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchingController {

    private final MatchingService matchingService;
    private final MatchedService matchedService;
    private final ChatRoomService chatRoomService;
    private final KDTreeService kdTreeService;
    private final MatchingRepository matchingRepository;
    private final Comparator<UsrNodeService> timeComparator = Comparator.comparingLong(UsrNodeService::getTimestamp);
    private final PriorityQueue<UsrNodeService> queue = new PriorityQueue<>(timeComparator);
    private NodeService root = null;


    @PostMapping("/api/matching")
    public CreateMatchingResponse saveMatching(@RequestBody CreateMatchingRequest request) {

        Long matchingId = matchingService.matching(request);
        Long timeStamp = System.currentTimeMillis();

        Matching matching = matchingRepository.findById(matchingId).get();
        Double latitude = matching.getLatitude();
        Double longitude = matching.getLongitude();

        Double[] axes = {latitude, longitude};
        Long uid = request.getMemberId();

        ArrayList<UsrNodeService> result = null;
        NodeService best1 = null;
        PriorityQueue<UsrNodeService> tempQueue = new PriorityQueue<>(timeComparator);

        if (request.getTrashOwn()) {
            root = kdTreeService.insert(root, axes, uid);

            while (!queue.isEmpty()) {
                UsrNodeService node = queue.poll();
                best1 = kdTreeService.nearest(root, node.getAxes());

                if (best1 != null) {
                    best1 = best1.withinRange();
                }
                if (best1 == null) {
                    log.info("add123");
                    tempQueue.add(node);
                    continue;
                }

                result = kdTreeService.findGroup(root, best1, node);

                if (result != null) {
                    root = kdTreeService.deleteNode(root, best1.getAxes());
                    Matching owner = matchingRepository.findByMemberId(best1.getUId()).get();

                    matchedService.matched(best1.getUId(), result.get(0).getUId(), result.get(1).getUId(), owner.getAddress());
                    chatRoomService.createChatRoom(best1.getUId().toString(), result.get(0).getUId().toString(), result.get(1).getUId().toString());
                    while (!tempQueue.isEmpty()) {
                        log.info("paste");

                        queue.add(tempQueue.poll());
                    }
                    return new CreateMatchingResponse(request.getMemberId());
                }
            }
            while (!tempQueue.isEmpty()) {
                log.info("paste");

                queue.add(tempQueue.poll());
            }

            log.info(root.toString());
        } else if (!request.getTrashOwn()) {
            UsrNodeService user1 = new UsrNodeService(uid, axes, timeStamp);
            best1 = kdTreeService.nearest(root, user1.getAxes());

            if (best1 != null) {
                best1 = best1.withinRange();
            }
            if (best1 == null) {
                log.info("add q");
                queue.add(user1);
            }

            log.info("best = {}", best1);

            Iterator iter = queue.iterator();
            while (iter.hasNext()) {
                log.info("queue = {}", iter.next());
            }

            result = kdTreeService.findGroup(root, best1, user1);
        }

        if (result != null) {
            root = kdTreeService.deleteNode(root, best1.getAxes());
            Matching owner = matchingRepository.findByMemberId(best1.getUId()).get();

            matchedService.matched(best1.getUId(), result.get(0).getUId(), result.get(1).getUId(), owner.getAddress());
            chatRoomService.createChatRoom(best1.getUId().toString(), result.get(0).getUId().toString(), result.get(1).getUId().toString());
            while (!tempQueue.isEmpty()) {
                log.info("paste");

                queue.add(tempQueue.poll());
            }
            return new CreateMatchingResponse(request.getMemberId());

        }

        return null;
    }
}