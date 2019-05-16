package mk.trkalo.emtlab.EMTlab.service.impl;

import mk.trkalo.emtlab.EMTlab.model.Branch;
import mk.trkalo.emtlab.EMTlab.model.Role;
import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.AdminClientManage;
import mk.trkalo.emtlab.EMTlab.repository.jpa.UserRepository;
import mk.trkalo.emtlab.EMTlab.service.BranchService;
import mk.trkalo.emtlab.EMTlab.service.ManagerService;
import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final UserRepository userRepository;
    private final UserManagementService userManagementService;
    private final BranchService branchService;
    private final PasswordEncoder passwordEncoder;

    public ManagerServiceImpl(UserRepository userRepository, UserManagementService userManagementService, BranchService branchService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userManagementService = userManagementService;
        this.branchService = branchService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAllFreeManagers() {
        return userRepository.findAllFreeManagers();
    }

    @Override
    public User createUser(AdminClientManage adminClientManage) {
        if(!StringUtils.hasLength(adminClientManage.name) || !StringUtils.hasLength(adminClientManage.password)) throw new Error("All fields are required" );

        if(userManagementService.existsByEmail(adminClientManage.email)) throw new Error("Already exists that email");
        User logged = userManagementService.getLoggedUser();
        User newUser = User.createUser(adminClientManage.name, adminClientManage.email, passwordEncoder.encode(adminClientManage.password));
        newUser.activated=true;
        if(logged.role == Role.ROLE_ADMIN){
            if(adminClientManage.branchId != 0) {
                Branch branch = branchService.findById(adminClientManage.branchId);
                newUser.branch = branch;
            }
            if(adminClientManage.manager){
                newUser.role = Role.ROLE_MANAGER;

            }

        }else if(logged.role == Role.ROLE_MANAGER){
            newUser.branch = logged.branch;
        }

        return userRepository.saveAndFlush(newUser);

    }

    @Override
    public User editUser(Long id, AdminClientManage adminClientManage) {
        if(!StringUtils.hasLength(adminClientManage.name)) throw new Error("Name is required" );

        User logged = userManagementService.getLoggedUser();
        User newUser = userManagementService.findById(id);

        if(logged.role==Role.ROLE_MANAGER&& logged.branch.id != newUser.branch.id) throw new Error("Doesnt have permissions");
        newUser.email = adminClientManage.email;
        newUser.name = adminClientManage.name;
        if(StringUtils.hasLength(adminClientManage.password)){
            newUser.password = passwordEncoder.encode(adminClientManage.password);
        }
        if(logged.role == Role.ROLE_ADMIN){
            if(adminClientManage.branchId != 0) {
                Branch branch = branchService.findById(adminClientManage.branchId);
                newUser.branch = branch;
            }
            if(adminClientManage.manager){
                newUser.role = Role.ROLE_MANAGER;
            }else{
                newUser.role = Role.ROLE_USER;
            }

        }else if(logged.role == Role.ROLE_MANAGER){
            newUser.branch = logged.branch;
        }

        return userRepository.saveAndFlush(newUser);

    }
}
