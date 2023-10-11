package foodwasting.server.service;

import foodwasting.server.domain.Member;
import foodwasting.server.dto.CreateMemberRequest;
import foodwasting.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(CreateMemberRequest request) {

        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();


        // 존재하지 않는 회원일 경우
        if (!validateDuplicateMember(member)) {
            memberRepository.save(member);
        }

        return member.getId();
    }

    // 회원가입 중복 여부
    private Boolean validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (!findMembers.isEmpty()) {
            member.setId(findMembers.get(0).getId());
            return true;
        } else {
            return false;
        }
    }
}
