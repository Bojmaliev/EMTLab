package mk.trkalo.emtlab.EMTlab.service;


import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.ChangePasswordRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.LoginRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.ForgotPassSubmitRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.SignUpRequest;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;


public interface UserManagementService {
    User register(SignUpRequest user) throws IOException, MessagingException;
    User findById(Long userId) ;
    User findByEmail(String email);
    boolean existsByEmail(String email);

    User getLoggedUser();

    void activateUser(Long userId, String code);

    void requestNewPassword(String email) throws IOException, MessagingException;
    void changeUserPasswordWithToken(ForgotPassSubmitRequest forgotPassSubmit);
    void deleteNotActivatedUsers();

    void createAdministrator(String adminName, String adminEmail, String adminPassword);

    String login(LoginRequest loginRequest);

    void saveName(String name);

    void changePassword(ChangePasswordRequest passwordRequest);

    List<User> myColleagues();
}
