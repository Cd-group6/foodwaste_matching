package foodwasting.server.service;

import foodwasting.server.domain.ChatRoomEntity;
import foodwasting.server.domain.Member;
import foodwasting.server.dto.DepositRequest;
import foodwasting.server.repository.ChatRoomRepository;
import foodwasting.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

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

    @Transactional
    public Integer roomDeposit(String roomId) {
        ChatRoomEntity chatRoom =chatRoomRepository.findByRoomId(roomId);
        chatRoom.setDeposit(chatRoom.getDeposit() - 180);
        return chatRoom.getDeposit();
    }
}
