package foodwasting.server.repository;

import foodwasting.server.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByEmail(String email);

    Long findIdByEmail(String email);
}
