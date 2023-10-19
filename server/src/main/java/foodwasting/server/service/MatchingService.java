package foodwasting.server.service;

import foodwasting.server.domain.*;
import foodwasting.server.dto.CreateMatchingRequest;
import foodwasting.server.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
