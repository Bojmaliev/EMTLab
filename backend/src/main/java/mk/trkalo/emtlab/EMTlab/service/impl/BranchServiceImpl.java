package mk.trkalo.emtlab.EMTlab.service.impl;

import mk.trkalo.emtlab.EMTlab.model.Branch;
import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.repository.jpa.BranchRepository;
import mk.trkalo.emtlab.EMTlab.service.BranchService;
import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final UserManagementService userManagementService;

    public BranchServiceImpl(BranchRepository branchRepository, UserManagementService userManagementService) {
        this.branchRepository = branchRepository;
        this.userManagementService = userManagementService;
    }

    @Override
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    public Branch findByName(String name) {
        return branchRepository.findByName(name).orElseThrow(()-> new Error("Branch not found"));
    }

    @Override
    public Branch save(String name) {
        if(!StringUtils.hasLength(name))throw new Error("Name cannot be empty");
        if(branchRepository.existsByName(name)) throw new Error("Branch with a name already exists");
        return branchRepository.save(new Branch(name));
    }

    @Override
    public Branch findById(Long id) {
        return branchRepository.findById(id).orElseThrow(()-> new Error("Branch doesn't exists"));
    }

    @Override
    public void setManager(Long branchId, Long managerId) {

        Branch branch = findById(branchId);
        if(managerId!=0){

            User user = userManagementService.findById(managerId);
            branch.manager = user;
            user.branch=branch;
        }
        else branch.manager=null;

        branchRepository.saveAndFlush(branch);
    }
}
