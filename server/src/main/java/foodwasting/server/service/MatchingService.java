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
    private final MatchingOwner3Repository matchingOwner3Repository;
    private final MatchingOwner5Repository matchingOwner5Repository;
    private final MatchingUser3Repository matchingUser3Repository;
    private final MatchingUser5Repository matchingUser5Repository;

    @Transactional
    public Long matching(CreateMatchingRequest request) {

        Member member = memberRepository.getById(request.getMemberId());

        if(request.getTrashOwn()) {

            if (request.getTrashSize()) {
                MatchingOwner3 matchingOwner3 = MatchingOwner3.builder()
                        .member(member)
                        .address(request.getAddress())
                        .build();

                matchingOwner3Repository.save(matchingOwner3);

                return matchingOwner3.getId();
            } else {
                MatchingOwner5 matchingOwner5 = MatchingOwner5.builder()
                        .member(member)
                        .address(request.getAddress())
                        .build();

                matchingOwner5Repository.save(matchingOwner5);

                return matchingOwner5.getId();
            }

        } else {
            if (request.getTrashSize()) {
                MatchingUser3 matchingUser3 = MatchingUser3.builder()
                        .member(member)
                        .address(request.getAddress())
                        .build();

                matchingUser3Repository.save(matchingUser3);

                return matchingUser3.getId();
            } else {
                MatchingUser5 matchingUser5 = MatchingUser5.builder()
                        .member(member)
                        .address(request.getAddress())
                        .build();

                matchingUser5Repository.save(matchingUser5);

                return matchingUser5.getId();
            }
        }
    }

}
