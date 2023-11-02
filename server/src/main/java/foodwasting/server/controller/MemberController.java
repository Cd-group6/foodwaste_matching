package foodwasting.server.controller;

import foodwasting.server.domain.Member;
import foodwasting.server.dto.CreateMatchingRequest;
import foodwasting.server.dto.CreateMemberRequest;
import foodwasting.server.dto.CreateMemberResponse;
import foodwasting.server.service.MatchingService;
import foodwasting.server.service.MemberService;
import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void PostConstruct() {
        memberService.join(new CreateMemberRequest("shin", "shin@example11.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example12.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example13.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example14.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example156.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example17.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example18.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example154.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example19.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example176.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example158.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example147.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example2.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example1.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example1761.com"));
        memberService.join(new CreateMemberRequest("shin", "shin@example1762.com"));
       }

}
