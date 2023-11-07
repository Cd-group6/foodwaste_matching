package foodwasting.server.service;

import foodwasting.server.domain.Member;
import foodwasting.server.dto.DepositRequest;
import foodwasting.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final MemberRepository memberRepository;

    @Transactional
    public Integer chargeDeposit(DepositRequest request) {

        Member member = memberRepository.findById(request.getMemberId()).get();
        Integer deposit = member.getDeposit();
        member.setDeposit(deposit + 900);

        return member.getDeposit();
    }

    @Transactional
    public Integer dischargeDeposit(DepositRequest request) {

        Member member = memberRepository.findById(request.getMemberId()).get();
        Integer deposit = member.getDeposit();
        member.setDeposit(deposit - 900);

        return member.getDeposit();
    }
}
