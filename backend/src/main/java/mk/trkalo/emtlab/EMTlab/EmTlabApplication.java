package mk.trkalo.emtlab.EMTlab;

import mk.trkalo.emtlab.EMTlab.security.UserPrincipal;
import mk.trkalo.emtlab.EMTlab.repository.jpa.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class EmTlabApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmTlabApplication.class, args);
	}

}
