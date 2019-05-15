package mk.trkalo.emtlab.EMTlab;

import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmTlabApplicationTests {

	@Autowired
	UserManagementService userService;

	@Test
	public void contextLoads() {
//		UserDto user=new UserDto();
//		user.firstLastName="Martin Bojmaliev";
//		user.email="mbojmaliev@gmail.com";
//		user.password="M9das89SAD98ads98s";
//		user.matchingPassword="M9das89SAD98ads98s";
//		userService.register(user);

	}

}
