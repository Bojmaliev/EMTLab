package mk.trkalo.emtlab.EMTlab.service.impl;

import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.dto.UserDto;
import mk.trkalo.emtlab.EMTlab.model.exceptions.*;
import mk.trkalo.emtlab.EMTlab.repository.jpa.UserRepository;
import mk.trkalo.emtlab.EMTlab.repository.mail.MailSenderRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import mk.trkalo.emtlab.EMTlab.service.UserService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MailSenderRepository mailSenderRepository;

    public UserServiceImpl(UserRepository userRepository, MailSenderRepository mailSenderRepository) {
        this.userRepository = userRepository;
        this.mailSenderRepository = mailSenderRepository;
    }


    public User register(UserDto dto) throws ShortNameException, WeakPasswordException, InvalidEmailException, IOException, MessagingException {
        User user = User.createUser(dto.firstLastName, dto.email, dto.password);
        user = userRepository.save(user);

        Map<String, String> map = new HashMap<>();
        map.put("userName", user.firstLastName);
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

    public User login(String email, String password) throws UserInvalidLoginDetails {
        User u = userRepository.findByEmailAndPassword(email, password).orElseThrow(UserInvalidLoginDetails::new);

        return u;
    }

    @Override
    public User findById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }


    @Override
    public void activateUser(Long userId, String code) throws UserActivationCodeNotFound, UserNotFoundException {
        User user = findById(userId);
        user.activateUser(code);
        userRepository.flush();

    }

    @Override
    public void requestNewPassword(String email) throws UserNotFoundException, IOException, MessagingException {
        User u = findByEmail(email);
        String newPass = User.randomStringGenerator(10);
        u.password=newPass;
        userRepository.flush();

        Map<String, String> map = new HashMap<>();
        map.put("userName", u.firstLastName);
        map.put("userNewPassword", newPass);
        mailSenderRepository.sendHtmlMail(
                u.email,
                "You requested new password | EMT Lab",
                "requestNewPass.html",
                map
        );



    }

    @Scheduled(cron="0 1 1 * * *")
    public void deleteNotActivatedUsers(){
        userRepository.deleteAllByActivatedFalseAndRegisteredOnBefore(LocalDateTime.now().plusHours(24));
    }
}