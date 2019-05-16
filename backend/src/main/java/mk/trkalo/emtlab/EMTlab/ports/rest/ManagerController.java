package mk.trkalo.emtlab.EMTlab.ports.rest;

import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.AdminClientManage;
import mk.trkalo.emtlab.EMTlab.model.payloads.response.ApiResponse;
import mk.trkalo.emtlab.EMTlab.model.payloads.response.SearchClientsResponse;
import mk.trkalo.emtlab.EMTlab.service.ManagerService;
import mk.trkalo.emtlab.EMTlab.service.UsersFilterService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/manager", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerController {
    private final UsersFilterService usersFilterService;
    private final ManagerService managerService;

    public ManagerController(UsersFilterService usersFilterService, ManagerService managerService) {
        this.usersFilterService = usersFilterService;
        this.managerService = managerService;
    }

    @GetMapping("/search-clients")
    public ResponseEntity<?> searchClients(@RequestParam String name, @RequestParam String email, @RequestParam String activated, @RequestParam Long branchId, @RequestParam Integer usersPerPage, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page-1, usersPerPage);
        SearchClientsResponse searchClients = usersFilterService.filterUsersAdmin(name, email, branchId, activated, pageable);
        return ResponseEntity.ok(searchClients);
    }

    @PostMapping("/clients")
    public ResponseEntity<?> newClient(@RequestBody AdminClientManage adminClientManage){

        User result = managerService.createUser(adminClientManage);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{id}")
                .buildAndExpand(result.id).toUri();
        return ResponseEntity.created(location).body(new ApiResponse("Successful created new client"));
    }
    @PatchMapping("/clients/{clientId}")
    public ResponseEntity<?> newClient(@RequestBody AdminClientManage adminClientManage, @PathVariable Long clientId){

        User result = managerService.editUser(adminClientManage.id, adminClientManage);

        return ResponseEntity.ok(new ApiResponse("Successful edited the client"));
    }
}
