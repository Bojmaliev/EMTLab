package mk.trkalo.emtlab.EMTlab.web;

import mk.trkalo.emtlab.EMTlab.model.exceptions.UserActivationCodeNotFound;
import mk.trkalo.emtlab.EMTlab.model.exceptions.UserNotFoundException;
import mk.trkalo.emtlab.EMTlab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/activate/{code}")
    public String activateUser(@PathVariable("userId") Long userId, @PathVariable("code") String code) throws UserNotFoundException, UserActivationCodeNotFound {
        userService.activateUser(userId, code);
        return "Succefull activated";
    }

    @PostMapping("/forgot_password")
    public String forgotPassword(@RequestParam("email") String email) throws UserNotFoundException, IOException, MessagingException {
        userService.requestNewPassword(email);
        return "Check your email for the new password";
    }


}
