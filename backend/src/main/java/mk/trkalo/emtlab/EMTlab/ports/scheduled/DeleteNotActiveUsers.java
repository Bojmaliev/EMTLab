package mk.trkalo.emtlab.EMTlab.ports.scheduled;

import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteNotActiveUsers {

    private final UserManagementService userService;

    public DeleteNotActiveUsers(UserManagementService userService) {
        this.userService = userService;
    }

    @Scheduled(cron="0 1 1 * * *")
    public void deleteNotActivatedUsers(){
        userService.deleteNotActivatedUsers();
    }
}
