package mk.trkalo.emtlab.EMTlab.ports.rest;

import mk.trkalo.emtlab.EMTlab.model.payloads.response.SearchClientsResponse;
import mk.trkalo.emtlab.EMTlab.service.ManagerService;
import mk.trkalo.emtlab.EMTlab.service.UsersFilterService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private final ManagerService managerService;
    private final UsersFilterService usersFilterService;

    public AdminController(ManagerService managerService, UsersFilterService usersFilterService) {
        this.managerService = managerService;
        this.usersFilterService = usersFilterService;
    }

    @GetMapping("/free-managers")
    public ResponseEntity<?> findAllManager() {
        return ResponseEntity.ok(managerService.findAllFreeManagers());
    }

}
