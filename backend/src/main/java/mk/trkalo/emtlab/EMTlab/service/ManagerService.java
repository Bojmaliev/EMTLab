package mk.trkalo.emtlab.EMTlab.service;

import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.AdminClientManage;

import java.util.List;

public interface ManagerService {
    List<User> findAllFreeManagers();

    User createUser(AdminClientManage adminClientManage);

    User editUser(Long id, AdminClientManage adminClientManage);
}
