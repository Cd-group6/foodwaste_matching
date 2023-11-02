package foodwasting.server.service;

import foodwasting.server.domain.Matched;
import foodwasting.server.domain.Member;
import foodwasting.server.repository.MatchedRepository;
import foodwasting.server.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchedService {

    private final MatchedRepository matchedRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long matched(Long ownerId, Long user1Id, Long user2Id, String address) {

        Member owner = memberRepository.findById(ownerId).get();
        Member user1 = memberRepository.findById(user1Id).get();
        Member user2 = memberRepository.findById(user2Id).get();

        Matched matched = Matched.builder()
                .owner(owner)
                .user1(user1)
                .user2(user2)
                .address(address)
                .build();

        matchedRepository.save(matched);

        return matched.getId();
    }
}
