package mk.trkalo.emtlab.EMTlab.repository.jpa;

import mk.trkalo.emtlab.EMTlab.model.Branch;
import mk.trkalo.emtlab.EMTlab.model.Role;
import mk.trkalo.emtlab.EMTlab.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    void deleteAllByActivatedFalseAndRegisteredOnBefore(LocalDateTime dateTime);
    Optional<User> findByEmail(String email);
    List<User> findAllByBranch(Branch branch);

    @Query("SELECT _user from User as _user " +
            "WHERE _user.role = mk.trkalo.emtlab.EMTlab.model.Role.ROLE_MANAGER " +
            "AND _user NOT IN (" +
                "SELECT DISTINCT _branch.manager from Branch as _branch" +
            ")")
    List<User> findAllFreeManagers();
}
