package mk.trkalo.emtlab.EMTlab.service;

import mk.trkalo.emtlab.EMTlab.model.payloads.response.SearchClientsResponse;
import org.springframework.data.domain.Pageable;

public interface UsersFilterService {
    SearchClientsResponse filterUsersAdmin(String name, String email, Long branchId, String activated, Pageable pageable);
}
