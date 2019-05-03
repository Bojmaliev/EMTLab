package mk.trkalo.emtlab.EMTlab;

import mk.trkalo.emtlab.EMTlab.model.Role;
import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.model.dto.UserDto;
import mk.trkalo.emtlab.EMTlab.model.exceptions.InvalidEmailException;
import mk.trkalo.emtlab.EMTlab.model.exceptions.ShortNameException;
import mk.trkalo.emtlab.EMTlab.model.exceptions.WeakPasswordException;
import mk.trkalo.emtlab.EMTlab.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmTlabApplicationTests {

	@Autowired
	UserService userService;

	@Test
	public void contextLoads() throws ShortNameException, WeakPasswordException, InvalidEmailException, IOException, MessagingException {
		UserDto user=new UserDto();
		user.firstLastName="Martin Bojmaliev";
		user.email="mbojmaliev@gmail.com";
		user.password="M9das89SAD98ads98s";
		userService.register(user);

	}

}
