package mk.trkalo.emtlab.EMTlab.service;

import mk.trkalo.emtlab.EMTlab.model.Branch;

import java.net.URI;
import java.util.List;

public interface BranchService {

    List<Branch> findAll();
    Branch findByName(String name);
    Branch save(String name);
    Branch findById(Long id);

    void setManager(Long branchId, Long managerId);
}
