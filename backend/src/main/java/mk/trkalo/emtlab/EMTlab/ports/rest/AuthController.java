package mk.trkalo.emtlab.EMTlab.ports.rest;


import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.LoginRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.NewPassRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.SignUpRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.response.ApiResponse;
import mk.trkalo.emtlab.EMTlab.model.payloads.response.JwtAuthenticationResponse;
import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final  UserManagementService userManagementService;

    @Autowired
    public AuthController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        String jwt =  userManagementService.login(loginRequest);

        JwtAuthenticationResponse token = new JwtAuthenticationResponse(jwt);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) throws IOException, MessagingException {

        User result = userManagementService.register(signUpRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{id}")
                .buildAndExpand(result.id).toUri();

        return ResponseEntity.created(location).body(new ApiResponse( "User registered successfully"));
    }

    @GetMapping("/activate/{userId}/token/{code}")
    public ResponseEntity<?> activateUser(@PathVariable("userId") Long userId, @PathVariable("code") String code)  {
        userManagementService.activateUser(userId, code);

        return ResponseEntity.ok(new ApiResponse("Successfully activated. Now you can login"));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) throws IOException, MessagingException {
        userManagementService.requestNewPassword(email);

        return ResponseEntity.ok(new ApiResponse("Check your mail for link"));
    }
    @PostMapping("/change_password")
    public ResponseEntity<?> changePassword(@RequestBody NewPassRequest req){
        userManagementService.changeUserPasswordWithToken(req);
        return ResponseEntity.ok(new ApiResponse("You changed your password successfully"));
    }
}
