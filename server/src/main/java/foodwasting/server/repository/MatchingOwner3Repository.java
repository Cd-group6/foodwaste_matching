package foodwasting.server.repository;

import foodwasting.server.domain.MatchingOwner3;
import foodwasting.server.domain.MatchingOwner5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingOwner3Repository extends JpaRepository<MatchingOwner3, Long> {
}
