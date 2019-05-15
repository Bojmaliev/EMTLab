package mk.trkalo.emtlab.EMTlab.service.impl;

import mk.trkalo.emtlab.EMTlab.model.Role;
import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.exceptions.Error;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.LoginRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.NewPassRequest;
import mk.trkalo.emtlab.EMTlab.model.payloads.request.SignUpRequest;
import mk.trkalo.emtlab.EMTlab.repository.jpa.UserRepository;
import mk.trkalo.emtlab.EMTlab.repository.mail.MailSenderRepository;
import mk.trkalo.emtlab.EMTlab.security.JwtTokenProvider;
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


    public User register(SignUpRequest dto) throws IOException, MessagingException {
        dto.email = dto.email.toLowerCase();
        if(!StringUtils.hasLength(dto.name) || !StringUtils.hasLength(dto.email) || !StringUtils.hasLength(dto.password) || !StringUtils.hasLength(dto.matchPassword)) throw new mk.trkalo.emtlab.EMTlab.model.exceptions.Error("All fields are required!");

        if(existsByEmail(dto.email)) throw new Error("Email is already in use");
        if(!dto.password.equals(dto.matchPassword)) throw new java.lang.Error("Password doesnt match");
        if(!Methods.isPasswordStrong(dto.password)) throw new java.lang.Error("Weak password");
        User user = User.createUser(dto.name, dto.email, passwordEncoder.encode(dto.password));

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
    public void changeUserPasswordWithToken(NewPassRequest newPassRequest) {
        if(!StringUtils.hasLength(newPassRequest.password) ||
                !StringUtils.hasLength(newPassRequest.matchPassword) ||
                !StringUtils.hasLength(newPassRequest.token)) throw new Error("All files are required");
        User u = findById(newPassRequest.userId);
        if(!newPassRequest.password.equals(newPassRequest.matchPassword)) throw new Error("Passwords doesn't match");
        if(!Methods.isPasswordStrong(newPassRequest.password)) throw new Error("Please enter stronger password");

        u.setPasswordViaRequest(passwordEncoder.encode(newPassRequest.password), newPassRequest.token);


    }

    public void deleteNotActivatedUsers(){
        userRepository.deleteAllByActivatedFalseAndRegisteredOnBefore(LocalDateTime.now().plusHours(24));
    }

    @Override
    public void createAdministrator(String adminName, String adminEmail, String adminPassword) {
        User user = User.createUser(adminName, adminEmail, passwordEncoder.encode(adminPassword));
        user.roleList.add(Role.ROLE_ADMIN)
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email,
                        loginRequest.password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }
}