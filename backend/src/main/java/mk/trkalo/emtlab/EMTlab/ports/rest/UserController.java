package mk.trkalo.emtlab.EMTlab.ports.rest;

import com.sun.mail.iap.Response;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.ChangePasswordRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.response.ApiResponse;
import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;


@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserManagementService userService;

    @Autowired
    public UserController(UserManagementService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?>  getMyInfo(){

        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @PatchMapping("/me/name")
    public ResponseEntity<?> updateName(@RequestParam String name){
        userService.saveName(name);
        return ResponseEntity.ok(new ApiResponse("Your changes are saved!"));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordRequest passwordRequest){
        userService.changePassword(passwordRequest);
        return ResponseEntity.ok(new ApiResponse("Password changed, please login again!"));
    }
    @GetMapping("/me/colleagues")
    public ResponseEntity<?> myColleagues(){
        return  ResponseEntity.ok(userService.myColleagues());
    }






}
