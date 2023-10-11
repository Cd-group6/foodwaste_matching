package foodwasting.server.controller;

import foodwasting.server.domain.Member;
import foodwasting.server.dto.CreateMemberRequest;
import foodwasting.server.dto.CreateMemberResponse;
import foodwasting.server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody CreateMemberRequest request) {

        Long id = memberService.join(request);
        return new CreateMemberResponse(id);
    }
}
