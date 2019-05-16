package mk.trkalo.emtlab.EMTlab.service.impl;

import mk.trkalo.emtlab.EMTlab.model.Role;
import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.exceptions.Error;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.ChangePasswordRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.LoginRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.ForgotPassSubmitRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.SignUpRequest;
import mk.trkalo.emtlab.EMTlab.repository.jpa.UserRepository;
import mk.trkalo.emtlab.EMTlab.repository.mail.MailSenderRepository;
import mk.trkalo.emtlab.EMTlab.security.JwtTokenProvider;
import mk.trkalo.emtlab.EMTlab.security.UserPrincipal;
import mk.trkalo.emtlab.EMTlab.service.Methods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final MailSenderRepository mailSenderRepository;

    @Autowired
    public UserManagementServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, MailSenderRepository mailSenderRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.mailSenderRepository = mailSenderRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public User register(SignUpRequest dto) throws IOException, MessagingException {
        dto.email = dto.email.toLowerCase();
        if(!StringUtils.hasLength(dto.name) || !StringUtils.hasLength(dto.email) || !StringUtils.hasLength(dto.password) || !StringUtils.hasLength(dto.matchPassword)) throw new Error("All fields are required!");
        String pass = getValidatedPassword(dto.password, dto.matchPassword);
        if(existsByEmail(dto.email)) throw new Error("Email is already in use");
        User user = User.createUser(dto.name, dto.email, pass);

        user = userRepository.save(user);

        Map<String, String> map = new HashMap<>();
        map.put("userName", user.name);
        map.put("userId", user.id.toString());
        map.put("userActivationCode", user.activationCode);
        mailSenderRepository.sendHtmlMail(
                user.email,
                "Activate your profile | EMT Lab",
                "activateUser.html",
                map
                );

        return user;
    }
    @Override
    public boolean existsByEmail(String email){
        return userRepository.findByEmail(email).isPresent();

    }


    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new Error("User not found"));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase()).orElseThrow(()->new Error("User not found"));
    }
    @Override
    public User getLoggedUser(){
        Long id = ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).id;
        return findById(id);
    }

    @Override
    public void activateUser(Long userId, String code) {
        User user = findById(userId);
        user.activateUser(code);
        userRepository.flush();

    }

    @Override
    public void requestNewPassword(String email) throws IOException, MessagingException {
        User u = findByEmail(email);
        if(!u.activated) throw new Error("User is not activated");
        String code = Methods.generateRandomString(20);
        u.activationCode=code;
        userRepository.flush();

        Map<String, String> map = new HashMap<>();
        map.put("userName", u.name);
        map.put("userId", u.id.toString());
        map.put("userCode", code);
        mailSenderRepository.sendHtmlMail(
                u.email,
                "You requested new password | EMT Lab",
                "requestNewPass.html",
                map
        );
    }

    @Override
    public void changeUserPasswordWithToken(ForgotPassSubmitRequest forgotPassSubmit) {
        if(!StringUtils.hasLength(forgotPassSubmit.token)) throw new Error("All files are required");
        String pass = getValidatedPassword(forgotPassSubmit.matchPassword, forgotPassSubmit.password);

        User u = findById(forgotPassSubmit.userId);
        u.setPasswordViaRequest(pass, forgotPassSubmit.token);


    }
    @Override
    public void deleteNotActivatedUsers(){
        userRepository.deleteAllByActivatedFalseAndRegisteredOnBefore(LocalDateTime.now().plusHours(24));
    }

    @Override
    public void createAdministrator(String adminName, String adminEmail, String adminPassword) {
        User user;
        if(existsByEmail(adminEmail)){
            user = findByEmail(adminEmail);
            user.password=passwordEncoder.encode(adminPassword);
        }else {
            user = User.createUser(adminName, adminEmail, passwordEncoder.encode(adminPassword));
            user.activateUser(user.activationCode);
        }
        user.role = Role.ROLE_ADMIN;
        userRepository.saveAndFlush(user);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        if(!StringUtils.hasLength(loginRequest.email) || !StringUtils.hasLength(loginRequest.password)) throw new Error("All fields are required");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email,
                        loginRequest.password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    @Override
    public void saveName(String name) {
        User u = getLoggedUser();
        if(!StringUtils.hasLength(name))throw new Error("All fields are required");
        u.name = name;
        userRepository.saveAndFlush(u);
    }

    @Override
    public void changePassword(ChangePasswordRequest passwordRequest) {
        User u = getLoggedUser();
        u.setPassword(getValidatedPassword(passwordRequest.password, passwordRequest.matchPassword));
        userRepository.saveAndFlush(u);

    }

    @Override
    public List<User> myColleagues() {
        User u = getLoggedUser();
        if(u.branch == null) throw new Error("User doesn't have any colleagues");
        return userRepository.findAllByBranch(u.branch);
    }

    public String getValidatedPassword(String pas1, String pas2){
        if(!StringUtils.hasLength(pas1) ||
                !StringUtils.hasLength(pas2)) throw new Error("All files are required");
        if(!pas1.equals(pas2)) throw new Error("Passwords doesn't match");

        if(!Methods.isPasswordStrong(pas1)) throw new Error("Please enter stronger password");

        return passwordEncoder.encode(pas1);

    };
}