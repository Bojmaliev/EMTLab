package mk.trkalo.emtlab.EMTlab.service;


import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.dto.UserDto;
import mk.trkalo.emtlab.EMTlab.model.exceptions.*;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService{
    User register(UserDto user) throws ShortNameException, WeakPasswordException, InvalidEmailException, IOException, MessagingException;
    User findById(Long userId) throws UserNotFoundException;
    User findByEmail(String email) throws UserNotFoundException;

    void activateUser(Long userId, String code) throws UserActivationCodeNotFound, UserNotFoundException;

    void requestNewPassword(String email) throws UserNotFoundException, IOException, MessagingException;
}
