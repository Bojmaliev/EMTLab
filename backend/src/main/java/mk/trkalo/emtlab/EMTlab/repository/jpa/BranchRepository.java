package mk.trkalo.emtlab.EMTlab.repository.jpa;

import mk.trkalo.emtlab.EMTlab.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByName(String name);

    boolean existsByName(String name);
}
