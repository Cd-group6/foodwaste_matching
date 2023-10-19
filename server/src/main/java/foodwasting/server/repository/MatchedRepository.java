package foodwasting.server.repository;

import foodwasting.server.domain.Matched;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchedRepository extends JpaRepository<Matched, Long> {
}
