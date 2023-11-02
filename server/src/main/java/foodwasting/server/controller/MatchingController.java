package foodwasting.server.controller;

import foodwasting.server.domain.Matching;
import foodwasting.server.dto.CreateMatchingRequest;
import foodwasting.server.dto.CreateMatchingResponse;
import foodwasting.server.repository.MatchingRepository;
import foodwasting.server.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
        log.info("Saved matching request time");

        Matching matching = matchingRepository.findById(matchingId).get();
        Double latitude = matching.getLatitude();
        Double longitude = matching.getLongitude();

        Double[] axes = {latitude, longitude};
        Long uid = request.getMemberId();

        ArrayList<UsrNodeService> result = null;
        NodeService best1 = null;
        PriorityQueue<UsrNodeService> tempQueue = new PriorityQueue<>(timeComparator);

        if (request.getTrashOwn()) {
            log.info("Inserting trashOwner into a KDTree");
            root = kdTreeService.insert(root, axes, uid);

            log.info("Searching for trashUsers within range");
            while (!queue.isEmpty()) {
                UsrNodeService node = queue.poll();
                best1 = kdTreeService.nearest(root, node.getAxes());

                if (best1 != null) {
                    best1 = best1.withinRange();
                }
                if (best1 == null) {
                    tempQueue.add(node);
                    continue;
                }

                result = kdTreeService.findGroup(root, best1, node);

                if (result != null) {
                    log.info("Found Group");
                    root = kdTreeService.deleteNode(root, best1.getAxes());
                    log.info("Delete TrashOwner from KDTree");
                    Matching owner = matchingRepository.findByMemberId(best1.getUId()).get();
                    matchedService.matched(best1.getUId(), result.get(0).getUId(), result.get(1).getUId(), owner.getAddress());
                    chatRoomService.createChatRoom(best1.getUId().toString(), result.get(0).getUId().toString(), result.get(1).getUId().toString());
                    while (!tempQueue.isEmpty()) {
                        queue.add(tempQueue.poll());
                    }
                    return new CreateMatchingResponse(request.getMemberId());
                }
            }
            log.info("Need more trashUsr");
            log.info("Waiting for trashUsers within range");
            while (!tempQueue.isEmpty()) {
                queue.add(tempQueue.poll());
            }

        } else if (!request.getTrashOwn()) {
            log.info("Searching for trashOwner within range");
            UsrNodeService user1 = new UsrNodeService(uid, axes, timeStamp);
            best1 = kdTreeService.nearest(root, user1.getAxes());

            if (best1 != null) {
                best1 = best1.withinRange();
            }
            if (best1 == null) {
                log.info("No nearby trashOwner found");
                queue.add(user1);
            }

            result = kdTreeService.findGroup(root, best1, user1);
            if (result != null) {
                log.info("Found Group");
                root = kdTreeService.deleteNode(root, best1.getAxes());
                log.info("Delete TrashOwner from KDTree");
                Matching owner = matchingRepository.findByMemberId(best1.getUId()).get();
                matchedService.matched(best1.getUId(), result.get(0).getUId(), result.get(1).getUId(), owner.getAddress());
                chatRoomService.createChatRoom(best1.getUId().toString(), result.get(0).getUId().toString(), result.get(1).getUId().toString());
                while (!tempQueue.isEmpty()) {
                    queue.add(tempQueue.poll());
                }
                return new CreateMatchingResponse(request.getMemberId());
            }
            log.info("Waiting in the queue");
            //
        }


        return null;
    }
}