package mk.trkalo.emtlab.EMTlab.ports.rest;

import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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





}
