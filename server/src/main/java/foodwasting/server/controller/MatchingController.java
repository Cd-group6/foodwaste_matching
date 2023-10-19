package foodwasting.server.controller;

import foodwasting.server.domain.Member;
import foodwasting.server.dto.CreateMatchingRequest;
import foodwasting.server.dto.CreateMatchingResponse;
import foodwasting.server.service.KDTreeService;
import foodwasting.server.service.MatchingService;
import foodwasting.server.service.MemberService;
import foodwasting.server.service.NodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;
    private final KDTreeService kdTreeService;
    private NodeService root = null;

    @PostMapping("/api/matching")
    public CreateMatchingResponse saveMatching(@RequestBody CreateMatchingRequest request) {

        Long matchingId = matchingService.matching(request);

//        long test[] = {129L,384L};
//        long test2[] = {132L,314L};
//
//
//        System.out.println("test2 = " + test2.getClass().getName());
//
//        if (request.getTrashOwn()) {
//            kdTreeService.insert(root, test);
//        } else {
//            kdTreeService.nearest(this.root, test2);
//        }




        return new CreateMatchingResponse(matchingId);
    }
}
