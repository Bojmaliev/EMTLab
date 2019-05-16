package mk.trkalo.emtlab.EMTlab.service.impl;

import mk.trkalo.emtlab.EMTlab.model.Branch;
import mk.trkalo.emtlab.EMTlab.model.Role;
import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.payloads.response.SearchClientsResponse;
import mk.trkalo.emtlab.EMTlab.repository.jpa.BranchRepository;
import mk.trkalo.emtlab.EMTlab.repository.jpa.UserRepository;
import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import mk.trkalo.emtlab.EMTlab.service.UsersFilterService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersFilterServiceImpl implements UsersFilterService {
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final UserManagementService userManagementService;

    public UsersFilterServiceImpl(UserRepository userRepository, BranchRepository branchRepository, UserManagementService userManagementService) {
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
        this.userManagementService = userManagementService;
    }


    @Override
    public SearchClientsResponse filterUsersAdmin(String name, String email, Long branchId, String activated, Pageable pageable) {
        SearchClientsResponse searchClientsResponse = new SearchClientsResponse();
        User u = new User();
        u.name = name;
        u.activated=null;
        if(activated.equals("yes")) u.activated = true;
        if(activated.equals("no")) u.activated = false;
        u.email=email;
        u.branch = null;
        User loggedUser = userManagementService.getLoggedUser();
        if(loggedUser.role == Role.ROLE_MANAGER){
            u.branch = loggedUser.branch;
        }else if(branchId != 0) {
            Optional<Branch> b = branchRepository.findById(branchId);
            b.ifPresent(branch -> u.branch = branch);
        }


        ExampleMatcher ex = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("registeredOn", "activationCode", "role");

        Example<User> example = Example.of(u, ex);

        Page<User> page = userRepository.findAll(example, pageable);

        searchClientsResponse.numPages = page.getTotalPages();
        searchClientsResponse.clients = page.getContent();
        return searchClientsResponse;
    }
}
