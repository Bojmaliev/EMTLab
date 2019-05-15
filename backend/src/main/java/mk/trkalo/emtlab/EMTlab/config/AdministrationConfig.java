package mk.trkalo.emtlab.EMTlab.config;

import mk.trkalo.emtlab.EMTlab.service.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdministrationConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${app.admin.name}")
    private String adminName;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Autowired
    private UserManagementService userManagementService;


    private static final Logger LOGGER = LoggerFactory.getLogger(AdministrationConfig.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        LOGGER.info("Default administrator is created.");
        userManagementService.createAdministrator(adminName, adminEmail, adminPassword);
    }
}
