package foodwasting.server.repository;

import foodwasting.server.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    Optional<Matching> findByMemberId(Long memberId);
}
