package mk.trkalo.emtlab.EMTlab.ports.rest;

import mk.trkalo.emtlab.EMTlab.model.Branch;
import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.payloads.response.ApiResponse;
import mk.trkalo.emtlab.EMTlab.service.BranchService;
import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/branches", produces = MediaType.APPLICATION_JSON_VALUE)
public class BranchController {
    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(branchService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(branchService.findById(id));
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestParam String name){
        Branch b = branchService.save(name);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/branches/{id}")
                .buildAndExpand(b.id).toUri();
        return ResponseEntity.created(location).body(new ApiResponse("Successfully added new branch"));
    }
    @GetMapping("/{branchId}/set-manager/{managerId}")
    public ResponseEntity<?> changeManager(@PathVariable Long branchId, @PathVariable Long managerId){

        branchService.setManager(branchId, managerId);
        return ResponseEntity.ok(new ApiResponse("Saved!"));

    }
}
