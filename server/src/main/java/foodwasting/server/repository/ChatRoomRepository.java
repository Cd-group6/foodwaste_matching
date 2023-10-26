package foodwasting.server.repository;

import foodwasting.server.domain.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    List<ChatRoomEntity> findByMember1(String member1);
    List<ChatRoomEntity> findByMember2(String member2);
    List<ChatRoomEntity> findByMember3(String member3);


    List<ChatRoomEntity> findByRoomId(String roomId);

    List<ChatRoomEntity> findAll();
}
