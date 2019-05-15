package mk.trkalo.emtlab.EMTlab.service;


import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.LoginRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.NewPassRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.SignUpRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.response.JwtAuthenticationResponse;

import javax.mail.MessagingException;
import java.io.IOException;


public interface UserManagementService {
    User register(SignUpRequest user) throws IOException, MessagingException;
    User findById(Long userId) ;
    User findByEmail(String email);

    void activateUser(Long userId, String code);

    void requestNewPassword(String email) throws IOException, MessagingException;
    void changeUserPasswordWithToken(NewPassRequest newPassRequest);
    void deleteNotActivatedUsers();

    void createAdministrator(String adminName, String adminEmail, String adminPassword);

    String login(LoginRequest loginRequest);
}
